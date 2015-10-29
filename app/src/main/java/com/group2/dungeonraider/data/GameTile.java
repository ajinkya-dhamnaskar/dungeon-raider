package com.group2.dungeonraider.data;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;

import com.group2.dungeonraider.utilities.Constants;

/**
 * An extension of GameImage, GameTile represents a tile used to build
 * a level in the game.
 * 
 * In addition to the GameImage properties, game tiles include a unique
 * key, tile type identifier and visibility setting. 
 * 
 * @author Dan Ruscoe (ruscoe.org)
 * @version 1.0
 */
public class GameTile extends GameImage
{
	public static final int TYPE_EMPTY = 1;
	public static final int TYPE_WALL = 2;
	public static final int TYPE_SLIDING = 3;
	public static final int TYPE_DOOR = 4;
	public static final int TYPE_KEY = 5;
	public static final int TYPE_BOMB = 6;
	public static final int TYPE_BREAKABLEWALL = 7;
	public static final int TYPE_CHEST = 8;
	public static final int TYPE_WEIGHTSWITCH = 9;
	public static final int TYPE_FIRE = 10;
	public static final int TYPE_SPIKE = 11;
	public static final int TYPE_ENTRANCESTART = 12;
	public static final int TYPE_EXITSOLVE = 13;
	public static final int TYPE_FINISH = 14;
	public static final int TYPE_COIN = 15;
	
	private int mKey = 0;
	private int mType = TYPE_EMPTY;

	private boolean mVisible = true;
	
	private Rect mCollisionRect = null;
	
	public GameTile(Context context, Point point)
	{
		super(context);

		this.mX = point.x;
		this.mY = point.y;
	}
	
	public GameTile(Context context, int drawable, Point point)
	{
		super(context, drawable);
		
		this.mX = point.x;
		this.mY = point.y;
	}

	public boolean isDangerous()
	{
		return (this.mType == TYPE_FIRE);
	}

	public boolean getCollision(float x, float y, int width, int height)
	{
		if (this.mCollisionRect == null)
		{
			this.mCollisionRect = new Rect((int)x, (int)y, ((int)x + width), ((int)y + height));
		}
		else
		{
			this.mCollisionRect.set((int)x, (int)y, ((int)x + width), ((int)y + height));
		}

		return (this.mCollisionRect.intersects(this.mX, this.mY, (this.mX + getWidth()), (this.mY + getHeight())));
	}
	
	public boolean getCollision(GameUnit gameUnit)
	{
		return (gameUnit.getRect().intersects(this.mX, this.mY, (this.mX + mWidth), (this.mY + mHeight)));
	}

	public int getKey()
	{
		return this.mKey;
	}
	
	public void setKey(int key)
	{
		this.mKey = key;
	}
	
	public int getType()
	{
		return this.mType;
	}
	
	public void setType(int type)
	{
		this.mType = type;
	}
	
	public int getX()
	{
		return this.mX;
	}

	public void setX(int x)
	{
		this.mX = x;
	}

	public int getY()
	{
		return this.mY;
	}

	public void setY(int y)
	{
		this.mY = y;
	}

	public boolean isVisible()
	{
		return this.mVisible;
	}

	public void setVisible(boolean visible)
	{
		this.mVisible = visible;
	}
	
	public boolean isCollisionTile()
	{
		return ((this.mType != GameTile.TYPE_EMPTY) && this.mVisible);
	}

	public boolean isBlockerTile()
	{
		return this.mType != GameTile.TYPE_EMPTY;

	}
}
