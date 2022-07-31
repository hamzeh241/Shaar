package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryDetailsChipModel {

    int id;
    String title;
    boolean isSelected;

    public CategoryDetailsChipModel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public CategoryDetailsChipModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public String toString() {
        return title;
    }
}
