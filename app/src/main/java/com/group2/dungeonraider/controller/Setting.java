package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by Amruta on 10/23/2015.
 */

public class Setting extends Activity {
    MediaPlayer mp1;
    Switch ch,ch1;
    private int sound=1;
    int volume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //volume


        ch=(Switch) findViewById(R.id.switch_volume);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("VOLUME", MODE_PRIVATE);
        //SharedPreferences.Editor editor = preferences.edit();

     //   Log.d(LOG, "onCreate() -> Get current volume mode.");

        volume=preferences.getInt("volume", 0);
      //  Log.d(LOG,"onCreate() -> Current volume mode. Mode : " + volume);


        // checkbox check
        if(volume==1){
            ch.setChecked(true);
        }

        if(volume==0)
        {
            sound=0;
        }
        else if(sound==1) {
            mp1 = MediaPlayer.create(Setting.this, R.raw.cointake);
            mp1.start();
        }
    }

    public void changeVolume(View v) {

        ch1 = (Switch)v;
        SharedPreferences pref = getApplicationContext().getSharedPreferences("VOLUME", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(ch1.isChecked())
        {
            editor.putInt("volume", 1);
            editor.commit();
            Toast.makeText(this, "Volume on", Toast.LENGTH_SHORT).show();
        }
        else
        {
            editor.putInt("volume", 0);
            Toast.makeText(this, "Volume off", Toast.LENGTH_SHORT).show();
            editor.commit();
        }
    }

}
