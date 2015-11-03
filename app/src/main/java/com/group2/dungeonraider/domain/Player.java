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
    private List<PlayerItem> itemList;


    public List<PlayerItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PlayerItem> itemList) {
        this.itemList = itemList;
    }

    public int getItemCount(String itemName)
    {
        List<PlayerItem> lstItem = this.getItemList();
        int count = 0;
        for (PlayerItem item: lstItem)
        {
            if(item.getItemName().equals(Constants.ITEM_BOMB)) {
                count = item.getItemQuantity();
                break;
            }
            else if(item.getItemName().equals(Constants.ITEM_KEY)) {
                count = item.getItemQuantity();
                break;
            }
            else if(item.getItemName().equals(Constants.ITEM_POTION)) {
                count = item.getItemQuantity();
                break;
            }
            else{
                count = item.getItemQuantity();
                break;
            }
        }
        return count;
    }

    public int setItemCount(String itemName,int itemCount)
    {
        List<PlayerItem> lstItem = this.getItemList();
        for (PlayerItem item: lstItem)
        {
            if(item.getItemName().equals(Constants.ITEM_BOMB)) {
                item.setItemQuantity(itemCount);
                break;
            }
            else if(item.getItemName().equals(Constants.ITEM_KEY)) {
                item.setItemQuantity(itemCount);
                break;
            }
            else if(item.getItemName().equals(Constants.ITEM_POTION)) {
                item.setItemQuantity(itemCount);
                break;
            }
            else{
                item.setItemQuantity(itemCount);
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
