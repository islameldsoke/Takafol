package com.example.moham.takafol;

import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class SplashActivity extends AwesomeSplash {



    @Override
    public void initSplash(ConfigSplash configSplash) {

            /* you don't have to override every property */

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.primary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1300); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.takaful_splash); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000);//int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)




        //Customize Title
        configSplash.setTitleSplash("");


    }

    @Override
    public void animationsFinished() {

        //transit to another activity here
        //or do whatever you want

        Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
