package com.mickdevil.go4lunch.UI.botoomNavStaf.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Geolocation {
    public List<Double> latlngList = new ArrayList<>();

public static void  getAddress(final Context context, Handler handler){
Thread thread = new Thread(){

    @Override
    public void run() {
        super.run();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
      String myAddress = "myAddres";
double[] latlng = new  double[2];
       try {
            List addressList = geocoder.getFromLocationName(myAddress, 1);
if (addressList != null && addressList.size() > 0){
Address address = (Address) addressList.get(0);

latlng[0] = (address.getLatitude());
    latlng[1] = (address.getLongitude());


}
else {
    //need to open dialog and ask user to enable the geolacation
}


        } catch (IOException e) {
            e.printStackTrace();
        }
       finally {
           Message message = Message.obtain();
           message.setTarget(handler);
       message.what = 1;
           Bundle bundle = new Bundle();
//           bundle.putDoubleArray(MapFragment.key, latlng);

           message.sendToTarget();
       }

    }

};
thread.start();

}
}


