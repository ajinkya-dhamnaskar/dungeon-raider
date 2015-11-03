package com.group2.dungeonraider.controller;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.utilities.Constants;

/**
 * Created by ukara on 10/27/2015.
 */
public class Help extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
    }

    public void backtomain(View v) {
        Help.this.finish();
    }

    public void howtoplay(View v) {
        String alertTitle = getResources().getString(R.string.help_how_to_play);
        ;
        String alertMessage = getResources().getString(R.string.help_how_to_play_details);
        displayAlert(alertTitle, alertMessage, false);
    }

    public void tips(View view) {
        String alertTitle = getResources().getString(R.string.help_tips);
        String alertMessage = getResources().getString(R.string.help_tips_details);
        displayAlert(alertTitle, alertMessage, false);
    }

    public void aboutthegame(View view) {
        final String alertTitle = getResources().getString(R.string.help_about_the_game);
        final String alertMessage = getResources().getString(R.string.help_about_the_game_details);
        displayAlert(alertTitle, alertMessage, true);
    }

    public void displayAlert(String alertTitle, String alertMessage, boolean centerAligned)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage(alertMessage);
        builder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.show();

        // Must call show() prior to fetching text view
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        if(centerAligned == true)
            messageView.setGravity(Gravity.CENTER);

    }
}
