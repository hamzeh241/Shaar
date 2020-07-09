package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 6/30/2019.
 */

//این کلاس برای برای اسپینر موقعیت صفحه AddHome می باشد
public class Location {

    private int Id;
    private String Title;

    public Location(int Id,String Title){
        this.Id=Id;
        this.Title=Title;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return Title;
    }
}
