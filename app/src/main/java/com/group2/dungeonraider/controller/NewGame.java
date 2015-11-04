package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.data.DatabaseHelper;
import com.group2.dungeonraider.domain.Item;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.domain.PlayerItem;
import com.group2.dungeonraider.domain.PlayerView;
import com.group2.dungeonraider.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Amruta on 10/27/2015.
 */
public class NewGame extends Activity {
    private EditText NameEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Boolean openFlag=false;
    DatabaseHelper db;
    Player player = Player.getInstance();
    List<Item> itemList = new ArrayList<Item>();
    List<Item> playerItemList = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);
        NameEditText = (EditText) findViewById(R.id.txt_name);

    }

    public void gotolevel(View v)
    {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupCharacter);

        final String Name=NameEditText.getText().toString();
        if(Name.isEmpty())
        {
            Toast.makeText(this,"Please enter valid Player Name to proceed", Toast.LENGTH_LONG).show();
        }
        else {

            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Select a Character to proceed ", Toast.LENGTH_SHORT).show();
            } else {
                if (selectedId == R.id.radioCharacter1) {
                    //save characarer 1

                    SharedPreferences pref = getApplicationContext().getSharedPreferences("DUNGEON", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putInt("character", 1);
                    editor.commit();
                    Constants.CHARACTER_SELECTED = 1;

                } else if (selectedId == R.id.radioCharacter1) {

                    //save characarer 2
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("DUNGEON", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putInt("character", 2);
                    editor.commit();
                    Constants.CHARACTER_SELECTED = 2;
                }
                //check if exists from database....check by name...then load...
                // else create...
                //else  create new..


                db = new DatabaseHelper(getApplicationContext());

                if (db.checkIfPlayerExists(Name)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            NewGame.this);



                    alertDialogBuilder.setTitle(getResources().getString(R.string.loadProfile));




                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    player =  db.loadProfile(Name);

                                    playerItemList =  db.loadPlayerItems(player.getId());
                                    player.setItemList(playerItemList);

                                    launchIntent();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });


                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {

                    player.setName(Name);
                    player.setScore(0);
                    player.setTime(0);
                    player.setGold(Constants.INITIAL_GOLD);


                    long returnValue = db.addPlayerDetail(player);

                    //insert blank entries in player item table.

                    if (returnValue == -1) {
                        Toast.makeText(this, "Player Already Exists", Toast.LENGTH_LONG).show();
                    } else if (returnValue == -2 || returnValue == -3) {
                        Toast.makeText(this, "Error occurred while creating player", Toast.LENGTH_LONG).show();
                    } else {

                        player =  db.loadProfile(Name);


                        itemList=db.getAllItems();
                        if (!itemList.isEmpty()) {
                            for (Item i : itemList)
                            {
                                PlayerItem player_item= new PlayerItem();
                                player_item.setPlayerId(player.getId());
                                player_item.setItemId(i.getId());
                                player_item.setItemQuantity(0);
                                db.insertDefaultPlayerItem(player_item);
                            }
                        }

                        playerItemList =  db.loadPlayerItems(player.getId());
                        player.setItemList(playerItemList);
                        Intent i = new Intent(this, Level.class);
                        openFlag=false;
                        startActivity(i);
                    }


                }

            }
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
    private void launchIntent() {
        Intent i = new Intent(this, Level.class);
        openFlag=false;
        startActivity(i);
    }


}
