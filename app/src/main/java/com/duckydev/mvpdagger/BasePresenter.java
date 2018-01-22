package com.duckydev.mvpdagger;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();

}
