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

            setContentView(R.layout.activity_scoreboard);

//            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ScoreCard.this);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("username", "CHAAVA");
//            editor.putInt("score", 12);
//            editor.commit();
//
//            String gameUsername = sp.getString("username", "default");
//            int gameScore = sp.getInt("score", 0);
//
//            TextView userView = (TextView)findViewById(R.id.textViewtest1);
//            TextView scoreView = (TextView)findViewById(R.id.textViewtest2);
//
//
//
//            scoreView.setText(gameUsername.toString());
//            userView.setText(gameScore);


//            TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
//
//            TextView rank = (TextView)findViewById(R.id.rank);
//            rank.setText("RANK");
//            TextView percentage = (TextView)findViewById(R.id.percentage);
//            percentage.setText("SCORE");
//            TextView score = (TextView)findViewById(R.id.score);
//            score.setText("TIME");
//
//            TableRow rowHeader = (TableRow)findViewById(R.id.rowHeader);
//
//            rowHeader.addView(rank);  //Line 39
//            rowHeader.addView(percentage);
//            rowHeader.addView(score);
//
//
//
//           //table.addView(rowHeader, new TableLayout.LayoutParams(Layout.getDesiredWidth(300), LayoutParams.WRAP_CONTENT));


        }

    public void backtomainscoreboard(View v)
    {
        ScoreCard.this.finish();
    }

    public void onBackPressed() {
        Log.d("Scorecard", "onBackPressed Called");

        ScoreCard.this.finish();
        return;
    }

}
