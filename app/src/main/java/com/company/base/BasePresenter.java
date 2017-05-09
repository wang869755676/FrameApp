package com.company.base;



public abstract class BasePresenter<D extends BaseViewController> {


    public D model;

    /**
     * 在子类的构造函数中，设定参数为model，这时候可以presenter调用接口来实现对界面的操作。
     */
    public BasePresenter(D model) {
        this.model = model;
    }

    public abstract void initData();


}
