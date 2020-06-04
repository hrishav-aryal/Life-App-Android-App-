package com.example.life;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;

public class handleFirstTimeAppStart extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preferences = getSharedPreferences("preference", MODE_PRIVATE);
        Boolean firstTime = preferences.getBoolean("firstTime", true);

        if(true || firstTime){
            chanagePref();
            Intent intent = new Intent(handleFirstTimeAppStart.this, info.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(handleFirstTimeAppStart.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void chanagePref() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstTime", false);
        editor.apply();
    }
}
