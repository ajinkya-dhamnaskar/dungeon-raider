package com.group2.dungeonraider.utilities;

/**
 * Created by Rohit on 10/27/2015.
 */
public class Constants {

    private static final String LOG = Constants.class.getSimpleName();

   // public static FPropsLoader drProps = new FPropsLoader("DRGame");

    public static int VOLUME_MODE;
//    /*
//    DB details
//     */
//   /* public static String DB_NAME = drProps.getValue("db.name");
//    public static String DB_SERVER = drProps.getValue("db.server");
//    public static String DB_PORT = drProps.getValue("db.port");
//    public static String DB_PASSWORD = drProps.getValue("db.password");
//
//    /*
//    item type
//     */
//    public static String IT_KEY = drProps.getValue("it.key");
//    public static String IY_BOMB = drProps.getValue("it.bomb");
//    public static String IT_POTION = drProps.getValue("it.potion");
//    public static String IT_MAP = drProps.getValue("it.map");
//
//    /*
//    direction
//     */
//    public static String DIR_UP = drProps.getValue("dir.up");
//    public static String DIR_DOWN = drProps.getValue("dir.down");
//    public static String DIR_LEFT = drProps.getValue("dir.left");
//    public static String DIR_RIGHT = drProps.getValue("dir.right");
//
//    /*
//    levels
//     */
//    public static String LVL_TUTORIAL = drProps.getValue("lvl.tutorial");
//    public static String LVL_EASY = drProps.getValue("lvl.easy");
//    public static String LVL_MEDIUM = drProps.getValue("lvl.medium");
//    public static String LVL_HARD = drProps.getValue("lvl.hard");
//
//    /*
//    mutator type
//     */
//    public static String MT_HAIR = drProps.getValue("mt.hair");
//    public static String MT_SKIN= drProps.getValue("mt.skin");
//    public static String MT_SHIRT = drProps.getValue("mt.shirt");
//    public static String MT_PANTS = drProps.getValue("mt.pants");
//
//    /*
//    move results
//     */
//    public static String MR_SUCCESS = drProps.getValue("mr.success");
//    public static String MR_FAILURE = drProps.getValue("mr.failure");
//    public static String MR_KEY_RECEIVED = drProps.getValue("mr.key.received");
//    public static String MR_SWITCH_ROOMS = drProps.getValue("mr.switch.rooms");
//    public static String MR_BOMB_RECEIVED = drProps.getValue("mr.bomb.received");
//
//    /*
//    block type
//     */
//    public static String BT_EMPTY = drProps.getValue("bt.empty");
//    public static String BT_WALL = drProps.getValue("bt.wall");
//    public static String BT_SLIDING = drProps.getValue("bt.sliding");
//    public static String BT_DOOR = drProps.getValue("bt.door");
//    public static String BT_KEY = drProps.getValue("bt.key");
//    public static String BT_BREAKABLEWALL = drProps.getValue("bt.breakable.wall");
//    public static String BT_CHEST = drProps.getValue("bt.chest");
//    public static String BT_WEIGHT_SWITCH = drProps.getValue("bt.weight.switch");
//    public static String BT_FIRE = drProps.getValue("bt.fire");
//    public static String BT_SPIKE = drProps.getValue("bt.spike");
//    public static String BT_ENTRANCE_START = drProps.getValue("bt.entrance.start");
//    public static String BT_EXIT_SOLVE = drProps.getValue("bt.exit.solve");
//    public static String BT_FINISH = drProps.getValue("bt.finish");
//
//    /*
//    block state
//     */
//    public static String BS_MOVING = drProps.getValue("bs.moving");
//    public static String BS_STILL = drProps.getValue("bs.still");
//    public static String BS_OPEN_TEMPORARILY = drProps.getValue("bs.open.temporarily");
//    public static String BS_OPEN_ALWAYS = drProps.getValue("bs.open.always");
//    public static String BS_CLOSED = drProps.getValue("bs.closed");
//    public static String BS_ON = drProps.getValue("bs.on");
//    public static String BS_OFF = drProps.getValue("bs.off");

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
        EXITSOLVE(13), FINISH(14);
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
