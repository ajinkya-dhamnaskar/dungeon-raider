package com.group2.dungeonraider.controller;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

import java.util.ArrayList;

/**
 * Created by Rohit on 10/25/2015.
 */
public class ScoreCard extends Activity {
    MediaPlayer mp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);


      //  TextView scoreView = (TextView)findViewById(R.id.high_scores_list);
        mp1= MediaPlayer.create(ScoreCard.this, R.raw.cointake);
        mp1.start();

         ArrayList<String> highscores = new ArrayList<String>();

    }



    public void backtomain(View v)
    {

        ScoreCard.this.finish();
}


    public void onBackPressed() {
        ScoreCard.this.finish();
    }
}
