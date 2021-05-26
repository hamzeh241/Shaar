package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class CarChipsListModel {
  int id;
  String title;
  boolean selected;



  public CarChipsListModel(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public CarChipsListModel() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
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
