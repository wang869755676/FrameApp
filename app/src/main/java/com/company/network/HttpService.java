package com.company.network;


import com.company.db.User;
import com.company.model.MessageList;
import com.company.model.Repo;
import com.company.model.Result;
import com.squareup.okhttp.ResponseBody;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit的服务接口，由于使用了RxJava进行封装，将原先应该返回的Call封装成了Observable。但是网络请求是在被订阅时才发生的，所以必须在io线程或新线程调用
 * Created by snowbean on 16-6-7.
 */
public interface HttpService {

    @GET("/user/message/list")
    Call<Result<MessageList>> getMessageList(@Query("page") int page);
    String URL_BASE = "https://api.github.com";
    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<List<User>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);

    @Headers({HEADER_API_VERSION})
    @GET("/users/{username}/repos")
    Observable<List<Repo>> getRepos(@Path("username") String userName);

    @Headers({HEADER_API_VERSION})
    @GET("/users/{username}") Observable<Response<User>> getUser(@Path("username") String username);

    @Multipart
    @POST("")
    Call<ResponseBody> register(@Body Object post, @Part MultipartBody.Part image);  //图文上传
    // 、、使用@Field时记得添加@FormUrlEncoded  单个提交post的请求
    @FormUrlEncoded
    @POST("user/edit")
    Call<User> getUser(@FieldMap Map<String, String> map);
}
