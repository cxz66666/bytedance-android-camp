package net.zjueva.bytedance_hw2;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ItemLab {
    private static ItemLab sItemLab;
    private List<Item>mItems;

    public static ItemLab get(Context context){
        if(sItemLab==null){
            sItemLab=new ItemLab(context);
        }
        return sItemLab;
    }
    private ItemLab(Context context){
        mItems=new ArrayList<>();
        mItems.add(new Item(1,"让人忘记原唱的歌手", "524.6w"));
        mItems.add(new Item(2,"林丹退役", "433.6w"));
        mItems.add(new Item(3,"你在教我做事？", "357.8w"));
        mItems.add(new Item(4,"投身乡村教育的燃灯者", "333.6w"));
        mItems.add(new Item(5,"暑期嘉年华", "285.6w"));
        mItems.add(new Item(6,"2020年三伏天有40天", "183.2w"));
        mItems.add(new Item(7,"会跟游客合照的老虎", "139.4w"));
        mItems.add(new Item(8,"苏州暴雨", "75.6w"));
        mItems.add(new Item(9,"6月全国菜价上涨", "55w"));
        mItems.add(new Item(10,"猫的第六感有多强", "43w"));
        mItems.add(new Item(11,"IU真好看", "22.2w"));
        mItems.add(new Item(12,"让人忘记原唱的歌手", "524.6w"));
        mItems.add(new Item(13,"林丹退役", "433.6w"));
        mItems.add(new Item(14,"你在教我做事？", "357.8w"));
        mItems.add(new Item(15,"投身乡村教育的燃灯者", "333.6w"));
        mItems.add(new Item(16,"暑期嘉年华", "285.6w"));
        mItems.add(new Item(17,"2020年三伏天有40天", "183.2w"));
        mItems.add(new Item(18,"会跟游客合照的老虎", "139.4w"));
        mItems.add(new Item(19,"苏州暴雨", "75.6w"));
        mItems.add(new Item(20,"6月全国菜价上涨", "55w"));
        mItems.add(new Item(21,"猫的第六感有多强", "43w"));
        mItems.add(new Item(22,"IU真好看", "22.2w"));
    }
    public List<Item>getItems(){
        return mItems;
    }
    public Item getItems(int id){
        for(Item item:mItems){
            if(item.getId()==id){
                return item;
            }
        }
        return null;
    }
}
