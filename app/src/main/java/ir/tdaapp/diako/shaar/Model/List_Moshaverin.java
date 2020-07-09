package ir.tdaapp.diako.shaar.Model;

/**
 * Created by Diako on 5/25/2019.
 */

public class List_Moshaverin {
    private String Name;
    private int Type;
    private boolean Active;

    public List_Moshaverin(String Name,int Type,boolean Active){
        this.Name=Name;
        this.Type=Type;
        this.Active=Active;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
