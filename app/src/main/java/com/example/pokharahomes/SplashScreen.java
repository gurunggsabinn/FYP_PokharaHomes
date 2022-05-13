package com.example.pokharahomes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokharahomes.utils.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    //initializing variables
    Animation topAnim, bottomAnim;
    ImageView image, title;
    TextView slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Assigning Variables
        image = findViewById(R.id.imageView);
        title = findViewById(R.id.imageView2);
        slogan = findViewById(R.id.textView2);

        //Setting fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initializing top animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        //Initializing bottom animation
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //starting Top animation
        image.setAnimation(topAnim);
        //starting Bottom animation
        slogan.setAnimation(bottomAnim);
        title.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN);
    }
}