package com.example.chapter3.homework;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemLab {
    private static ItemLab sItemLab;
    private List<Item> mItems;

    public static ItemLab get(Context context){
        if(sItemLab==null){
            sItemLab=new ItemLab(context);
        }
        return sItemLab;
    }
    private ItemLab(Context context){
        mItems=new ArrayList<>();
        mItems.add(new Item("陈旭征","让人忘记原唱的歌手"));
        mItems.add(new Item("陈旭征","林丹退役"));
        mItems.add(new Item("陈旭征","你在教我做事？"));
        mItems.add(new Item("陈旭征","投身乡村教育的燃灯者"));
        mItems.add(new Item("陈旭征","暑期嘉年华"));
        mItems.add(new Item("陈旭征","2020年三伏天有40天"));
        mItems.add(new Item("陈旭征","会跟游客合照的老虎"));
        mItems.add(new Item("陈旭征","苏州暴雨"));
        mItems.add(new Item("陈旭征","6月全国菜价上涨"));
        mItems.add(new Item("陈旭征","猫的第六感有多强"));
        mItems.add(new Item("陈旭征","IU真好看"));
        mItems.add(new Item("陈旭征","让人忘记原唱的歌手"));
        mItems.add(new Item("陈旭征","林丹退役"));
        mItems.add(new Item("陈旭征","你在教我做事？"));
        mItems.add(new Item("陈旭征","投身乡村教育的燃灯者"));
        mItems.add(new Item("陈旭征","暑期嘉年华"));
        mItems.add(new Item("陈旭征","2020年三伏天有40天"));
        mItems.add(new Item("陈旭征","会跟游客合照的老虎"));
        mItems.add(new Item("陈旭征","苏州暴雨"));
        mItems.add(new Item("陈旭征","6月全国菜价上涨"));
        mItems.add(new Item("陈旭征","猫的第六感有多强"));
        mItems.add(new Item("陈旭征","IU真好看"));
    }
    public List<Item>getItems(){
        return mItems;
    }
    public Item getItems(UUID id){
        for(Item item:mItems){
            if(item.getUuid()==id){
                return item;
            }
        }
        return null;
    }
}
