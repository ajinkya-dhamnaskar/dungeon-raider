package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.dungeonmainmenu.R;

/**
 *
 * Created by Amruta on 10/27/2015.
 */
public class NewGame extends Activity {
    private EditText NameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);
        NameEditText = (EditText) findViewById(R.id.txt_name);

    }

    public void gotolevel(View v)
    {

        final String Name=NameEditText.getText().toString();
        if(Name.isEmpty())
            {
                Toast.makeText(this,"Please enter valid Player Name to proceed", Toast.LENGTH_LONG).show();
            }
        else
            {
            Intent i = new Intent(this, Level.class);
            startActivity(i);
            }
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
