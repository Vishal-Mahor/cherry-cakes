package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    ImageView logo, back,shopping;
    LottieAnimationView lottieAnimationView;
    private static int SPLASH_SCREEN = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);

        back = findViewById(R.id.back_scene);
        lottieAnimationView = findViewById(R.id.splash_motion);

        back.animate().translationY(-2800).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(1900).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1900).setDuration(1000).setStartDelay(4000);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, welcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}