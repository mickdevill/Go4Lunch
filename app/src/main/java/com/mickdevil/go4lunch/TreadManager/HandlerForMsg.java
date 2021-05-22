package com.mickdevil.go4lunch.TreadManager;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.UI.botoomNavStaf.map.MapFragment;

public class HandlerForMsg extends Handler {

    Context context;
    Activity activity;

    public HandlerForMsg(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void handleMessage(@NonNull Message TaskCode) {
        switch (TaskCode.what) {
            case 0:


            case 1:
                GetPlacesTheRightWay getPlacesTheRightWay = new GetPlacesTheRightWay(MapFragment.locationForPlaces);
                getPlacesTheRightWay.getPlacesWithGoogle("5000");
                break;

            case 2:
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MapFragment.PutMarckersOnMap(GetPlacesTheRightWay.finalPlacesResult, activity.getApplicationContext());
                    }
                });
                break;

        }


    }
}
