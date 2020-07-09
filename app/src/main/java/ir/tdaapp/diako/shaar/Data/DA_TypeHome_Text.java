package ir.tdaapp.diako.shaar.Data;

/**
 * Created by Diako on 6/18/2019.
 */

public class DA_TypeHome_Text {
    private int Id;
    private String Val;

    public DA_TypeHome_Text(int Id,String Val){
        this.Id=Id;
        this.Val=Val;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getVal() {
        return Val;
    }

    public void setVal(String val) {
        Val = val;
    }
}
