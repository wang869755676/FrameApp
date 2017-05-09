package com.company.network;

import android.content.Context;
import android.widget.Toast;

import com.company.exception.ApiException;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

//辅助处理异常
public class ApiErrorHelper {

    public static void handleCommonError(Context context, Throwable e) {
        if (e instanceof HttpException) {
            Toast.makeText(context, "服务暂不可用", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
              Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
           //ApiException处理
        } else {
             Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }

}