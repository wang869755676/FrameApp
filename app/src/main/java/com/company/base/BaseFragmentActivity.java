package com.company.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseFragmentActivity<T extends BasePresenter, M extends BaseViewController> extends FragmentActivity{

    public Context mContext;   //吐司的上下文

    protected T mPresenter;     //主持人角色

    protected abstract T initPresenter();    //获取到主持人


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();    //初始化Presenter，提供主持人，拥有主持人后才能提交界面数据给presenter
        setContentView(setLayoutId());
        mContext=getApplicationContext();
        ButterKnife.bind(this);
        initView();
        mPresenter.initData();
        initEvent();
        doOther();
    }

    /**
     *  处理其他的一些事件
     */
    protected void doOther() {

    }

    /**
     *  设置监听
     */
    protected abstract void initEvent();

    /**
     *  查找控件
     */
    protected abstract void initView();

    /**
     *  设置布局
     * @return
     */
    protected abstract int setLayoutId();

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {   //返回按钮点击事件
        //当Activity中的 进度对话框正在旋转的时候（数据正在加载，网络延迟高，数据难以加载）,关闭 进度对话框 ， 然后可以手动执行重新加载

        super.onBackPressed();
    }

    /**
     * 恢复界面后,我们需要判断我们的presenter是不是存在,不存在则重置presenter
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null)
            mPresenter = initPresenter();
    }

    /**
     * onDestroy中销毁presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter = null;
    }

}
