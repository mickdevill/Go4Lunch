package com.mickdevil.go4lunch.TreadManager;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.mickdevil.go4lunch.UI.G4LunchMain;

public class HavyTasksThread extends HandlerThread {

    HandlerForMsg handlerForMsg;
Context context;

    public HavyTasksThread(String name, Context context) {
        super(name);
   this.context = context;
    }
    @Override
    protected void onLooperPrepared() {
        handlerForMsg = new HandlerForMsg(context);
    }

    public HandlerForMsg getHuendler() {
        return handlerForMsg ;
    }

}
