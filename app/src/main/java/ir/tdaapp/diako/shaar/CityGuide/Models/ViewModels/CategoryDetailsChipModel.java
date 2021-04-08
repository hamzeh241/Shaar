package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryDetailsChipModel {

  int id;
  String title;

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
}
