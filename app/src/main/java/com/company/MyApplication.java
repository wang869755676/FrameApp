package com.company;

import android.app.Application;

import com.company.contants.MContants;
import com.company.network.HttpUtils;
import com.company.utils.SharePreferenceUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by smile_x on 2016/10/25.
 */

public class MyApplication extends Application {

    private HttpUtils httpUtils;
    private static MyApplication instance;
    public SharePreferenceUtil mPreferenceUtil;

    public static MyApplication getInstance() { // 通过一个方法给外面提供实例
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mPreferenceUtil=new SharePreferenceUtil(this, MContants.SHAREPREFERENCE_NAME);
        /**
         *  初始化网络请求的工具类
         */
        httpUtils = new HttpUtils(mPreferenceUtil,getFilesDir());
        Fresco.initialize(this);
    }

    /**
     * 获得网络请求工具类
     *
     * @return
     */
    public HttpUtils getHttpUtils() {
        return httpUtils;
    }
}
