package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by Amruta on 10/23/2015.
 */

public class Setting extends Activity {
    MediaPlayer mp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mp1= MediaPlayer.create(Setting.this, R.raw.cointake);
        mp1.start();
    }

}
