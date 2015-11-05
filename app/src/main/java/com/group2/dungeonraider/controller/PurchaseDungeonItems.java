package com.group2.dungeonraider.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.data.DatabaseHelper;
import com.group2.dungeonraider.domain.Item;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.utilities.Constants;

import java.util.List;

/**
 * Created by ukara on 10/28/2015.
 */
public class PurchaseDungeonItems extends Activity {

    DatabaseHelper databaseHelper = new DatabaseHelper(Constants.appContext);;
    Player p = Player.getInstance();
    List<Item> lstItem = databaseHelper.getAllItems();
    int mapCost = Constants.ITEM_MAP_VALUE;
    int keyCost = Constants.ITEM_KEY_VALUE;
    int bombCost = Constants.ITEM_BOMB_VALUE;
    int potionCost = Constants.ITEM_POTION_VALUE;
    TextView textView;
    int keyCount = p.getItemCount(Constants.ITEM_KEY);
    int bombsCount = p.getItemCount(Constants.ITEM_BOMB);
    int potionCount = p.getItemCount(Constants.ITEM_POTION);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PurchaseDungeonItems", "onCreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_dungeon_items);
        textView = (TextView)findViewById(R.id.textView_goldvalue);
        textView.setText(Integer.toString(p.getGold()));
        textView = (TextView)findViewById(R.id.textViewKeysOwned);
        textView.setText(Integer.toString(keyCount));
        textView.setGravity(Gravity.CENTER);
        textView = (TextView)findViewById(R.id.textViewPotionsOwned);
        textView.setText(Integer.toString(potionCount));
        textView.setGravity(Gravity.CENTER);
        textView = (TextView)findViewById(R.id.textViewBombsOwned);
        textView.setText(Integer.toString(bombsCount));
        textView.setGravity(Gravity.CENTER);

    }

    public void buydungeonmap(View v)
    {
        Log.d("PurchaseDungeonItems", "buydungeonmap called");
        if(p.getGold() >= mapCost)
        {
            p.setGold(p.getGold() - mapCost);
            databaseHelper.updatePlayerGoldVlaue(p);
            TextView textView = (TextView) findViewById(R.id.textView_goldvalue);
            textView.setText(Integer.toString(p.getGold()));
        }
        else
        {
            Toast.makeText(this, "Not enough gold to purchase map", Toast.LENGTH_LONG).show();
        }

    }

    public void buydungeonkey (View v)
    {
        Log.d("PurchaseDungeonItems", "buydungeonkey called");
        if(p.getGold() >= keyCost)
        {
            p.setGold(p.getGold() - keyCost);
            p.setItemCount(Constants.ITEM_KEY, keyCount + 1);
            databaseHelper.updatePlayerGoldVlaue(p);
            databaseHelper.updatePlayerItemCount(p, Constants.ITEM_KEY);
            textView = (TextView)findViewById(R.id.textView_goldvalue);
            textView.setText(Integer.toString(p.getGold()));
            TextView textViewKeys = (TextView)findViewById(R.id.textViewKeysOwned);
            textViewKeys.setText(Integer.toString(p.getItemCount(Constants.ITEM_KEY)));
        }

        else
        {
            Toast.makeText(this, "Not enough gold to purchase key", Toast.LENGTH_LONG).show();
        }
    }

    public void buydungeonpotion (View v)
    {
        Log.d("PurchaseDungeonItems", "buydungeonoptions called");
        if(p.getGold() >= potionCost) {
            p.setGold(p.getGold() - potionCost);
            p.setItemCount(Constants.ITEM_POTION, potionCount + 1);
            databaseHelper.updatePlayerGoldVlaue(p);
            databaseHelper.updatePlayerItemCount(p, Constants.ITEM_POTION);
            textView = (TextView) findViewById(R.id.textView_goldvalue);
            textView.setText(Integer.toString(p.getGold()));
            TextView textViewPotions = (TextView) findViewById(R.id.textViewPotionsOwned);
            textViewPotions.setText(Integer.toString(p.getItemCount(Constants.ITEM_POTION)));
        }

        else
        {
            Toast.makeText(this, "Not enough gold to purchase potion", Toast.LENGTH_LONG).show();
        }
    }

    public void buydungeonbombs (View v)
    {
        Log.d("PurchaseDungeonsItems", "buydungeonbombs Called");
        if(p.getGold() >= bombCost)
        {
            p.setGold(p.getGold() - bombCost);
            p.setItemCount(Constants.ITEM_BOMB, bombsCount + 1);
            databaseHelper.updatePlayerGoldVlaue(p);
            databaseHelper.updatePlayerItemCount(p, Constants.ITEM_BOMB);
            textView = (TextView)findViewById(R.id.textView_goldvalue);
            textView.setText(Integer.toString(p.getGold()));
            TextView textViewBombs = (TextView)findViewById(R.id.textViewBombsOwned);
            textViewBombs.setText(Integer.toString(p.getItemCount(Constants.ITEM_BOMB)));
        }
        else
        {
            Toast.makeText(this, "Not enough gold to purchase bomb", Toast.LENGTH_LONG).show();
        }

    }

    public void backtostorescreen(View v)
    {
        Log.d("PurchaseDungeonsItems", "onBackPressed Called");
        PurchaseDungeonItems.this.finish();
    }
}
