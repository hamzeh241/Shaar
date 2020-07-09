package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 5/16/2019.
 */

public class List_Home {

    private String Price,Address,Discription,Time,CountRoom,Area,Image,CountImage;
    private int Id;
    private boolean Special,IsFavorit;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCountRoom() {
        return CountRoom;
    }

    public void setCountRoom(String countRoom) {
        CountRoom = countRoom;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCountImage() {
        return CountImage;
    }

    public void setCountImage(String countImage) {
        CountImage = countImage;
    }

    public boolean isSpecial() {
        return Special;
    }

    public void setSpecial(boolean special) {
        Special = special;
    }

    public boolean isFavorit() {
        return IsFavorit;
    }

    public void setFavorit(boolean favorit) {
        IsFavorit = favorit;
    }
}
