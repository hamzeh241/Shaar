package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 7/15/2019.
 */

public class Published_Home {

    private String Title,Price,Address,Image;
    private int Id,Room;

    public Published_Home(String Title,String Price,String Address,String Image,int Id,int Room){
        this.Title=Title;
        this.Price=Price;
        this.Address=Address;
        this.Image=Image;
        this.Id=Id;
        this.Room=Room;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

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

    public int getRoom() {
        return Room;
    }

    public void setRoom(int room) {
        Room = room;
    }
}
