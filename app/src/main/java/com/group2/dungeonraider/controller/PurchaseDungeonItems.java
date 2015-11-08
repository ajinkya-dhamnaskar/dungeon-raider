package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.group2.dungeonraider.R;
import com.group2.dungeonraider.data.DatabaseHelper;
import com.group2.dungeonraider.domain.Item;
import com.group2.dungeonraider.domain.Mutator;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.service.Audio;
import com.group2.dungeonraider.service.AudioImpl;
import com.group2.dungeonraider.utilities.Constants;

import java.util.List;

/**
 * Created by ukara on 10/28/2015.
 */
public class PurchaseDungeonItems extends Activity {

    DatabaseHelper databaseHelper = new DatabaseHelper(Constants.appContext);
    Audio audio  = new AudioImpl();
    Player p = Player.getInstance();
    List<Item> lstItem = databaseHelper.getAllItems();

    TextView textViewGold;
    TextView textViewKeys;
    TextView textViewPotion;
    TextView textViewBombs;
    TextView textCosts;
    ImageButton btn;
    int mapCost;
    int keyCost;
    int potionCost;
    int bombCost;
    int potionCount;
    int keyCount;
    int bombsCount;
    int mapCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PurchaseDungeonItems", "onCreate called");
        super.onCreate(savedInstanceState);
        loadStore();
    }

    public void loadStore() {
        Log.d("PurchaseDungeonsItems", "loadStore Called");

        mapCost = Constants.ITEM_MAP_VALUE;
        keyCost = Constants.ITEM_KEY_VALUE;
        bombCost = Constants.ITEM_BOMB_VALUE;
        potionCost = Constants.ITEM_POTION_VALUE;

        potionCount = p.getItemCount(Constants.ITEM_POTION);
        keyCount = p.getItemCount(Constants.ITEM_KEY);
        bombsCount = p.getItemCount(Constants.ITEM_BOMB);
        mapCount = p.getItemCount(Constants.ITEM_MAP);

        setContentView(R.layout.purchase_dungeon_items);
        textViewGold = (TextView) findViewById(R.id.textView_goldvalue);
        textViewGold.setText(Constants.DOLLAR+Integer.toString(p.getGold()));
        textViewKeys = (TextView) findViewById(R.id.textViewKeysOwned);
        textViewKeys.setText(Integer.toString(keyCount));
        textViewKeys.setGravity(Gravity.CENTER);
        textViewPotion = (TextView) findViewById(R.id.textViewPotionsOwned);
        textViewPotion.setText(Integer.toString(potionCount));
        textViewPotion.setGravity(Gravity.CENTER);
        textViewBombs = (TextView) findViewById(R.id.textViewBombsOwned);
        textViewBombs.setText(Integer.toString(bombsCount));
        textViewBombs.setGravity(Gravity.CENTER);
        textCosts = (TextView) findViewById(R.id.txtbombcost);
        textCosts.setText(Constants.DOLLAR+Integer.toString(Constants.ITEM_BOMB_VALUE));
        textViewKeys.setGravity(Gravity.CENTER);
        textCosts = (TextView) findViewById(R.id.txtkeycost);
        textCosts.setText(Constants.DOLLAR+Integer.toString(Constants.ITEM_KEY_VALUE));
        textViewKeys.setGravity(Gravity.CENTER);
        textCosts = (TextView) findViewById(R.id.txtpotioncost);
        textCosts.setText(Constants.DOLLAR+Integer.toString(Constants.ITEM_POTION_VALUE));
        textViewKeys.setGravity(Gravity.CENTER);
        textCosts = (TextView) findViewById(R.id.txtmapcost);
        textCosts.setText(Constants.DOLLAR+Integer.toString(Constants.ITEM_MAP_VALUE));
        textViewKeys.setGravity(Gravity.CENTER);
    }

    public void buydungeonmap(View v) {
        Log.d("PurchaseDungeonItems", "buydungeonmap called");
        audio.play(getApplicationContext(), R.raw.btn_click);
        if(p.getItemCount(Constants.ITEM_MAP) >= 1)
            Toast.makeText(this,"You already have an identical map !! Cannot buy another.",Toast.LENGTH_SHORT).show();
        else if (p.getGold() >= mapCost) {
            p.setGold(p.getGold() - mapCost);
            p.setItemCount(Constants.ITEM_MAP, p.getItemCount(Constants.ITEM_MAP) + 1);
            databaseHelper.updatePlayerGoldValue(p);
            databaseHelper.updatePlayerItemCount(Constants.ITEM_MAP, getItemId(Constants.ITEM_MAP));
            Constants.GAME_NO_OF_MAP=p.getItemCount(Constants.ITEM_MAP);
            TextView textView = (TextView) findViewById(R.id.textView_goldvalue);
            textView.setText(Constants.DOLLAR+Integer.toString(p.getGold()));
            btn = (ImageButton) findViewById(R.id.imgBtnMap);

        } else {
            Toast.makeText(this, "Not enough gold to purchase map", Toast.LENGTH_SHORT).show();
        }

    }

    public void buydungeonkey(View v) {
        Log.d("PurchaseDungeonItems", "buydungeonkey called");
        audio.play(getApplicationContext(), R.raw.btn_click);
        if (p.getGold() >= keyCost) {
            p.setGold(p.getGold() - keyCost);
            p.setItemCount(Constants.ITEM_KEY, p.getItemCount(Constants.ITEM_KEY) + 1);
            databaseHelper.updatePlayerGoldValue(p);
            databaseHelper.updatePlayerItemCount(Constants.ITEM_KEY, getItemId(Constants.ITEM_KEY));
            textViewGold = (TextView) findViewById(R.id.textView_goldvalue);
            textViewGold.setText(Constants.DOLLAR+Integer.toString(p.getGold()));
            Constants.GAME_NO_OF_KEYS=p.getItemCount(Constants.ITEM_KEY);
            textViewKeys = (TextView) findViewById(R.id.textViewKeysOwned);
            textViewKeys.setText(Constants.OWNED + Integer.toString(p.getItemCount(Constants.ITEM_KEY)));

        } else {
            Toast.makeText(this, "Not enough gold to purchase key", Toast.LENGTH_SHORT).show();
        }
    }

    public void buydungeonpotion(View v) {
        Log.d("PurchaseDungeonItems", "buydungeonoptions called");
        audio.play(getApplicationContext(), R.raw.btn_click);
        if (p.getGold() >= potionCost) {
            p.setGold(p.getGold() - potionCost);
            p.setItemCount(Constants.ITEM_POTION, p.getItemCount(Constants.ITEM_POTION) + 1);
            databaseHelper.updatePlayerGoldValue(p);
            databaseHelper.updatePlayerItemCount(Constants.ITEM_POTION, getItemId(Constants.ITEM_POTION));
            textViewGold = (TextView) findViewById(R.id.textView_goldvalue);
            textViewGold.setText(Constants.DOLLAR+Integer.toString(p.getGold()));
            textViewPotion = (TextView) findViewById(R.id.textViewPotionsOwned);
            Constants.GAME_NO_OF_POTIONS=p.getItemCount(Constants.ITEM_POTION);
            textViewPotion.setText(Constants.OWNED + Integer.toString(p.getItemCount(Constants.ITEM_POTION)));

        } else {
            Toast.makeText(this, "Not enough gold to purchase potion", Toast.LENGTH_SHORT).show();
        }
    }

    public void buydungeonbombs(View v) {
        Log.d("PurchaseDungeonsItems", "buydungeonbombs Called");
        audio.play(getApplicationContext(), R.raw.btn_click);
        if (p.getGold() >= bombCost) {
            p.setGold(p.getGold() - bombCost);
            p.setItemCount(Constants.ITEM_BOMB, p.getItemCount(Constants.ITEM_BOMB) + 1);
            databaseHelper.updatePlayerGoldValue(p);
            databaseHelper.updatePlayerItemCount(Constants.ITEM_BOMB, getItemId(Constants.ITEM_BOMB));
            textViewGold = (TextView) findViewById(R.id.textView_goldvalue);
            textViewGold.setText(Constants.DOLLAR+Integer.toString(p.getGold()));
            textViewBombs = (TextView) findViewById(R.id.textViewBombsOwned);
            Constants.GAME_NO_OF_BOMBS=p.getItemCount(Constants.ITEM_BOMB);
            textViewBombs.setText(Constants.OWNED + Integer.toString(p.getItemCount(Constants.ITEM_BOMB)));

        } else {
            Toast.makeText(this, "Not enough gold to purchase bomb", Toast.LENGTH_SHORT).show();
        }

    }

    public void backtostorescreen(View v) {
        Log.d("PurchaseDungeonsItems", "onBackPressed Called");
        PurchaseDungeonItems.this.finish();
    }

    public int getItemId(String name) {
        Log.d("PurchaseDungeonsItems", "getItemId Called");
        int value = 0;
        for (Item item : lstItem) {
            if (item.getName().equals(name)) {
                value = item.getId();

            }
        }
        return value;
    }
}
