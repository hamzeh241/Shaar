package ir.tdaapp.diako.shaar.Data;

/**
 * Created by Diako on 6/18/2019.
 */

public class DA_TypeHome_Number {
    private int Id;
    private int Val;

    public DA_TypeHome_Number(int Id,int Val){
        this.Id=Id;
        this.Val=Val;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getVal() {
        return Val;
    }

    public void setVal(int val) {
        Val = val;
    }
}
