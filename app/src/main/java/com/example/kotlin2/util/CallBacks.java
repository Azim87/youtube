package com.example.kotlin2.util;

public interface CallBacks {

    void callbackObserver(Object object);

    interface playerCallBack {
        void onItemClickOnItem(int albumId);

        void onPlayingEnd();
    }
}