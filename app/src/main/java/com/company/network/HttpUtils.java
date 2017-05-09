package com.company.network;

import com.company.utils.SharePreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * play的网络数据源，通过Retrofit请求得到
 * Created by snowbean on 16-6-6.
 */
public class HttpUtils {

    private static final String BASE_URL = "http://api.playalot.cn/";
    private Retrofit mRetrofit;
    private HttpService mPlayService;
    private ErrorListener mErrorListener;


    //TODO 待完善，还有一些配置未加入，现在只加入请求头
     public HttpUtils(SharePreferenceUtil userDataUtils) {
        Gson gson = new GsonBuilder()
              /*  .registerTypeAdapter(EmojiType.class, new EmojiSerializer())
                .registerTypeAdapter(LevelType.class, new LevelSerializer())
                .registerTypeAdapter(GenderType.class, new GenderSerializer())
                .registerTypeAdapter(NotificationType.class, new NotificationSerializer())
                .registerTypeAdapter(NewsType.class,new NewsSerializer())*/
                .create();

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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mPlayService = mRetrofit.create(HttpService.class);
    }

    public void setErrorListener(ErrorListener errorListener) {
        mErrorListener = errorListener;
    }
}
