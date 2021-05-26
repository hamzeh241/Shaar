package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryDetailsChipModel {
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    @Override
    public String toString() {
        return title;
    }
}
