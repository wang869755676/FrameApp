package com.company.network;

import com.company.cache.CacheProviders;
import com.company.db.User;
import com.company.utils.SharePreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * play的网络数据源，通过Retrofit请求得到
 * Created by snowbean on 16-6-6.
 */
public class HttpUtils {

    private static final String BASE_URL = "https://api.github.com";
    private Retrofit mRetrofit;
    private HttpService mPlayService;
    private ErrorListener mErrorListener;
   private CacheProviders cacheProviders; // 缓存操作类

    //TODO 待完善，还有一些配置未加入，现在只加入请求头
     public HttpUtils(SharePreferenceUtil userDataUtils,File caheFile) {
        Gson gson = new GsonBuilder()
              /*  .registerTypeAdapter(EmojiType.class, new EmojiSerializer())
                .registerTypeAdapter(LevelType.class, new LevelSerializer())
                .registerTypeAdapter(GenderType.class, new GenderSerializer())
                .registerTypeAdapter(NotificationType.class, new NotificationSerializer())
                .registerTypeAdapter(NewsType.class,new NewsSerializer())*/
                .create();
         //persistence设置为缓存文件路径cacheDir,using设置成你所定义的接口类class
         cacheProviders = new RxCache.Builder()
                 .persistence(caheFile,new GsonSpeaker())
                 .using(CacheProviders.class);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor mTokenInterceptor = chain -> {
            Request originRequest = chain.request();
            Request authorisedRequest = originRequest.newBuilder()
                    .header("X-Auth-Token", userDataUtils.getToken())  //TODO TOKEN的判断
                    .build();
            Response response = chain.proceed(authorisedRequest);
            if (response.code() == 403 || response.code() == 500) {
                if (mErrorListener != null) {
                    mErrorListener.onRemoteErrorHappened(response.code());
                }
            }
            return response;
        };


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(mTokenInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mPlayService = mRetrofit.create(HttpService.class);
    }

    public void setErrorListener(ErrorListener errorListener) {
        mErrorListener = errorListener;
    }

    public Observable<Reply<List<User>>> getUsers(int idLastUserQueried, final boolean update) {
        return cacheProviders.getUsers(mPlayService.getUsers(idLastUserQueried, 20), new DynamicKey(idLastUserQueried), new EvictDynamicKey(update));
    }
}
