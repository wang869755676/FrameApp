package com.company.ui.activty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.company.R;
import com.company.base.BaseFragmentActivity;
import com.company.base.BasePresenter;
import com.company.ui.activty.presenter.SplashActivityPresenter;
import com.company.ui.activty.view.SplashActivityView;


public class SplashActivity extends BaseFragmentActivity<SplashActivityPresenter,SplashActivityView> implements SplashActivityView {

    @Override
    protected SplashActivityPresenter initPresenter() {
        return new SplashActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.startMenuActivity();

    }


    @Override
    public void onBackPressed() {
        mPresenter.stopTime();
        super.onBackPressed();
    }

    @Override
    public void gotoMainActivity() {
        startActivity(new Intent(mContext,MainActivity.class));
        finish();
    }

    @Override
    public void gotFirstActivity() {
        startActivity(new Intent(mContext,FirstActivity.class));
        finish();
    }
}
