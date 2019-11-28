package com.example.youi_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;

import com.google.firebase.auth.FirebaseAuth;

import java.lang.annotation.Annotation;

public class Loginnow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginnow);

        Thread timer = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Loginnow.this,Homepage.class));
                    finish();
                }
            }
        };
        timer.start();
    }
}
