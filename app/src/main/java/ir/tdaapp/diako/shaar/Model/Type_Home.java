package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 5/11/2019.
 */

public class Type_Home {

    private int Id;
    private String Text;

    public Type_Home(String Text,int Id){
        this.Text=Text;
        this.Id=Id;
    }

    public void SetText(String Text) {
        this.Text = Text;
    }

    public String GetText() {
        return this.Text;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
