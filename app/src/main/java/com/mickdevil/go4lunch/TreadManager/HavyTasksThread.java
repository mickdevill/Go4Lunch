package com.mickdevil.go4lunch.TreadManager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.mickdevil.go4lunch.UI.G4LunchMain;

public class HavyTasksThread extends HandlerThread {

    HandlerForMsg handlerForMsg;
Context context;
Activity mActivity;
    public HavyTasksThread(String name, Context context, Activity mActivity) {
        super(name);
   this.context = context;
    this.mActivity = mActivity;
    }
    @Override
    protected void onLooperPrepared() {
        handlerForMsg = new HandlerForMsg(context, mActivity);
    }

    public HandlerForMsg getHuendler() {
        return handlerForMsg ;
    }

}
