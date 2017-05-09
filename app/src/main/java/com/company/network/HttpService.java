package com.company.network;


import com.company.model.MessageList;
import com.company.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit的服务接口，由于使用了RxJava进行封装，将原先应该返回的Call封装成了Observable。但是网络请求是在被订阅时才发生的，所以必须在io线程或新线程调用
 * Created by snowbean on 16-6-7.
 */
public interface HttpService {

    @GET("/user/message/list")
    Call<Result<MessageList>> getMessageList(@Query("page") int page);
}
