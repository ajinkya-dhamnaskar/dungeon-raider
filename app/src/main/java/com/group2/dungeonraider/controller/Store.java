package com.group2.dungeonraider.controller;

/**
 * Created by Rohit on 10/28/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test.dungeonmainmenu.R;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.test.dungeonmainmenu.R;

/**
 * Created by Rohit on 10/27/2015.
 */
public class Store extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Store", "onCreate Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

    }

    public void browsedungeonitems (View v)
    {
        Log.d("Store", "BrowseDungeonItems Called");
        Intent i = new Intent(this, PurchaseDungeonItems.class);
        startActivity(i);
    }

    public void backtolevel(View v)
    {
        Log.d("Store", "BackToLevel Called");
        Store.this.finish();
    }

    public void onBackPressed() {
        Log.d("Store", "onBackPressed Called");

        Store.this.finish();
        return;
    }

    public void viewequipmutator(View view)
    {

    }
}
