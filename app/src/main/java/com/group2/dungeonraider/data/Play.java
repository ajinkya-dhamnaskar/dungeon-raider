package com.group2.dungeonraider.data;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

import com.group2.dungeonraider.R;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.service.Audio;
import com.group2.dungeonraider.service.AudioImpl;
import com.group2.dungeonraider.utilities.Constants;
import com.group2.dungeonraider.utilities.Utils;

public class Play extends Activity
{
	private GameView mGameView = null;

	private DisplayMetrics mMetrics = new DisplayMetrics();
	private float mScreenDensity;
	public Play getPlayeActivity(){
		return  this;
	}
	//public static Play end;
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

		Log.d("Tile Game Example", "Starting game at stage: " + Constants.STAGE + ", level: " + Constants.GAME_LEVEL);
		if(Constants.IS_PLAYER_LEVEL){
			mGameView = new GameView(mContext, this, mScreenDensity);
		}else{
			Utils.clearGameData();
			Constants.GAME_NO_OF_POTIONS = Player.getInstance().getItemCount(Constants.ITEM_POTION);
			Constants.GAME_NO_OF_MAP = Player.getInstance().getItemCount(Constants.ITEM_MAP);
			Constants.GAME_NO_OF_BOMBS = Player.getInstance().getItemCount(Constants.ITEM_BOMB);
			Constants.GAME_NO_OF_KEYS = Player.getInstance().getItemCount(Constants.ITEM_KEY);
			Constants.PLAYER_GOLD = Player.getInstance().getGold();
			Constants.PLAYER_SCORE = Player.getInstance().getScore();
			Constants.GAME_START_TIME = System.currentTimeMillis();
			mGameView = new GameView(mContext, this, Constants.STAGE, Constants.GAME_LEVEL, mScreenDensity);

		}

		setContentView(mGameView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();

		
		return true;
	}

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

