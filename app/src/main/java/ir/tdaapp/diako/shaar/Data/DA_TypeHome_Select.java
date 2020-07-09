package ir.tdaapp.diako.shaar.Data;

/**
 * Created by Diako on 6/18/2019.
 */

public class DA_TypeHome_Select {
    private int Id;
    private String Val;
    private int Position;

    public DA_TypeHome_Select(int Id,String Val,int Position){
        this.Id=Id;
        this.Val=Val;
        this.Position=Position;
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

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }
}
