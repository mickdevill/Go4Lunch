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
    Activity mActivity;

    public HandlerForMsg(Context context, Activity mActivity) {
        this.context = context;
        this.mActivity = mActivity;
    }

    @Override
    public void handleMessage(@NonNull Message TaskCode) {
        switch (TaskCode.what) {
            case 0:


            case 1:
                GetPlacesTheRightWay getPlacesTheRightWay = new GetPlacesTheRightWay(MapFragment.locationForPlaces);
                getPlacesTheRightWay.getPlaces();
                break;

            case 2:
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MapFragment.PutMarckersOnMap(GetPlacesTheRightWay.finalPlacesResult);
                    }
                });
                break;

        }


    }
}
