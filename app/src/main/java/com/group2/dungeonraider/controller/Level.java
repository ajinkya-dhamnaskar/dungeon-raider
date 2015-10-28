package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.data.Play;

/**
 * Created by Amruta on 10/27/2015.
 */
public class Level  extends Activity {


    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

    }



    public void onBackPressed() {
        Log.d("Level", "onBackPressed Called");

        Level.this.finish();
        return;
    }

    public void backtonewgame(View v)
    {
        Level.this.finish();

    }

    public void go(View v)
    {

        radioGroup = (RadioGroup) findViewById(R.id.radioGrouplevel);


        int selectedId = radioGroup.getCheckedRadioButtonId();

        if(selectedId==R.id.radiostore)
        {
            Intent i=new Intent(this,Store.class);
            startActivity(i);
        }



       else if(selectedId==R.id.radioeasy){

            Intent i=new Intent(this,Play.class);
            startActivity(i);

        }
            
    }
}
