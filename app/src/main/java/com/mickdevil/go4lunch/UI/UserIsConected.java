package com.mickdevil.go4lunch.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mickdevil.go4lunch.R;

import java.util.ArrayList;
import java.util.List;

public class UserIsConected extends AppCompatActivity {

    public static final String key = "getUserInfo";

    private static List<String> userInfo;

TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_is_conected);

userName = findViewById(R.id.userName);
userName.setText(userInfo.get(0));


    }


    public static void startAndGet(Activity activity, ArrayList<String> userData) {
//Bundle  info = new Bundle();
//        info.putStringArrayList(key, userData);

      userInfo = userData;

        Intent intent = new Intent(activity.getApplicationContext(), UserIsConected.class);
        activity.startActivity(intent);


    }

}