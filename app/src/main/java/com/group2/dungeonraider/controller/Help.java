package com.group2.dungeonraider.controller;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by ukara on 10/27/2015.
 */
public class Help extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }

    public void backtomain(View v)
    {
        Help.this.finish();
    }

    public void howtoplay(View v)
    {
        String alertTitle = "How To Play";
        String alertMessage = "How to Play Details";
        displayAlert(alertTitle, alertMessage);
    }

    public void tips(View view)
    {
        String alertTitle = "Tips";
        String alertMessage = "Tips details";
        displayAlert(alertTitle, alertMessage);
    }

    public void aboutthegame(View view)
    {
        final String alertTitle = "About The Game";
        final String alertMessage = "About the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game detailsAbout the game details";
        displayAlert(alertTitle, alertMessage);
    }

    public void displayAlert(String alertTitle, String alertMessage)
    {
       new AlertDialog.Builder(this).setMessage(alertMessage)
                .setTitle(alertTitle)
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.dismiss();
                            }
                        })
                .show();
    }

}
