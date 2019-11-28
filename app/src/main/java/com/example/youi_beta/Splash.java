package com.example.youi_beta;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Splash extends AppCompatActivity {
    /*要先到build.gradle(Module.app)導入 implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.19'
    然後在app的graedle重新build的一次 會導入gif的gradle*/
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openflash);
        GifImageView logo = (GifImageView)findViewById(R.id.logo);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(),R.drawable.youi_home);
            logo.setImageDrawable(gifDrawable);
        }catch (Exception e){
                e.printStackTrace();
        }
        Animation fadein= AnimationUtils.loadAnimation(Splash.this,R.anim.fadeln);

        logo.setAnimation(fadein);

        Thread timer= new Thread()
        {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(Splash.this,Homepage.class));
                    finish();
                }
            }
        };
        timer.start();


    }

}
