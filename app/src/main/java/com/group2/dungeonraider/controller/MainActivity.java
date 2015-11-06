package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.group2.dungeonraider.R;
import com.group2.dungeonraider.service.Audio;
import com.group2.dungeonraider.service.AudioImpl;
import com.group2.dungeonraider.utilities.Constants;

/*
Entry point
 */

public class MainActivity extends Activity {

    Audio audio = new AudioImpl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("VOLUME", MODE_PRIVATE);
        Constants.VOLUME_MODE = preferences.getInt("volume", 0);
        Constants.THEME_MODE = preferences.getString("theme", "BLACK");
        Constants.CHARACTER_SELECTED = preferences.getInt("character", 0);
       
        Constants.appContext = getApplicationContext();
        setContentView(R.layout.activity_main);
    }

    public void setting(View v) {
        Intent i = new Intent(this, Setting.class);
        audio.play(getApplicationContext(), R.raw.btn_click);
        startActivity(i);
    }

    public void help(View v) {
        Intent i = new Intent(this, Help.class);
        audio.play(getApplicationContext(), R.raw.btn_click);
        startActivity(i);
    }

    public void newgame(View v) {
        Intent i = new Intent(this, NewGame.class);
        audio.play(getApplicationContext(), R.raw.btn_click);
        startActivity(i);
    }

//    public void theme(View v)
//    {
//
//        Spinner staticSpinner = (Spinner) findViewById(R.id.spn_theme);
//
//        // Create an ArrayAdapter using the string array and a default spinner
//        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
//                .createFromResource(this, R.array.theme_array,
//                        android.R.layout.simple_spinner_item);
//
//        // Specify the layout to use when the list of choices appears
//        staticAdapter
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // Apply the adapter to the spinner
//        staticSpinner.setAdapter(staticAdapter);
//
//
//
//
//
//        }


    public void quit(final View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Are you sure you want to quit the game");

        audio.play(getApplicationContext(), R.raw.btn_click);
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity

                        MainActivity.this.finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void scorecard(View v) {
        Intent i = new Intent(this, ScoreCard.class);
        audio.play(getApplicationContext(), R.raw.btn_click);
        startActivity(i);
    }
}

