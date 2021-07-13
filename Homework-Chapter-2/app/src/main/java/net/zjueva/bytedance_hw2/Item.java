package net.zjueva.bytedance_hw2;

public class Item {
    private int id;
    private String title;
    private String hotDegree;
    public Item(){

    }
    public Item(int i,String b, String c){
        id=i;
        title=b;
        hotDegree=c;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(String hotDegree) {
        this.hotDegree = hotDegree;
    }
}
