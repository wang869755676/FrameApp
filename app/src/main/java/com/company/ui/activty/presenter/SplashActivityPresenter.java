package com.company.ui.activty.presenter;

import android.content.Intent;
import android.os.Handler;

import com.company.MyApplication;
import com.company.base.BasePresenter;
import com.company.ui.activty.FirstActivity;
import com.company.ui.activty.SplashActivity;
import com.company.ui.activty.view.SplashActivityView;

/**
 * Created by smile_x on 2016/10/26.
 */

public class SplashActivityPresenter extends BasePresenter<SplashActivityView> implements Runnable {

    private Handler handler = new Handler();

    public SplashActivityPresenter(SplashActivityView model) {
        super(model);
    }

    @Override
    public void initData() {

    }

    public void startMenuActivity() {
        if (MyApplication.getInstance().mPreferenceUtil.isFisrt()) {
              model.gotFirstActivity();
        } else {
            handler.postDelayed(this, 2000);
        }
    }

    public void stopTime() {
        handler.removeCallbacks(this);
    }

    @Override
    public void run() {
        model.gotoMainActivity();

    }
}
