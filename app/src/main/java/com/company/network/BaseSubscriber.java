package com.company.network;

import android.content.Context;

import com.company.contants.ApiErrorCode;
import com.company.exception.ApiException;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public class BaseSubscriber<T> implements Subscriber<T> {
    private Context mContext;

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context) {
        mContext = context;
    }


    @Override
    public void onError(Throwable e) {
        ApiErrorHelper.handleCommonError(mContext, e);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {

    }
}