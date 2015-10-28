package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by Amruta on 10/27/2015.
 */
public class Level  extends Activity {
    //hello amruta
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

    }

    public void backtonewgame(View v)
    {
        Level.this.finish();
    }

    public void onBackPressed() {
        Log.d("Level", "onBackPressed Called");

        Level.this.finish();
        return;
    }
}
