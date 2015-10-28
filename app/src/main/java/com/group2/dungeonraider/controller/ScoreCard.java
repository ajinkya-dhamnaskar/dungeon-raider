package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by Amruta on 10/27/2015.
 */
public class ScoreCard extends Activity{
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_scorecard);


        }

    public void backtomainmenuscoreboard(View v)
    {
        ScoreCard.this.finish();
    }

    public void onBackPressed() {
        Log.d("Scorecard", "onBackPressed Called");

        ScoreCard.this.finish();
        return;
    }

}
