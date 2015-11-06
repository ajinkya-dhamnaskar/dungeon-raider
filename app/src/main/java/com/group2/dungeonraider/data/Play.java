package com.group2.dungeonraider.data;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

import com.group2.dungeonraider.R;
import com.group2.dungeonraider.controller.Help;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.utilities.Constants;
import com.group2.dungeonraider.utilities.Utils;


/**
 * The Play activity creates a new GameView instance and starts
 * the game.
 * 
 * @author Dan Ruscoe (ruscoe.org)
 * @version 1.0
 */
public class Play extends Activity
{
	private GameView mGameView = null;

	private DisplayMetrics mMetrics = new DisplayMetrics();
	private float mScreenDensity;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Context mContext = getApplicationContext();
		Constants.appContext = getApplicationContext();

		/**
		 * Get the screen density that all pixel values will be based on.
		 * This allows scaling of pixel values over different screen sizes.
		 * 
		 * See: http://developer.android.com/reference/android/util/DisplayMetrics.html
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
	    mScreenDensity = mMetrics.density;

		/**
		 * There is only one stage / level in this example.
		 * In a real game, the user's chosen stage / level should be
		 * passed to this activity.
		 */
		int stage = 1;
		//Constants.GAME_LEVEL++;
		Log.d("Tile Game Example", "Starting game at stage: " + stage + ", level: " + Constants.GAME_LEVEL);
		mGameView = new GameView(mContext, this, stage, Constants.GAME_LEVEL, mScreenDensity);
		Utils.clearGameData();
		Constants.GAME_NO_OF_POTIONS = Player.getInstance().getItemCount(Constants.ITEM_POTION);
		Constants.GAME_NO_OF_MAP = 5;//Player.getInstance().getItemCount(Constants.ITEM_MAP);
		Constants.GAME_NO_OF_BOMBS = Player.getInstance().getItemCount(Constants.ITEM_BOMB);
		Constants.GAME_NO_OF_KEYS = Player.getInstance().getItemCount(Constants.ITEM_KEY);
		Constants.PLAYER_GOLD = Player.getInstance().getGold();
		Constants.PLAYER_SCORE = Player.getInstance().getScore();
		Constants.GAME_START_TIME = System.currentTimeMillis();
		setContentView(mGameView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
	//	inflater.inflate(R.id.btn_buy_bombs, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent i = null;
		
		switch (item.getItemId())

		{
			case R.id.btnHowToPlay:
				i = new Intent(this, Help.class);
    			startActivity(i);
				return true;
			case R.id.btnClose:
				finish();
				return true;
		}

		return false;
	}
	
	/**
	 * Invoked when the Activity loses user focus.
	 */
	@Override
	protected void onPause()
	{
		super.onPause();
		//Constants.DELAY_LAST_TIME = System.currentTimeMillis() - Constants.LAST_TIME;
		//mGameView.getThread().setState(GameView.STATE_PAUSED); // pause game when Activity pauses

	}

	@Override
	public void onResume() {
		super.onResume();

		//mGameView.getThread().setState(GameView.STATE_RUNNING);
	}


}