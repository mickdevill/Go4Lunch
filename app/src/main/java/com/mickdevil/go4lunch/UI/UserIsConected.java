package com.mickdevil.go4lunch.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickdevil.go4lunch.R;

import java.util.ArrayList;
import java.util.List;

public class UserIsConected extends AppCompatActivity {

    public static final String key = "getUserInfo";


ImageView userAva;
TextView userName;
Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_is_conected);
userAva = findViewById(R.id.userAva);
userName = findViewById(R.id.userName);

logOut = findViewById(R.id.logOut);


    }


    public static void startAndGet(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), UserIsConected.class);
        activity.startActivity(intent);


    }

}