package com.group2.dungeonraider.data;

/**
 * Created by Rohit on 10/30/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.test.dungeonmainmenu.R;
import com.group2.dungeonraider.domain.Item;
import com.group2.dungeonraider.domain.Mutator;
import com.group2.dungeonraider.domain.Player;
import com.group2.dungeonraider.domain.PlayerItem;
import com.group2.dungeonraider.domain.PlayerMutator;
import com.group2.dungeonraider.domain.PlayerView;
import com.group2.dungeonraider.utilities.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Rohit on 10/28/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name
    public static String DATABASE_NAME = "DUNGEON_DATABASE";

    // Current version of database
    private static final int DATABASE_VERSION = 1;

    // Name of table
    private static final String TABLE_PLAYER = "PLAYER";
    private static final String TABLE_ITEMS = "ITEMS";
    private static final String TABLE_PLAYER_ITEM = "PLAYER_ITEMS";
    private static final String TABLE_MUTATOR = "MUTATOR";
    private static final String TABLE_PLAYER_MUTATOR = "PLAYER_MUTATOR";
    private static final String TABLE_LEVEL = "LEVEL";
    private static final String TABLE_ROOM = "ROOM";
    private static final String TABLE_PLAYER_ROOM = "PLAYER_ROOM";
    private static final String TABLE_LEVEL_ROOM = "LEVEL_ROOM";

    // All Keys used in  tables
    //PLAYER
    private static final String KEY_ID = "ID";

    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_GOLD = "GOLD";
    private static final String KEY_SCORE = "SCORE";
    private static final String KEY_TIME = "TIME";
    //ITEM
    private static final String KEY_PURCHASE_VALUE = "PURCHASE_VALUE";
    private static final String KEY_ITEM_NAME = "ITEM_NAME";
    // PLAYER_ITEM
    private static final String KEY_PLAYER_ID = "PLAYER_ID";
    private static final String KEY_ITEM_ID="ITEM_ID";
    private static final String KEY_ITEM_QUANTITY = "ITEM_QUANTITY";
    //MUTATOR
    private static final String KEY_MUTATOR_ID = "MUTATOR_ID";
    private static final String KEY_MUTATOR_NAME="MUTATOR_NAME";
    private static final String KEY_MUTATOR_VALUE = "MUTATOR_VALUE";
    private static final String KEY_MUTATOR_COLOR = "MUTATOR_COLOR";
    //ROOM
    private static final String KEY_ROOM_ID = "ROOM_ID";
    private static final String KEY_PUZZLE_STRUCT = "PUZZLE_STRUCT";
    private static final String KEY_LEVEL_ID = "LEVEL_ID";
    private static final String KEY_ROOM_NUMBER = "ROOM_NUMBER";
    private static final String KEY_TOTAL_GOLD = "TOTAL_GOLD";
    private static final String KEY_NUMBER_BOMBS = "NUMBER_BOMBS";
    private static final String KEY_DESIRED_TIME = "DESIRED_TIME";

    //LEVEL
    private static final String KEY_LEVEL_NAME = "LEVEL_NAME";
    private static final String KEY_NUMBER_ROOMS = "NUMBER_ROOMS";

    //PLAYER_ROOM
    private static final String KEY_GOLD_EARN = "GOLD_EARN";
    private static final String KEY_BOMB_EARN = "BOMB_EARN";
    private static final String KEY_TIME_TAKEN = "TIME_TAKEN";


    public static String TAG = "tag";


    private static final String CREATE_TABLE_PLAYER = "CREATE TABLE "
            + TABLE_PLAYER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT NOT NULL UNIQUE ,"
            + KEY_GOLD + " INTEGER,"+   KEY_SCORE + " INTEGER,"+KEY_TIME + " INTEGER );";


    private static final String CREATE_TABLE_PLAYER_ITEMS = "CREATE TABLE "
            + TABLE_PLAYER_ITEM + "(" + KEY_PLAYER_ID
            + " INTEGER NOT NULL," + KEY_ITEM_ID + " INTEGER NOT NULL , "
            + KEY_ITEM_QUANTITY + " INTEGER, FOREIGN KEY("
            + KEY_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER +"(" +KEY_ID +"), FOREIGN KEY("
            + KEY_ITEM_ID   + ") REFERENCES " + TABLE_ITEMS +"(" +KEY_ID +"), "
            + "PRIMARY KEY (" + KEY_PLAYER_ID + ","+ KEY_ITEM_ID +" ));";


    private static final String CREATE_TABLE_ITEMS = "CREATE TABLE "
            + TABLE_ITEMS + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_ITEM_NAME + " TEXT NOT NULL UNIQUE ,"
            + KEY_PURCHASE_VALUE+ " INTEGER );";


    //-------------URMIL

    private static final String CREATE_TABLE_LEVEL = "CREATE TABLE "
            + TABLE_LEVEL + "(" + KEY_LEVEL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LEVEL_NAME + " TEXT NOT NULL UNIQUE ,"
            + KEY_NUMBER_ROOMS + " INTEGER );";

    private static final String CREATE_TABLE_MUTATOR = "CREATE TABLE "
            + TABLE_MUTATOR + "(" + KEY_MUTATOR_ID
            + " INTEGER PRIMARY KEY ," + KEY_MUTATOR_NAME + " TEXT NOT NULL ,"
            + KEY_MUTATOR_VALUE + " INTEGER ," + KEY_MUTATOR_COLOR + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_LEVEL_ROOM = "CREATE TABLE "
            + TABLE_LEVEL_ROOM + "(" + KEY_LEVEL_ID
            + " INTEGER NOT NULL," + KEY_ROOM_ID + " INTEGER NOT NULL, FOREIGN KEY("
            + KEY_LEVEL_ID + ") REFERENCES " + TABLE_LEVEL +"(" +KEY_LEVEL_ID +"), FOREIGN KEY("
            + KEY_ROOM_ID   + ") REFERENCES " + TABLE_ROOM +"(" +KEY_ROOM_ID +"), "
            + "PRIMARY KEY (" + KEY_LEVEL_ID + ","+ KEY_ROOM_ID +" ));";

    private static final String CREATE_TABLE_ROOM = "CREATE TABLE "
            + TABLE_ROOM + "(" + KEY_ROOM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ KEY_TOTAL_GOLD + " INTEGER,"
            + KEY_NUMBER_BOMBS + " INTEGER,"
            + KEY_LEVEL_ID + " INTEGER,"
            + KEY_DESIRED_TIME + " INTEGER NOT NULL,"
            + KEY_ROOM_NUMBER + " INTEGER NOT NULL,"
            + KEY_PUZZLE_STRUCT + " TEXT NOT NULL, FOREIGN KEY("
            + KEY_LEVEL_ID + ") REFERENCES " + TABLE_LEVEL +"(" +KEY_LEVEL_ID +"));";

    private static final String CREATE_TABLE_PLAYER_MUTATOR = "CREATE TABLE "
            + TABLE_PLAYER_MUTATOR + "(" + KEY_PLAYER_ID
            + " INTEGER NOT NULL," + KEY_MUTATOR_ID + " INTEGER NOT NULL, FOREIGN KEY("
            + KEY_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER +"(" +KEY_ID +"), FOREIGN KEY("
            + KEY_MUTATOR_ID   + ") REFERENCES " + TABLE_ITEMS +"(" +KEY_MUTATOR_ID +"), "
            + "PRIMARY KEY (" + KEY_PLAYER_ID + ","+ KEY_MUTATOR_ID +" ));";

    private static final String CREATE_TABLE_PLAYER_ROOM = "CREATE TABLE "
            + TABLE_PLAYER_ROOM + "(" + KEY_PLAYER_ID
            + " INTEGER NOT NULL,"+ KEY_ROOM_ID + " INTEGER NOT NULL,"
            + KEY_GOLD_EARN + " INTEGER,"
            + KEY_BOMB_EARN + " INTEGER,"
            + KEY_TIME_TAKEN + " INTEGER NOT NULL,"
            + KEY_PUZZLE_STRUCT + " TEXT NOT NULL, FOREIGN KEY("
            + KEY_PLAYER_ID + ") REFERENCES " + TABLE_PLAYER +"(" +KEY_ID +"), FOREIGN KEY("
            + KEY_ROOM_ID   + ") REFERENCES " + TABLE_ROOM +"(" +KEY_ROOM_ID +"), "
            + "PRIMARY KEY (" + KEY_PLAYER_ID + ","+ KEY_ROOM_ID +" ));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This method is called by system if the database is accessed but not yet
     * created.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_PLAYER);         // create player table
        db.execSQL(CREATE_TABLE_ITEMS);          // create items table
        db.execSQL(CREATE_TABLE_PLAYER_ITEMS);   // create player_items table

        //-------------------Urmil
        db.execSQL(CREATE_TABLE_LEVEL);   // create player_items table
        db.execSQL(CREATE_TABLE_ROOM);   // create player_items table
        db.execSQL(CREATE_TABLE_LEVEL_ROOM);   // create player_items table
        db.execSQL(CREATE_TABLE_PLAYER_ROOM);   // create player_items table
        db.execSQL(CREATE_TABLE_MUTATOR);   // create player_items table
        db.execSQL(CREATE_TABLE_PLAYER_MUTATOR);   // create player_items table

        ///INSERT ITEMS
        ContentValues valuesitem = new ContentValues();
        valuesitem.put(KEY_ITEM_NAME, Constants.ITEM_POTION);
        valuesitem.put(KEY_PURCHASE_VALUE, Constants.ITEM_POTION_VALUE);
        db.insert(TABLE_ITEMS, null, valuesitem);

        valuesitem.put(KEY_ITEM_NAME, Constants.ITEM_BOMB);
        valuesitem.put(KEY_PURCHASE_VALUE, Constants.ITEM_BOMB_VALUE);
        db.insert(TABLE_ITEMS, null, valuesitem);


        valuesitem.put(KEY_ITEM_NAME, Constants.ITEM_MAP);
        valuesitem.put(KEY_PURCHASE_VALUE, Constants.ITEM_MAP_VALUE);
        db.insert(TABLE_ITEMS, null, valuesitem);


        valuesitem.put(KEY_ITEM_NAME, Constants.ITEM_KEY);
        valuesitem.put(KEY_PURCHASE_VALUE, Constants.ITEM_KEY_VALUE);
        db.insert(TABLE_ITEMS, null, valuesitem);



        //INSERT MUTATOR VALUES
        ContentValues values = new ContentValues();
//        values.put(KEY_MUTATOR_NAME, Constants.MUTATOR_CAP);
//        values.put(KEY_MUTATOR_ID, 1);
//        values.put(KEY_MUTATOR_COLOR, Constants.COLOR_BROWN);
//        values.put(KEY_MUTATOR_VALUE, Constants.CAP_VALUE);
//        db.insert(TABLE_MUTATOR, null, values);

        values.put(KEY_MUTATOR_NAME, Constants.MUTATOR_CAP);
        values.put(KEY_MUTATOR_ID,1);
        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_YELLOW);
        values.put(KEY_MUTATOR_VALUE,Constants.CAP_VALUE);
        db.insert(TABLE_MUTATOR, null, values);

//        values.put(KEY_MUTATOR_NAME,Constants.MUTATOR_SHIRT)
//        values.put(KEY_MUTATOR_ID,2);
//        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_BLUE);
//        values.put(KEY_MUTATOR_VALUE,Constants.SHIRT_VALUE);
//        db.insert(TABLE_MUTATOR, null, values);
//
        values.put(KEY_MUTATOR_NAME,Constants.MUTATOR_SHIRT);
        values.put(KEY_MUTATOR_ID,2);
        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_GREEN);
        values.put(KEY_MUTATOR_VALUE,Constants.SHIRT_VALUE);
        db.insert(TABLE_MUTATOR, null, values);


        values.put(KEY_MUTATOR_NAME,Constants.MUTATOR_SKIN);
        values.put(KEY_MUTATOR_ID,4);
        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_PINK);
        values.put(KEY_MUTATOR_VALUE,Constants.SKIN_VALUE);
        db.insert(TABLE_MUTATOR, null, values);

        values.put(KEY_MUTATOR_NAME,Constants.MUTATOR_PANT);
        values.put(KEY_MUTATOR_ID,3);
        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_RED);
        values.put(KEY_MUTATOR_VALUE,Constants.PANT_VALUE);
        db.insert(TABLE_MUTATOR, null, values);

//        values.put(KEY_MUTATOR_NAME,Constants.MUTATOR_PANT);
//        values.put(KEY_MUTATOR_ID,6);
//        values.put(KEY_MUTATOR_COLOR,Constants.COLOR_PINK);
//        values.put(KEY_MUTATOR_VALUE,Constants.PANT_VALUE);
//        db.insert(TABLE_MUTATOR, null, values);




    }

    /**
     * This method is called when any modifications in database are done like
     * version is updated or database schema is changed
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER); // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_ITEM); // drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS); // drop table if


        //-------------------Urmil
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL); // drop table if
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM); // drop table if
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVEL_ROOM); // drop table if
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_ROOM); // drop table if
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MUTATOR); // drop table if
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER_MUTATOR); // drop table if




        onCreate(db);
    }


    public long addPlayerDetail(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, player.getName());
        values.put(KEY_TIME, player.getTime());
        values.put(KEY_GOLD,player.getGold());
        values.put(KEY_SCORE, player.getScore());

        // insert row in player table
        try {
            long insert = db.insert(TABLE_PLAYER, null, values);
            return insert;
        }

        catch(SQLiteConstraintException e)
        {
            Log.e(TAG, "Error inserting SQLConstraintException " + values, e);
            return -1; // Error message for integrity constraint violation
        }
        catch (SQLException e) {
            Log.e(TAG, "Error inserting SQLException " + values, e);
            return -2;
        }
        catch (Exception e) {
            Log.e(TAG, "Error inserting  Exception" + values, e);
            return -3;
        }


    }










    //
//    public int updateEntry(Player player) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Creating content values
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, student.name);
//        values.put(KEY_PHONENUMBER, student.phone_number);
//
//        // update row in students table base on students.is value
//        return db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(student.id) });
//    }
//
//    /**
//     * Used to delete particular student entry
//     *
//     * @param id
//     */
//    public void deleteEntry(long id) {
//
//        // delete row in students table based on id
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
//
//    /**
//     * Used to get particular student details
//     *
//     * @param id
//     * @return
//     */
//
//    public StudentsModel getStudent(long id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // SELECT * FROM students WHERE id = ?;
//        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE "
//                + KEY_ID + " = " + id;
//        Log.d(TAG, selectQuery);
//
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//        StudentsModel students = new StudentsModel();
//        students.id = c.getInt(c.getColumnIndex(KEY_ID));
//        students.phone_number = c.getString(c.getColumnIndex(KEY_PHONENUMBER));
//        students.name = c.getString(c.getColumnIndex(KEY_NAME));
//
//        return students;
//    }
//
//    /**
//     * Used to get detail of entire database and save in array list of data type
//     * StudentsModel
//     *
//     * @return
//     */


    public List<PlayerView> getAllPlayerListDescendingScore() {
        List<PlayerView> playerArrayList = new ArrayList<PlayerView>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYER+"  ORDER BY " +KEY_SCORE + " DESC";
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                PlayerView player= new PlayerView();

                player.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                player.setName(c.getString(c.getColumnIndex(KEY_USERNAME)));
                player.setScore(c.getInt(c.getColumnIndex(KEY_SCORE)));
                player.setGold(c.getInt(c.getColumnIndex(KEY_GOLD)));
                player.setTime(c.getInt(c.getColumnIndex(KEY_TIME)));


                // adding to Students list
                playerArrayList.add(player);
            } while (c.moveToNext());
        }

        c.close();
        return playerArrayList;
    }

    public Boolean checkIfPlayerExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();


        SQLiteStatement sqLiteStatement = db.compileStatement("select count(1) from PLAYER where USERNAME='" + name + "'; ");

        long count = sqLiteStatement.simpleQueryForLong();
        if (count==1) {
            return true;
        }

        return false;

    }
    public Player loadProfile(String name) {
        SQLiteDatabase db = this.getReadableDatabase();



        String selectQuery = "SELECT  * FROM " + TABLE_PLAYER + " WHERE "
                + KEY_USERNAME + " = '" + name +"'";
        Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Player player =Player.getInstance();

        player.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        player.setScore(c.getInt(c.getColumnIndex(KEY_SCORE)));
        player.setGold(c.getInt(c.getColumnIndex(KEY_GOLD)));
        player.setTime(c.getInt(c.getColumnIndex(KEY_TIME)));
        player.setName(c.getString(c.getColumnIndex(KEY_USERNAME)));

        c.close();
        return player;
    }



    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<Item>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS+ ";";
        Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Item item= new Item();

                item.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                item.setName(c.getString(c.getColumnIndex(KEY_ITEM_NAME)));
                item.setValue(c.getInt(c.getColumnIndex(KEY_PURCHASE_VALUE)));


                // adding to Students list
                itemList.add(item);
            } while (c.moveToNext());
        }

        c.close();
        return itemList;
    }


    public long insertDefaultPlayerItem(PlayerItem playerItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_ID, playerItem.getPlayerId());
        values.put(KEY_ITEM_ID, playerItem.getItemId());
        values.put(KEY_ITEM_QUANTITY, playerItem.getItemQuantity());

        // insert row in player table
        try {
            long insert = db.insert(TABLE_PLAYER_ITEM, null, values);
            return insert;
        }

        catch(SQLiteConstraintException e)
        {
            Log.e(TAG, "Error inserting SQLConstraintException " + values, e);
            return -1; // Error message for integrity constraint violation
        }
        catch (SQLException e) {
            Log.e(TAG, "Error inserting SQLException " + values, e);
            return -2;
        }
        catch (Exception e) {
            Log.e(TAG, "Error inserting  Exception" + values, e);
            return -3;
        }
    }


    public List<Item> loadPlayerItems(Integer id) {
        List<Item> playerItems = new ArrayList<Item>();
        String selectQuery = "SELECT PI.ITEM_ID,I.PURCHASE_VALUE, PI.ITEM_QUANTITY,I.ITEM_NAME FROM PLAYER_ITEMS PI,ITEMS I WHERE PI.PLAYER_ID ="+
                id +" AND PI.ITEM_ID=I.ID ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {

                Item item= new Item();
                item.setId(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
                item.setValue(c.getInt(c.getColumnIndex(KEY_PURCHASE_VALUE)));
                item.setName(c.getString(c.getColumnIndex(KEY_ITEM_NAME)));
                item.setCount(c.getInt(c.getColumnIndex(KEY_ITEM_QUANTITY)));
                playerItems.add(item);
            } while (c.moveToNext());
        }

        c.close();

        return playerItems;
    }

    public List<Mutator> loadPlayerMutators(Integer id) {
        List<Mutator> playerMutatorList = new ArrayList<Mutator>();

        String selectQuery = "SELECT PM.MUTATOR_ID, M.MUTATOR_NAME, M.MUTATOR_COLOR FROM PLAYER_MUTATOR PM, MUTATOR M WHERE PM.PLAYER_ID ="+
                id +" AND PM.MUTATOR_ID=M.MUTATOR_ID ";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Mutator mutator= new Mutator();

                mutator.setId(c.getInt(c.getColumnIndex(KEY_MUTATOR_ID)));
                mutator.setName(c.getString(c.getColumnIndex(KEY_MUTATOR_NAME)));
                mutator.setColor(c.getString(c.getColumnIndex(KEY_MUTATOR_COLOR)));

                playerMutatorList.add(mutator);
            } while (c.moveToNext());
        }

        c.close();
        return playerMutatorList;
    }

    public List<Mutator> getAllMutators()
    {
        List<Mutator> mutatorList = new ArrayList<Mutator>();
        String selectQuery = "SELECT  * FROM " + TABLE_MUTATOR + ";";
        Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mutator mutator= new Mutator();
                mutator.setId(c.getInt(c.getColumnIndex(KEY_MUTATOR_ID)));
                mutator.setName(c.getString(c.getColumnIndex(KEY_MUTATOR_NAME)));
                mutator.setColor(c.getString(c.getColumnIndex(KEY_MUTATOR_COLOR)));
                mutator.setValue(c.getInt(c.getColumnIndex(KEY_MUTATOR_VALUE)));
                mutatorList.add(mutator);
            } while (c.moveToNext());
        }

        c.close();
        return mutatorList;
    }


    public long insertPlayerMutator(PlayerMutator playerMutator) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_ID, playerMutator.getPlayerId());
        values.put(KEY_MUTATOR_ID, playerMutator.getMutatorId());

        // insert row in player table
        try {
            long insert = db.insert(TABLE_PLAYER_MUTATOR, null, values);
            return insert;
        }

        catch(SQLiteConstraintException e)
        {
            Log.e(TAG, "Error inserting SQLConstraintException " + values, e);
            return -1; // Error message for integrity constraint violation
        }
        catch (SQLException e) {
            Log.e(TAG, "Error inserting SQLException " + values, e);
            return -2;
        }
        catch (Exception e) {
            Log.e(TAG, "Error inserting  Exception" + values, e);
            return -3;
        }
    }


    public void updatePlayerGold() {
        SQLiteDatabase db = this.getWritableDatabase();
        Player player=Player.getInstance();
        // Creating content values
        String strSQL = "UPDATE PLAYER SET GOLD ="+player.getGold()+" WHERE ID = "+ player.getId();

        db.execSQL(strSQL);

    }
    public int updatePlayerGoldVlaue(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues valuesPlayer = new ContentValues();
        valuesPlayer.put(KEY_GOLD, player.getGold());

        // update row in students table base on students.is value
        return db.update(TABLE_PLAYER, valuesPlayer, KEY_ID + " = ?",
                new String[] { String.valueOf(player.getId()) });
    }
    public int updatePlayerItemCount(Player player, String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Creating content values
        ContentValues valuesPlayerItem = new ContentValues();
        valuesPlayerItem.put(KEY_ITEM_QUANTITY, player.getItemCount(itemName));

        // update row in students table base on students.is value
        return db.update(TABLE_PLAYER_ITEM, valuesPlayerItem, KEY_PLAYER_ID + " = ?",
                new String[] { String.valueOf(player.getId()) });
    }

}

