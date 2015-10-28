package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/*
Entry point
 */
public class MainActivity extends AppCompatActivity {

public class MainActivity extends Activity {
//asdfasdfasdfasdzx
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setting(View v)
    {
        Intent i=new Intent(this,Setting.class);
        startActivity(i);
    }

    public void help(View v)
    public void scorecard(View v)
    {
        Intent i=new Intent(this,Help.class);
        startActivity(i);
    }

    public void newgame(View v)
    {
        Intent i=new Intent(this,NewGame.class);
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




    public void quit(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                MainActivity.this);

        // set title
        alertDialogBuilder.setTitle("Are you sure you want to quit the game");


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
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

}


