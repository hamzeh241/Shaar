package ir.tdaapp.diako.shaar.ViewModel;

/**
 * Created by Diako on 7/30/2019.
 */

public class VM_List_Search {
    private int Id;
    private String Title;

    public VM_List_Search(){}
    public VM_List_Search(int Id,String Title){
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
}
