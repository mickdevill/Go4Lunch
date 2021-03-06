package com.mickdevil.go4lunch.UI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class OtherJsonParser {

    private HashMap<String, String> parseJsonObject(JSONObject object) {

        HashMap<String, String> dataList = new HashMap<>();
        try {
            String name = object.getString("name");

            String latitude = object.getJSONObject("geometry").
        getJSONObject("location").getString("lat");

            String longitude = object.getJSONObject("geometry").
                    getJSONObject("location").getString("lng");

            dataList.put("name", name);
            dataList.put("lat", latitude);
            dataList.put("lng", longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
