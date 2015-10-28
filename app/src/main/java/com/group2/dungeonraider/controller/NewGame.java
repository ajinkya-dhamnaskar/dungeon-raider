package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/**
 *
 * Created by Amruta on 10/27/2015.
 */
public class NewGame extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);

    }


    public void gotolevel(View v)
    {
        Intent i=new Intent(this,Level.class);
        startActivity(i);
    }

    public void backtomainnewgame(View v)
    {
        NewGame.this.finish();

    }

    public void onBackPressed() {
        Log.d("Newgame", "onBackPressed Called");

        NewGame.this.finish();
        return;
    }


}
