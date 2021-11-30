package com.example.android.querymaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;




public class Splash_Screen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        Runnable runnable = () -> {
            Intent intent = new Intent(Splash_Screen_Activity.this, Main_Page.class);
            startActivity(intent);
        };
        handler.postDelayed(runnable, 500);
    }
}
