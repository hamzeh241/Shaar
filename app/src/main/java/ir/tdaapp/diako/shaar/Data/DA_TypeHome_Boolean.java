package ir.tdaapp.diako.shaar.Data;

/**
 * Created by Diako on 6/18/2019.
 */

public class DA_TypeHome_Boolean {
    private int Id;
    private Boolean Val;

    public DA_TypeHome_Boolean(int Id,Boolean Val){
        this.Id=Id;
        this.Val=Val;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Boolean getVal() {
        return Val;
    }

    public void setVal(Boolean val) {
        Val = val;
    }
}
