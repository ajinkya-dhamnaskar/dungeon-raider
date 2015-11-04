package com.group2.dungeonraider.domain;

import java.sql.Time;
import java.util.List;
import com.group2.dungeonraider.utilities.Constants;


/**
 * Created by Rohit on 10/27/2015.
 */

public class Player {
    private int id;
    private String name;
    private int score;
    private int gold;
    private int time;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    private List<Item> itemList;




    public int getItemCount(String itemName)
    {
        List<Item> lstItem = this.getItemList();
        int count = 0;
        for (Item item: lstItem)
        {
            if(item.getName().equals(Constants.ITEM_BOMB)) {
                count = item.getCount();
                break;
            }
            else if(item.getName().equals(Constants.ITEM_KEY)) {
                count = item.getCount();
                break;
            }
            else if(item.getName().equals(Constants.ITEM_POTION)) {
                count = item.getCount();
                break;
            }
            else{
                count = item.getCount();
                break;
            }
        }
        return count;
    }

    public int setItemCount(String itemName,int itemCount)
    {
        List<Item> lstItem = this.getItemList();
        for (Item item: lstItem)
        {
            if(item.getName().equals(Constants.ITEM_BOMB)) {
                item.setCount(itemCount);
                break;
            }
            else if(item.getName().equals(Constants.ITEM_KEY)) {
                item.setCount(itemCount);
                break;
            }
            else if(item.getName().equals(Constants.ITEM_POTION)) {
                item.setCount(itemCount);
                break;
            }
            else{
                item.setCount(itemCount);
                break;
            }
        }
        return itemCount;
    }


    private  volatile static Player instance;

    private Player()
    {
        if (instance!= null)
        {
            throw new IllegalStateException("Already initialized " + "Instance already created.");
        }
    }

    //Get the only object available

    public static Player getInstance()
    {
        if(instance==null)
        {
            synchronized(Player.class)
            {
                if(instance==null)
                {
                    instance=new Player();
                }
            }

        }
        return instance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", score=").append(score);
        sb.append(", gold=").append(gold);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
    }


}
