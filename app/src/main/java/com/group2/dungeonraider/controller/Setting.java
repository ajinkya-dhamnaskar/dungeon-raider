package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amruta on 10/23/2015.
 */

public class Setting extends Activity implements AdapterView.OnItemSelectedListener {
    Switch ch,ch1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //volume


        ch=(Switch) findViewById(R.id.switch_volume);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("VOLUME", MODE_PRIVATE);

     //   Log.d(LOG, "onCreate() -> Get current volume mode.");

        Constants.VOLUME_MODE=preferences.getInt("volume", 0);

      //  Log.d(LOG,"onCreate() -> Current volume mode. Mode : " + volume);


        // checkbox check
        if(Constants.VOLUME_MODE==1){
            ch.setChecked(true);
        }

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner_theme);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("BROWN");
        categories.add("RED");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
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
            Constants.VOLUME_MODE = 1;
        }
        else
        {
            editor.putInt("volume", 0);
            Toast.makeText(this, "Volume off", Toast.LENGTH_SHORT).show();
            Constants.VOLUME_MODE = 0;
            editor.commit();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void onBackPressed() {
        Log.d("Level", "onBackPressed Called");

        Setting.this.finish();
        return;
    }

    public void backtomainnewgame(View v)
    {
        Setting.this.finish();

    }

}
