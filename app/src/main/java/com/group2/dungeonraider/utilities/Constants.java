package com.group2.dungeonraider.utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Rohit on 10/27/2015.
 */
public class Constants {

    private static final String LOG = Constants.class.getSimpleName();

    // public static FPropsLoader drProps = new FPropsLoader("DRGame");

    public static int VOLUME_MODE;
    public static String THEME_MODE;
    public static int CHARACTER_SELECTED;

    public static String ITEM_POTION="POTION";
    public static String ITEM_KEY="KEY";
    public static String ITEM_BOMB="BOMB";
    public static String ITEM_MAP="MAP";
    public static int ITEM_POTION_VALUE=10;
    public static int ITEM_BOMB_VALUE=30;
    public static int ITEM_MAP_VALUE=40;
    public static int ITEM_KEY_VALUE=50;
    public static int INITIAL_GOLD=100;

    public static int CHEST_PRIZE=100;

    public static int PLAYER_GOLD;
    public static int PLAYER_SCORE;

    public static String MUTATOR_SHIRT="SHIRT";
    public static String MUTATOR_PANT="PANT";
    public static String MUTATOR_CAP="CAP";
    public static String MUTATOR_SKIN="SKIN";

    public static String COLOR_RED="RED";
    public static String COLOR_BLUE="BLUE";
    public static String COLOR_GREEN="GREEN";
    public static String COLOR_YELLOW="YELLOW";
    public static String COLOR_PINK="PINK";
    public static String COLOR_BROWN="BROWN";
    public static String DOLLAR="$";
    public static String OWNED="Owned : ";


    public static String PLAYER_A="bbpwa";
    public static String PLAYER_B="bbpwb";

    public static String PLAYER_RIGHT="r";
    public static String PLAYER_LEFT="l";

    public static int CAP_VALUE=20;
    public static int SHIRT_VALUE=40;
    public static int PANT_VALUE=50;
    public static int SKIN_VALUE=100;




    public static Context appContext;
    public static int SLIDE_TILE_BY_DP = 10;
    public static int GAME_LEVEL = 0;
    public static long GAME_START_TIME = System.currentTimeMillis();
    public static long LAST_TIME = 0;
    public static long DELAY_LAST_TIME = 0;
    public static boolean IS_SLOW_DOWN_TIMER = false;
    public static int TICK_COUNTER_FOR_DELAY = 0;
    public static int MAX_TICK_COUNTER_FOR_DELAY = 5;
    public static int TIME_DELAY = 0;
    public static int MAX_TIME_DELAY = 1000;
    public static long LAST_CURR_TIME = 0;
    public static MediaPlayer MP = new MediaPlayer();

    public static int GAME_NO_OF_BOMBS;
    public static int GAME_NO_OF_POTIONS;
    public static int GAME_NO_OF_KEYS;
    public static int GAME_NO_OF_MAP;

    static MediaPlayer getMediaPlayer(Context context){

        MediaPlayer MP = new MediaPlayer();

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT) {
            return MP;
        }

        try {
            Class<?> cMediaTimeProvider = Class.forName( "android.media.MediaTimeProvider" );
            Class<?> cSubtitleController = Class.forName( "android.media.SubtitleController" );
            Class<?> iSubtitleControllerAnchor = Class.forName( "android.media.SubtitleController$Anchor" );
            Class<?> iSubtitleControllerListener = Class.forName( "android.media.SubtitleController$Listener" );

            Constructor constructor = cSubtitleController.getConstructor(new Class[]{Context.class, cMediaTimeProvider, iSubtitleControllerListener});

            Object subtitleInstance = constructor.newInstance(context, null, null);

            Field f = cSubtitleController.getDeclaredField("mHandler");

            f.setAccessible(true);
            try {
                f.set(subtitleInstance, new Handler());
            }
            catch (IllegalAccessException e) {return MP;}
            finally {
                f.setAccessible(false);
            }

            Method setsubtitleanchor = MP.getClass().getMethod("setSubtitleAnchor", cSubtitleController, iSubtitleControllerAnchor);

            setsubtitleanchor.invoke(MP, subtitleInstance, null);

        } catch (Exception e) {}

        return MP;
    }


    public enum Direction {
        UP(1), DOWN(2), LEFT(3), RIGHT(4);
        private int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    };

    public static Direction dir;
    public enum ItemType {
        KEY(1), BOMB(2), POTION(3), MAP(4);
        private int value;

        private ItemType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };

    public enum LEVEL {
        TUTORIAL(1), EASY(2), MEDIUM(3), HARD(4);
        private int value;

        private LEVEL(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };


    public enum MutatorType {
        HAIR(1), SHIRT(2), SKIN(3), PANTS(4);
        private int value;

        private MutatorType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };


    public enum MoveResult {
        SUCCESS(1), FAILURE(2), KEYRECEIVED(3), BOMBRECEIVED(4),
        SWITCHROOMS(5);
        private int value;

        private MoveResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };

    public enum BlockType {
        EMPTY(1), WALL(2), SLIDING(3), DOOR(4),
        KEY(5), BOMB(6), BREAKABLEWALL(7), CHEST(8),
        WEIGHTSWITCH(9), FIRE(10), SPIKE(11), ENTRANCESTART(12),
        EXITSOLVE(13), FINISH(14), DOOROPEN(15),DUNGEONFINISH(16);
        private int value;

        private BlockType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };

    public enum BlockState {
        MOVING(1), STILL(2), OPENTEPORARILY(3), OPENALWAYS(4),
        CLOSED(5), ON(6), OFF(7);
        private int value;

        private BlockState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    };

}
