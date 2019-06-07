package com.app.shipapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.shipapp.R;
import com.app.shipapp.app_utils.ConstantMethod;

public class SplashScreen extends AppCompatActivity {
    public static final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                String isLogedIn = ConstantMethod.getStringPreference("login_status",SplashScreen.this);
                if(isLogedIn.equals("login")){
                    Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(mainIntent);
                }
                else {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
