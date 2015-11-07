package com.group2.dungeonraider.data;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.group2.dungeonraider.R;
import com.group2.dungeonraider.controller.Help;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.service.Audio;
import com.group2.dungeonraider.service.AudioImpl;
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

	Audio audio = new AudioImpl();
	MediaPlayer stereo= null;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{


		super.onCreate(savedInstanceState);
		
		Context mContext = getApplicationContext();
		Constants.appContext = getApplicationContext();

		if(Constants.VOLUME_MODE == 1) {

			if(stereo!=null)
			{
				stereo.reset();
				stereo.release();
			}

			stereo = MediaPlayer.create(Constants.appContext, R.raw.game);
			stereo.start();
			stereo.setLooping(true);
		}
	//	audio.play(Constants.appContext, R.raw.game);
	//	audio.setloop(Constants.appContext, R.raw.game);
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

		
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		Intent i = null;
//
//		switch (item.getItemId())
//
//		{
//			case R.id.btnHowToPlay:
//				i = new Intent(this, Help.class);
//    			startActivity(i);
//				return true;
//			case R.id.btnClose:
//				finish();
//				return true;
//		}
//
//		return false;
//	}
	
	/**
	 * Invoked when the Activity loses user focus.
	 */


	@Override
	public void onResume() {

		super.onResume();
		if(Constants.VOLUME_MODE == 1) {

			if(stereo!=null)
			{
				stereo.reset();
				stereo.release();

			}
			stereo = MediaPlayer.create(Constants.appContext, R.raw.game);
			stereo.start();
			stereo.setLooping(true);


		}

	}

	@Override
	protected void onPause() {

		super.onPause();
		if(Constants.VOLUME_MODE ==1) {
			stereo.stop();
		}

	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();

		if(Constants.VOLUME_MODE ==1) {
			stereo.stop();
			//Toast.makeText(this, "volume play", Toast.LENGTH_SHORT).show();
		}


		Play.this.finish();


	}


	}

