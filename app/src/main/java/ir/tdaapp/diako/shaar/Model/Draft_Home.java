package ir.tdaapp.diako.shaar.Model;

import android.graphics.Bitmap;

/**
 * Created by Diako on 7/8/2019.
 */

public class Draft_Home {

    private int Id;
    private String Address;
    private int CountRoom;
    private String Price;
    private Bitmap Image;

    public Draft_Home(String Address,int CountRoom,String Price,Bitmap Image,int Id){
        this.Address=Address;
        this.CountRoom=CountRoom;
        this.Price=Price;
        this.Image=Image;
        this.Id=Id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getCountRoom() {
        return CountRoom;
    }

    public void setCountRoom(int countRoom) {
        CountRoom = countRoom;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
