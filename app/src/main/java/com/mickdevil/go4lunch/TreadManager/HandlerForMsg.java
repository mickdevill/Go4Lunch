package com.mickdevil.go4lunch.TreadManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mickdevil.go4lunch.UI.G4LunchMain;
import com.mickdevil.go4lunch.GetPlases.GetPlacesTheRightWay;
import com.mickdevil.go4lunch.UI.botoomNavStaf.GetPlaces;
import com.mickdevil.go4lunch.UI.botoomNavStaf.map.MapFragment;

public class HandlerForMsg extends Handler {

Context context;

    public HandlerForMsg(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(@NonNull Message TaskCode) {
        switch (TaskCode.what){
            case 0:
                GetPlaces getPlaces = new GetPlaces(G4LunchMain.getLocationProviderClient(), G4LunchMain.getClient(), G4LunchMain.getApikey()
                , context);
                getPlaces.getPlacesLikeHood();
                break;

            case 1:
       MapFragment.getPlaces();
                break;

        }


    }
}
