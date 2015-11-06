package com.group2.dungeonraider.data;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.group2.dungeonraider.R;
import com.group2.dungeonraider.controller.Level;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.service.Audio;
import com.group2.dungeonraider.service.AudioImpl;
import com.group2.dungeonraider.utilities.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * The game view and main game thread.
 *
 * GameView creates a new thread (GameThread) to handle all calculations
 * and drawing of game components.
 *
 * GameThread contains the run() function, which serves as the game loop,
 * updating each cycle while the game is running.
 *
 * To see how game level data is parsed and turned into a playable, tile level,
 * see the function GameView.parseGameLevelData.
 *
 * @author Dan Ruscoe (ruscoe.org)
 * @version 1.0
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	private static final int CONTROLS_PADDING = 10;

	private static final int START_STAGE = 1;
	private static final int START_LEVEL = 1;

	public static final int STATE_RUNNING = 1;
	public static final int STATE_PAUSED = 2;

	private int mScreenXMax = 0;
	private int mScreenYMax = 0;
	private int mScreenXCenter = 0;
	private int mScreenYCenter = 0;
	private int mScreenXOffset = 0;
	private int mScreenYOffset = 0;

	private float mScreenDensity;

	private Context mGameContext;
	private Play mGameActivity;
	private SurfaceHolder mGameSurfaceHolder = null;

	private boolean updatingGameTiles = false;

	private GameTileData mGameTileData = null;
	private GameLevelTileData mGameLevelTileData = null;

	private PlayerUnit mPlayerUnit = null;

	private int mPlayerStage = START_STAGE;
	private int mPlayerLevel = START_LEVEL;

	private Bitmap mBackgroundImage = null;

	private int mGameState;

	private boolean mGameRun = true;

	private boolean mPlayerMoving = false;
	private int mPlayerVerticalDirection = 0;
	private int mPlayerHorizontalDirection = 0;

	private int mVertDir = 0;
	private int mHorDir = 0;

	private GameUi mCtrlUpArrow = null;
	private GameUi mCtrlDownArrow = null;
	private GameUi mCtrlLeftArrow = null;
	private GameUi mCtrlRightArrow = null;
	private GameUi mCtrlBomb = null;
	private GameUi mctrlPotion = null;
	private GameUi mctrlKey = null;
	private GameUi mctrlMap = null;

	private Paint mUiTextPaint = null;
	private String mLastStatusMessage = "";

	/**
	 * Templates defining all available game tiles.
	 */
	private HashMap<Integer, ArrayList<Integer>> mGameTileTemplates = null;

	/**
	 * Bitmap instances for each game tile type.
	 */
	private HashMap<Integer, Bitmap> mGameTileBitmaps = new HashMap<Integer, Bitmap>();

	/**
	 * GameTile instances for each game tile used by the current level.
	 */
	private List<GameTile> mGameTiles = new ArrayList<GameTile>();

	private int mPlayerStartTileX = 0;
	private int mPlayerStartTileY = 0;

	private int mTileWidth = 0;
	private int mTileHeight = 0;
	Audio audio = new AudioImpl();
	DatabaseHelper db=new DatabaseHelper(Constants.appContext);
	class GameThread extends Thread
	{
		long timeElapsed = 0;
		public GameThread(SurfaceHolder surfaceHolder, Context context,
						  Handler handler)
		{
			mGameSurfaceHolder = surfaceHolder;
			mGameContext = context;

			Resources res = context.getResources();

			if("PURPLE".equals(Constants.THEME_MODE)) {
				mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.bg_purple);
			}
			else
			{
				mBackgroundImage = BitmapFactory.decodeResource(res, R.drawable.canvas_bg_01);
			}
			Display display = mGameActivity.getWindowManager().getDefaultDisplay();
			mScreenXMax = display.getWidth();
			mScreenYMax = display.getHeight();
			mScreenXCenter = (mScreenXMax / 2);
			mScreenYCenter = (mScreenYMax / 2);

			setGameStartState();
		}

		/**
		 * Callback invoked when the surface dimensions change.
		 */
		public void setSurfaceSize(int width, int height)
		{
			// synchronized to make sure these all change atomically
			synchronized (mGameSurfaceHolder)
			{
				mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage,
						width, height, true);
			}
		}

		/*/**
         * Sets the run status of the game loop inside the game thread.
         * @param boolean run - true when game should run, false otherwise.
         */
		public void setRunning(boolean run)
		{
			mGameRun = run;
		}

		/**
		 * Sets the game state to running.
		 */
		public void doStart()
		{
			setState(STATE_RUNNING);
		}

		/*/**
         * Sets the game state
         * @param int mode - May be STATE_RUNNING or STATE_PAUSED
         */
		public void setState(int state)
		{
			mGameState = state;
		}

		/**
		 * Contains the main game loop, which updates all elements of the game.
		 */
		@Override
		public void run()
		{
			while (mGameRun)
			{
				Canvas c = null;
				try
				{
					c = mGameSurfaceHolder.lockCanvas(null);
					synchronized (mGameSurfaceHolder)
					{
						if (mGameState == STATE_RUNNING)
						{
							updatePlayerUnit();
						}

						doDraw(c);
					}
				} finally
				{
					if (c != null)
					{
						mGameSurfaceHolder.unlockCanvasAndPost(c);
					}
				}
			}

			return;
		}

		/**
		 * Pauses the game.
		 */
		public void pause()
		{
			synchronized (mGameSurfaceHolder)
			{
				if (mGameState == STATE_RUNNING)
				{
					setState(STATE_PAUSED);
				}
			}
		}

		/**
		 * Unpauses the game.
		 */
		public void unpause()
		{
			synchronized (mGameSurfaceHolder)
			{
				if (mGameState != STATE_RUNNING)
				{
					setState(STATE_RUNNING);
				}
			}
		}

		/**
		 * Centers the game view around the location of the player unit.
		 */
		private void centerView()
		{
			mPlayerUnit.setUnmodifiedX(mPlayerUnit.getX() + mScreenXCenter);
			mPlayerUnit.setUnmodifiedY(mPlayerUnit.getY() + mScreenYCenter);

			mScreenXOffset = (mPlayerUnit.getX() - mScreenXCenter);
			mScreenYOffset = (mPlayerUnit.getY() - mScreenYCenter);

			mPlayerUnit.setX(mScreenXCenter);
			mPlayerUnit.setY(mScreenYCenter);
		}

		/*/**
         * Draws all visual elements of the game.
         * @param Canvas canvas
         */
		private void doDraw(Canvas canvas) {
			centerView();

			if (canvas != null)
			{
				canvas.drawBitmap(mBackgroundImage, 0, 0, null);

				if (!updatingGameTiles)
				{
					drawGameTiles(canvas);
				}

				if (mPlayerUnit != null)
				{
					canvas.drawBitmap(mPlayerUnit.getBitmap(), mPlayerUnit.getX(),
							mPlayerUnit.getY(), null);
				}

				drawControls(canvas);

				Paint text = new Paint();
				text.setColor(Color.RED);
				text.setTextSize(50);
				long currTime = System.currentTimeMillis();
				if(Constants.IS_SLOW_DOWN_TIMER){
					if(Constants.TICK_COUNTER_FOR_DELAY == 0){
						timeElapsed = (currTime + Constants.DELAY_LAST_TIME) - Constants.GAME_START_TIME;
						Constants.TICK_COUNTER_FOR_DELAY++;
					}else{
						timeElapsed = Constants.LAST_TIME;
						Constants.DELAY_LAST_TIME += (Constants.LAST_CURR_TIME - currTime);
						Constants.TICK_COUNTER_FOR_DELAY++;
						if(Constants.TICK_COUNTER_FOR_DELAY == Constants.MAX_TICK_COUNTER_FOR_DELAY){
							Constants.TICK_COUNTER_FOR_DELAY = 0;
						}
					}

				}else {
					timeElapsed = (currTime + Constants.DELAY_LAST_TIME) - Constants.GAME_START_TIME;
				}
				Constants.TIME_DELAY++;
				if(Constants.TIME_DELAY == Constants.MAX_TIME_DELAY){
					Constants.IS_SLOW_DOWN_TIMER = false;
					Constants.TIME_DELAY=0;
				}
				canvas.drawText((new SimpleDateFormat("mm:ss:SSS")).format(new Date(timeElapsed)).toString(), 20, 50, text);
				Constants.LAST_TIME = timeElapsed;
				Constants.LAST_CURR_TIME = currTime;
				canvas.drawText(mLastStatusMessage, 100, 50, mUiTextPaint);
				canvas.drawText(String.valueOf(Constants.GAME_NO_OF_BOMBS), 100, 140, mUiTextPaint);
				canvas.drawText(String.valueOf(Constants.GAME_NO_OF_POTIONS), 100, 220, mUiTextPaint);
				canvas.drawText(String.valueOf(Constants.GAME_NO_OF_KEYS), 100, 300, mUiTextPaint);
				canvas.drawText(String.valueOf(Constants.GAME_NO_OF_MAP), 100, 380, mUiTextPaint);
			}
		}

		/*/**
         * Draws the game tiles used in the current level.
         * @param Canvas canvas
         */
		private void drawGameTiles(Canvas canvas)
		{
			int gameTilesSize = mGameTiles.size();
			for (int i = 0; i < gameTilesSize; i++)
			{
				if (mGameTiles.get(i) != null)
				{
					mGameTiles.get(i).setX(
							mGameTiles.get(i).getX() - mScreenXOffset);
					mGameTiles.get(i).setY(
							mGameTiles.get(i).getY() - mScreenYOffset);

					if (mGameTiles.get(i).isVisible())
					{
						canvas.drawBitmap(mGameTiles.get(i).getBitmap(),
								mGameTiles.get(i).getX(), mGameTiles.get(i)
										.getY(), null);
					}
				}
			}
		}

		/*/**
         * Draws the game controls.
         * @param Canvas canvas
         */
		private void drawControls(Canvas canvas)
		{
			canvas.drawBitmap(mCtrlUpArrow.getBitmap(), mCtrlUpArrow.getX(), mCtrlUpArrow.getY(), null);
			canvas.drawBitmap(mCtrlDownArrow.getBitmap(), mCtrlDownArrow.getX(), mCtrlDownArrow.getY(), null);
			canvas.drawBitmap(mCtrlLeftArrow.getBitmap(), mCtrlLeftArrow.getX(), mCtrlLeftArrow.getY(), null);
			canvas.drawBitmap(mCtrlRightArrow.getBitmap(), mCtrlRightArrow.getX(), mCtrlRightArrow.getY(), null);
			canvas.drawBitmap(mCtrlBomb.getBitmap(), mCtrlBomb.getX(), mCtrlBomb.getY(), null);
			canvas.drawBitmap(mctrlPotion.getBitmap(), mctrlPotion.getX(), mctrlPotion.getY(), null);
			canvas.drawBitmap(mctrlKey.getBitmap(), mctrlKey.getX(), mctrlKey.getY(), null);
			canvas.drawBitmap(mctrlMap.getBitmap(), mctrlMap.getX(), mctrlMap.getY(), null);
		}

		/**
		 * Updates the direction, position and state of the player unit.
		 */
		private boolean updatePlayerUnit()
		{
			GameTile collisionTile = null;
			if (mPlayerMoving)
			{
				int differenceX = 0;
				int differenceY = 0;
				int newX = mPlayerUnit.getX();
				int newY = mPlayerUnit.getY();

				if (mPlayerHorizontalDirection != 0)
				{
					differenceX = (mPlayerHorizontalDirection == Constants.Direction.RIGHT.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
					newX = (mPlayerUnit.getX() + differenceX);
				}

				if (mPlayerVerticalDirection != 0)
				{
					differenceY = (mPlayerVerticalDirection == Constants.Direction.DOWN.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
					newY = (mPlayerUnit.getY() + differenceY);
				}

				collisionTile = getCollisionTile(newX, newY, mPlayerUnit.getWidth(), mPlayerUnit.getHeight(), null);

				if ((collisionTile != null)&& collisionTile.isBlockerTile() && !(collisionTile.getType() == Constants.BlockType.DOOROPEN.getValue())){

					if(collisionTile.getType() == GameTile.TYPE_SLIDING){
						updateSlidingTile(newX, newY, mPlayerUnit, collisionTile);
					}else {
						handleTileCollision(collisionTile);
					}

				} else
				{
					handleTileCollision(collisionTile);
					mPlayerUnit.setX(newX);
					mPlayerUnit.setY(newY);
				}
			}
			return true;
		}

		private boolean updateSlidingTile(int newX, int newY, PlayerUnit mPlayerUnit, GameTile slidingTile){

			int plyLeft = mPlayerUnit.getX();
			int plyRight =  plyLeft + mPlayerUnit.getWidth();
			int plyTop = mPlayerUnit.getY();
			int plyBottom =  plyTop + mPlayerUnit.getHeight();

			int tileLeft = slidingTile.getX();
			int tileRight =  tileLeft + slidingTile.getWidth();
			int tileTop = slidingTile.getY();
			int tileBottom =  tileTop + slidingTile.getHeight();

			int newXTile = 0;
			int newYTile = 0;
			boolean result = true;

			if(tileTop >= plyBottom){
				//move down
				newXTile = slidingTile.getX();
				newYTile = slidingTile.getY() + Constants.SLIDE_TILE_BY_DP;
			}else
			if(plyTop >= tileBottom){
				//move up
				newXTile = slidingTile.getX();
				newYTile = slidingTile.getY() - Constants.SLIDE_TILE_BY_DP;

			} else if (plyLeft >= tileRight) {
				//move left
				newXTile = slidingTile.getX() - Constants.SLIDE_TILE_BY_DP;
				newYTile = slidingTile.getY();
			}else
			if(tileLeft >= plyRight){
				//move right
				newXTile = slidingTile.getX() + Constants.SLIDE_TILE_BY_DP;
				newYTile = slidingTile.getY();
			}

			if(canSlidingTileMove(newXTile, newYTile, slidingTile.getWidth(), slidingTile.getHeight(), slidingTile)){
				slidingTile.setX(newXTile);
				slidingTile.setY(newYTile);
				//audio.play(Constants.appContext, R.raw.btn_click);
			}else{
				result = false;
			}

			return result;
		}


		private boolean canSlidingTileMove(int newXTile, int newYTile, int width, int height, GameTile slidingTile) {
			boolean result = true;
			GameTile collisionTile = getCollisionTile(newXTile, newYTile, slidingTile.getWidth(), slidingTile.getHeight(), slidingTile);
			if ((collisionTile != null)
					&& (collisionTile.isBlockerTile()
					&& collisionTile.getType() != Constants.BlockType.WEIGHTSWITCH.getValue())){
				result = false;
			}
			if(collisionTile!=null && checkForOvelap(slidingTile, collisionTile)){
				//mLastStatusMessage = "hogaya";
				processNearByFire(collisionTile);

			}
			return result;
		}

		private GameTile processNearByFire(GameTile switchWeightTile)
		{
			GameTile gameTile = null;

			int gameTilesSize = mGameTiles.size();
			for (int i = 0; i < gameTilesSize; i++)
			{
				gameTile = mGameTiles.get(i);
				if ((gameTile != null) && gameTile.getType() == Constants.BlockType.FIRE.getValue())
				{
//                    if((gameTile.getY() == (switchWeightTile.getY() -
//                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics())))
//                    && (gameTile.getX() == switchWeightTile.getX())){
//                        gameTile.setVisible(false);
//                    }
//                    if ((gameTile.getY() == (switchWeightTile.getY() +
//                            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics())))
//                    && (gameTile.getX() == switchWeightTile.getX())){
//                        gameTile.setVisible(false);
//                    }
					if(gameTile.getX() == switchWeightTile.getX()){
						gameTile.setVisible(false);
						gameTile.setType(Constants.BlockType.EMPTY.getValue());
						break;
					}
//                    if (gameTile.getX() == switchWeightTile.getX()){
//                        gameTile.setVisible(false);
//                    }

				}
			}
			return null;
		}

		public boolean checkForOvelap(GameTile slidingTile, GameTile collisionTile){
			boolean result = false;
			int xDelta = slidingTile.getX() - collisionTile.getX();
			int yDelta = slidingTile.getY() - collisionTile.getY();
			if(xDelta<0){
				xDelta = -xDelta;
			}
			if(yDelta<0){
				yDelta = -yDelta;
			}
			if(xDelta <= 20 && yDelta <= 20){
				result = true;
			}
			return result;
		}

		/**
		 * Detects a collision between a game unit and a game tile,
		 * returns the collision tile if available.
		 *
		 * @param x - The X (horizontal) position of the game unit.
		 * @param y - The Y (vertical) position of the game unit.
		 * @param width - The width of the game unit.
		 * @param height - The height of the game unit.
		 * @return GameTile - The collision game tile, if available.
		 */
		private GameTile getCollisionTile(int x, int y, int width, int height, GameTile tile)
		{
			GameTile gameTile = null;

			int gameTilesSize = mGameTiles.size();
			for (int i = 0; i < gameTilesSize; i++)
			{
				gameTile = mGameTiles.get(i);
				if ((gameTile != null) && gameTile.isCollisionTile())
				{
					// Make sure tiles don't collide with themselves
					if ((gameTile.getX() == x) && (gameTile.getY() == y))
					{
						continue;
					}

					if (gameTile.getCollision(x, y, width, height))
					{
						if(tile!=null && gameTile == tile){
							continue;
						}
						return gameTile;
					}
				}
			}
			return null;
		}

		/*/**
         * Handles a collision between the player unit and a game tile.
         * @param GameTile gameTile - The collision game tile.
         */
		private void handleTileCollision(GameTile gameTile)
		{
			if (gameTile != null)
			{
				switch (gameTile.getType())
				{
					case GameTile.TYPE_FIRE:
						handleFireTileCollision(gameTile);
						break;
					case GameTile.TYPE_KEY:
						handleKeyTileCollision(gameTile);
						break;
					case GameTile.TYPE_BOMB:
						handleBombTileCollision(gameTile);
						break;
					case GameTile.TYPE_SLIDING:
						handleSlidingTileCollision(gameTile);
						break;
					case GameTile.TYPE_CHEST:
						handleChestTileCollision(gameTile);
						break;
					case GameTile.TYPE_EXITSOLVE:
						handleExitTileCollision(gameTile);
						break;
					case GameTile.TYPE_FINISH:
						handleFinishTileCollision(gameTile);
						break;
					case GameTile.TYPE_WEIGHTSWITCH:
						handleWeightSwitchTileCollision(gameTile);
						break;
					case GameTile.TYPE_BREAKABLEWALL:
						handleBreakableTileCollision(gameTile);
						break;

					default:
						mLastStatusMessage = "Collision with regular tile";
				}
			}
		}



		/**
		 * Handles a collision between the player unit and a dangerous
		 * game tile.
		 */
		private void handleFireTileCollision(GameTile gameTile)
		{
			gameTile.setVisible(false);
			gameTile.setType(Constants.BlockType.EMPTY.getValue());
			mLastStatusMessage = "Collision with dangerous tile";
			Intent i=new Intent(Constants.appContext, Play.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			audio.play(Constants.appContext, R.raw.fire);
			Constants.appContext.startActivity(i);
		}

		private void handleWeightSwitchTileCollision(GameTile gameTile)
		{
			GameTile tile = null;

			int gameTilesSize = mGameTiles.size();
			for (int i = 0; i < gameTilesSize; i++)
			{
				tile = mGameTiles.get(i);
				if(tile.getType() == Constants.BlockType.EXITSOLVE.getValue()){
					break;
				}
			}
			tile.setBitmap(setAndGetGameTileBitmap(R.drawable.tile_dooropen));
			mLastStatusMessage = "Collision with weight switch tile";
		}

		private void handleFinishTileCollision(GameTile gameTile)
		{
			mLastStatusMessage = "Collision with finish tile";

			Intent i=new Intent(Constants.appContext,Play.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			gameTile.setType(Constants.BlockType.EMPTY.getValue());
			Constants.appContext.startActivity(i);
			Constants.GAME_LEVEL++;
			Player.getInstance().setItemCount(Constants.ITEM_POTION, Constants.GAME_NO_OF_POTIONS);
			Player.getInstance().setItemCount(Constants.ITEM_MAP, Constants.GAME_NO_OF_MAP);
			Player.getInstance().setItemCount(Constants.ITEM_BOMB, Constants.GAME_NO_OF_BOMBS);
			Player.getInstance().setItemCount(Constants.ITEM_KEY, Constants.GAME_NO_OF_KEYS);
			Player.getInstance().setGold(Constants.PLAYER_GOLD);
			Player.getInstance().setScore(Constants.PLAYER_SCORE);
			db.saveProfile();
		}

		private void handleBreakableTileCollision(GameTile gameTile)
		{
			mLastStatusMessage = "Collision with breakable tile";
			mVertDir = mPlayerVerticalDirection;
			mHorDir = mPlayerHorizontalDirection;
		}

		/**
		 * Handles a collision between the player unit and a sliding
		 * game tile.
		 */
		private void handleBombTileCollision(GameTile gameTile)
		{
			gameTile.setVisible(false);
			gameTile.setType(Constants.BlockType.EMPTY.getValue());
			Constants.GAME_NO_OF_BOMBS++;
			mLastStatusMessage = "Collision with bomb tile";
		}

		/**
		 * Handles a collision between the player unit and a sliding
		 * game tile.
		 */
		private void handleSlidingTileCollision(GameTile gameTile)
		{
			gameTile.setX(gameTile.getX());
			mLastStatusMessage = "Collision with sliding tile";
		}

		/**
		 * Handles a collision between the player unit and a sliding
		 * game tile.
		 */
		private void handleKeyTileCollision(GameTile gameTile)
		{
			gameTile.setVisible(false);
			gameTile.setType(Constants.BlockType.EMPTY.getValue());
			Constants.GAME_NO_OF_KEYS++;
			audio.play(Constants.appContext, R.raw.coin);
			mLastStatusMessage = "Collision with key tile";
		}
		/**
		 * Handles a collision between the player unit and a sliding
		 * game tile.
		 */
		private void handleChestTileCollision(GameTile gameTile)
		{
			gameTile.setVisible(false);
			gameTile.setType(Constants.BlockType.EMPTY.getValue());
			audio.play(Constants.appContext, R.raw.coin);
			Constants.PLAYER_GOLD = Constants.PLAYER_GOLD + Constants.CHEST_PRIZE;
			Constants.PLAYER_SCORE = Constants.PLAYER_SCORE + Constants.CHEST_PRIZE;
			mLastStatusMessage = "Collision with chest tile";
		}

		/**
		 * Handles a collision between the player unit and an exit
		 * game tile.
		 */
		private void handleExitTileCollision(GameTile gameTile)
		{
			//gameTile.setBitmap(setAndGetGameTileBitmap(R.drawable.tile_dooropen));
			mLastStatusMessage = "Collision with exit tile";
//			Constants.GAME_LEVEL++;
//			Intent i=new Intent(Constants.appContext,Play.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			Constants.appContext.startActivity(i);
			mVertDir = mPlayerVerticalDirection;
			mHorDir = mPlayerHorizontalDirection;
			//write code to next block
		}
	}


	private GameThread thread;

/*	/**
	 * The game view.
	 * @param Context context
	 * @param Activity activity
	 * @param int stage - The stage to load.
	 * @param int level - The level to load.
	 * @param float screenDensity - The screen density.
	 */

	public GameView(Context context, Play activity, int stage, int level, float screenDensity)
	{
		super(context);

		mGameContext = context;
		mGameActivity = activity;

		mScreenDensity = screenDensity;

		mPlayerStage = stage;
		mPlayerLevel = level;

		mGameTileData = new GameTileData(context);
		mGameLevelTileData = new GameLevelTileData(context);

		mGameTileTemplates = mGameTileData.getTilesData();

		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		// create thread only; it's started in surfaceCreated()
		thread = new GameThread(holder, context, null);

		setFocusable(true);

		mUiTextPaint = new Paint();
		mUiTextPaint.setStyle(Paint.Style.FILL);
		mUiTextPaint.setColor(Color.YELLOW);
		mUiTextPaint.setAntiAlias(true);

//		Typeface uiTypeface = Typeface.createFromAsset(activity.getAssets(), "fonts/Molot.otf");
//		if (uiTypeface != null)
//		{
//			mUiTextPaint.setTypeface(uiTypeface);
//		}
		mUiTextPaint.setTextSize(mGameContext.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.ui_text_size));

		startLevel();
		thread.doStart();
	}

	/**
	 * Gets the game thread.
	 * @return GameThread
	 */
	public GameThread getThread()
	{
		return thread;
	}

	/**
	 * Callback invoked when the surface dimensions change.
	 */
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height)
	{
		thread.setSurfaceSize(width, height);
	}

	/*
     * Callback invoked when the Surface has been created and is ready to be
     * used.
     */
	public void surfaceCreated(SurfaceHolder holder)
	{
		// start the thread here so that we don't busy-wait in run()
		// waiting for the surface to be created

		if (thread.getState() == Thread.State.TERMINATED)
		{
			thread = new GameThread(holder, getContext(), new Handler());
			thread.setRunning(true);
			thread.start();
			thread.doStart();
			startLevel();
		}
		else
		{
			thread.setRunning(true);
			thread.start();
		}
	}

	/*
     * Callback invoked when the Surface has been destroyed.
     */
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		thread.setRunning(false);
		while (retry)
		{
			try
			{
				thread.join();
				retry = false;
			} catch (InterruptedException e)
			{
				Log.e("Tile Game Example", e.getMessage());
			}
		}
	}

	/*/**
     * Detects and handles touch events from the user.
     * @param MotionEvent event
     * @return boolean
     */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int eventAction = event.getAction();
		int differenceX = 0;
		int differenceY = 0;
		int newX = mPlayerUnit.getX();
		int newY = mPlayerUnit.getY();

		switch (eventAction)
		{
			case MotionEvent.ACTION_DOWN:

				if (mGameState == STATE_RUNNING)
				{
					final int x = (int) event.getX();
					final int y = (int) event.getY();

					if (mCtrlUpArrow.getImpact(x, y))
					{
						Log.d("Tile Game Example", "Pressed up arrow");
						mLastStatusMessage = "Moving up";
						mPlayerVerticalDirection = Constants.Direction.UP.getValue();
						mPlayerMoving = true;
					}
					else if (mCtrlDownArrow.getImpact(x, y))
					{
						Log.d("Tile Game Example", "Pressed down arrow");
						mLastStatusMessage = "Moving down";
						mPlayerVerticalDirection = Constants.Direction.DOWN.getValue();
						mPlayerMoving = true;
					}
					else if (mCtrlLeftArrow.getImpact(x, y))
					{
						Log.d("Tile Game Example", "Pressed left arrow");
						mLastStatusMessage = "Moving left";
						mPlayerHorizontalDirection = Constants.Direction.LEFT.getValue();
						mPlayerUnit.setPlayer(mGameContext,
								getResources().getIdentifier(Player.getInstance().getPlayerCharacter()+Constants.PLAYER_LEFT, "drawable", "com.group2.dungeonraider"));
						mPlayerMoving = true;
					}
					else {
						if (mCtrlRightArrow.getImpact(x, y)) {
							Log.d("Tile Game Example", "Pressed right arrow");
							mLastStatusMessage = "Moving right";
							mPlayerHorizontalDirection = Constants.Direction.RIGHT.getValue();
							mPlayerUnit.setPlayer(mGameContext,
									getResources().getIdentifier(Player.getInstance().getPlayerCharacter()+Constants.PLAYER_RIGHT, "drawable", "com.group2.dungeonraider"));

							mPlayerMoving = true;
						}else if(mCtrlBomb.getImpact(x, y)){
							if(Constants.GAME_NO_OF_BOMBS > 0) {
								mLastStatusMessage = "Bomb used";

								if (mHorDir != 0)
								{
									differenceX = (mHorDir == Constants.Direction.RIGHT.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
									newX = (mPlayerUnit.getX() + differenceX);
								}

								if (mVertDir != 0)
								{
									differenceY = (mVertDir == Constants.Direction.DOWN.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
									newY = (mPlayerUnit.getY() + differenceY);
								}
								GameTile tile = getCollisionTile(newX, newY, mPlayerUnit.getWidth(), mPlayerUnit.getHeight(), null);
								if (tile != null && tile.getType() == Constants.BlockType.BREAKABLEWALL.getValue()) {
									tile.setVisible(false);
									Constants.GAME_NO_OF_BOMBS--;
									audio.play(Constants.appContext, R.raw.bomb);
								}

							}else{
								mLastStatusMessage = "Do not have bombs";
							}
						}else if(mctrlPotion.getImpact(x, y)){
							if(Constants.GAME_NO_OF_POTIONS > 0 && !Constants.IS_SLOW_DOWN_TIMER) {
								mLastStatusMessage = "Potion used";
								Constants.IS_SLOW_DOWN_TIMER = true;
								Constants.GAME_NO_OF_POTIONS--;
							}
						}else if(mctrlKey.getImpact(x, y)){
							if(Constants.GAME_NO_OF_KEYS > 0) {
								mLastStatusMessage = "Key used";

								if (mHorDir != 0)
								{
									differenceX = (mHorDir == Constants.Direction.RIGHT.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
									newX = (mPlayerUnit.getX() + differenceX);
								}

								if (mVertDir != 0)
								{
									differenceY = (mVertDir == Constants.Direction.DOWN.getValue()) ? getPixelValueForDensity(PlayerUnit.SPEED) : getPixelValueForDensity(-PlayerUnit.SPEED);
									newY = (mPlayerUnit.getY() + differenceY);
								}
								GameTile tile = getCollisionTile(newX, newY, mPlayerUnit.getWidth(), mPlayerUnit.getHeight(), null);
								if (tile != null && tile.getType() == Constants.BlockType.EXITSOLVE.getValue()) {
									tile.setBitmap(setAndGetGameTileBitmap(R.drawable.tile_dooropen));
									tile.setType(Constants.BlockType.DOOROPEN.getValue());
									Constants.GAME_NO_OF_KEYS--;
									audio.play(Constants.appContext, R.raw.btn_click);
								}

							}else{
								mLastStatusMessage = "Do not have bombs";
							}
						}else if(mctrlMap.getImpact(x, y)) {
							if (Constants.GAME_NO_OF_MAP > 0) {
								mLastStatusMessage = "Map used";
								showImage();
								Constants.GAME_NO_OF_MAP--;
							}
						}
					}
				}

				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mPlayerMoving = false;
				mPlayerVerticalDirection = 0;
				mPlayerHorizontalDirection = 0;
				break;
		}

		return true;
	}

	public void showImage() {

//		AlertDialog.Builder builder = new AlertDialog.Builder(Constants.appContext);
//		builder.setTitle("asdf");
//		builder.setMessage("asdfASDF");
//		builder.setNeutralButton(android.R.string.ok,
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int whichButton) {
//						dialog.dismiss();
//					}
//				});
//
//		AlertDialog dialog = builder.show();

		final Dialog dialog = new Dialog(Constants.appContext);

		//setting custom layout to dialog
		dialog.setContentView(R.layout.img_blueprint_dialog);

		ImageView image = (ImageView)dialog.findViewById(R.id.image);
		image.setImageDrawable(getResources().getDrawable(R.drawable.blueprint));

		//adding button click event
		Button dismissButton = (Button) dialog.findViewById(R.id.button);
		dismissButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
	}

	private GameTile getCollisionTile(int x, int y, int width, int height, GameTile tile)
	{
		GameTile gameTile = null;

		int gameTilesSize = mGameTiles.size();
		for (int i = 0; i < gameTilesSize; i++)
		{
			gameTile = mGameTiles.get(i);
			if ((gameTile != null) && gameTile.isCollisionTile())
			{
				// Make sure tiles don't collide with themselves
				if ((gameTile.getX() == x) && (gameTile.getY() == y))
				{
					continue;
				}

				if (gameTile.getCollision(x, y, width, height))
				{
					if(tile!=null && gameTile == tile){
						continue;
					}
					return gameTile;
				}
			}
		}
		return null;
	}
	/**
	 * Initializes and sets the on-screen position of the game controls.
	 */
	private void setControlsStart()
	{
		if (mCtrlDownArrow == null)
		{
			mCtrlDownArrow = new GameUi(mGameContext, R.drawable.ctrl_down_arrow);

			mCtrlDownArrow.setX(mScreenXMax - ((mCtrlDownArrow.getWidth() * 2) + getPixelValueForDensity(CONTROLS_PADDING)));
			mCtrlDownArrow.setY(mScreenYMax - (mCtrlDownArrow.getHeight() + getPixelValueForDensity(CONTROLS_PADDING)));
		}

		if (mCtrlUpArrow == null)
		{
			mCtrlUpArrow = new GameUi(mGameContext, R.drawable.ctrl_up_arrow);

			mCtrlUpArrow.setX(mCtrlDownArrow.getX());
			mCtrlUpArrow.setY(mCtrlDownArrow.getY() - (mCtrlUpArrow.getHeight() * 2));
		}

		if (mCtrlLeftArrow == null)
		{
			mCtrlLeftArrow = new GameUi(mGameContext, R.drawable.ctrl_left_arrow);
			mCtrlLeftArrow.setX(mCtrlDownArrow.getX() - mCtrlLeftArrow.getWidth());
			mCtrlLeftArrow.setY(mCtrlDownArrow.getY() - mCtrlLeftArrow.getHeight());
		}

		if (mCtrlRightArrow == null)
		{
			mCtrlRightArrow = new GameUi(mGameContext, R.drawable.ctrl_right_arrow);

			mCtrlRightArrow.setX(mScreenXMax - (mCtrlLeftArrow.getWidth() + getPixelValueForDensity(CONTROLS_PADDING)));
			mCtrlRightArrow.setY(mCtrlLeftArrow.getY());
		}

		if (mCtrlBomb == null)
		{
			mCtrlBomb = new GameUi(mGameContext, R.drawable.btn_bomb);

			mCtrlBomb.setX(10);
			mCtrlBomb.setY(90);
		}

		if (mctrlPotion == null)
		{
			mctrlPotion = new GameUi(mGameContext, R.drawable.btn_potion);

			mctrlPotion.setX(10);
			mctrlPotion.setY(170);
		}

		if (mctrlKey == null)
		{
			mctrlKey = new GameUi(mGameContext, R.drawable.btn_key);

			mctrlKey.setX(10);
			mctrlKey.setY(250);
		}

		if (mctrlMap == null)
		{
			mctrlMap = new GameUi(mGameContext, R.drawable.btn_map);

			mctrlMap.setX(10);
			mctrlMap.setY(330);
		}
	}

	/**
	 * Initializes and sets the starting location of the player unit.
	 */
	private void setPlayerStart()
	{
		if (mPlayerUnit == null)
		{
			mPlayerUnit = new PlayerUnit(mGameContext,
					getResources().getIdentifier(Player.getInstance().getPlayerCharacter()+Constants.PLAYER_RIGHT, "drawable", "com.group2.dungeonraider"));
		}

		int playerStartX = (mPlayerStartTileX * mPlayerUnit.getWidth());
		int playerStartY = (mPlayerStartTileY * mPlayerUnit.getHeight());

		Log.d("Tile Game Example", "Player unit starting at X: " + playerStartX + ", Y: " + playerStartY);

		mPlayerUnit.setX(playerStartX);
		mPlayerUnit.setY(playerStartY);
		mPlayerUnit.setUnmodifiedX(0);
		mPlayerUnit.setUnmodifiedY(0);
	}

	/**
	 * Parses game level data to create a tile-based level.
	 * Tile positioning logic expects all game tiles to
	 * maintain a consistent width and height.
	 */
	private void parseGameLevelData()
	{
		updatingGameTiles = true;

		ArrayList<String> gameLevelData = mGameLevelTileData.getGameLevelData(mPlayerStage, mPlayerLevel);

		String levelTileData = gameLevelData.get(GameLevelTileData.FIELD_ID_TILE_DATA);

		if (levelTileData == null)
		{
			return;
		}

		// Get player start position.
		mPlayerStartTileX = Integer.parseInt(gameLevelData.get(GameLevelTileData.FIELD_ID_PLAYER_START_TILE_X));
		mPlayerStartTileY = Integer.parseInt(gameLevelData.get(GameLevelTileData.FIELD_ID_PLAYER_START_TILE_Y));

		// Clear any existing loaded game tiles.
		mGameTiles.clear();

		// Split level tile data by line.
		String[] tileLines = levelTileData.split(GameLevelTileData.TILE_DATA_LINE_BREAK);

		Bitmap bitmap = null;
		Point tilePoint = new Point(0, 0);
		int tileX = 0;
		int tileY = 0;

		int tileKey = 0;

		// Loop through each line of the level tile data.
		for (String tileLine : tileLines)
		{
			tileX = 0;

			// Split tile data line by tile delimiter, producing an array of tile IDs.
			String[] tiles = tileLine.split(",");

			// Loop through the tile IDs, creating a new GameTile instance for each one.
			for (String tile : tiles)
			{
				// Get tile definition for the current tile ID.
				ArrayList<Integer> tileData = mGameTileTemplates.get(Integer.parseInt(tile));

				// Check for valid tile data.
				if ((tileData != null)
						&& (tileData.size() > 0)
						&& (tileData.get(GameTileData.FIELD_ID_DRAWABLE) > 0))
				{
					// Set tile position.
					tilePoint.x = tileX;
					tilePoint.y = tileY;

					GameTile gameTile = new GameTile(mGameContext, tilePoint);

					// Set tile bitmap.
					bitmap = setAndGetGameTileBitmap(tileData.get(GameTileData.FIELD_ID_DRAWABLE));
					gameTile.setBitmap(bitmap);

					// Set tile type.
					gameTile.setType(tileData.get(GameTileData.FIELD_ID_TYPE));

					// Set tile visibility.
					if (tileData.get(GameTileData.FIELD_ID_VISIBLE) == 0)
					{
						gameTile.setVisible(false);
					}

					gameTile.setKey(tileKey);

					// If undefined, set global tile width / height values.
					if (mTileWidth == 0)
					{
						mTileWidth = gameTile.getWidth();
					}
					if (mTileHeight == 0)
					{
						mTileHeight = gameTile.getHeight();
					}

					// Add new game tile to loaded game tiles.
					mGameTiles.add(gameTile);

					tileKey++;
				}

				// Increment next tile X (horizontal) position by tile width.
				tileX += mTileWidth;
			}

			// Increment next tile Y (vertical) position by tile width.
			tileY += mTileHeight;
		}

		updatingGameTiles = false;
	}

	/**
	 * Sets the state for a new game.
	 */
	private void setGameStartState()
	{
		setControlsStart();
		setPlayerStart();
	}

	/**
	 * Loads and starts the current level.
	 */
	private void startLevel()
	{
		parseGameLevelData();
		setPlayerStart();

		thread.unpause();
	}

	/*/**
     * Stores a bitmap for use by a game tile in a level.
     * @param int resourceId - The bitmap resource ID.
     * @return Bitmap - The Bitmap instance for the given resource ID.
     */
	private Bitmap setAndGetGameTileBitmap(int resourceId)
	{
		if (!mGameTileBitmaps.containsKey(resourceId))
		{
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeResource(mGameContext
					.getResources(), resourceId);

			if (bitmap != null)
			{
				mGameTileBitmaps.put(resourceId, bitmap);
			}
		}

		return mGameTileBitmaps.get(resourceId);
	}

	private int getPixelValueForDensity(int pixels)
	{
		return (int) (pixels * mScreenDensity);
	}
}
