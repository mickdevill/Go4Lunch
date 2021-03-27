package com.mickdevil.go4lunch.TreadManager;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.mickdevil.go4lunch.UI.G4LunchMain;

public class HavyTasksThread extends HandlerThread {

    public HavyTasksThread(String name) {
        super(name);
    }


}
