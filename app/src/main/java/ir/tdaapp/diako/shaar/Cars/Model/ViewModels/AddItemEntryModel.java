package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class AddItemEntryModel {
  int id;
  String title, titleShamsi, titleMiladi;

  public AddItemEntryModel(int id, String title) {
    this.id = id;
    this.title = title;
  }

  public AddItemEntryModel() {
  }

  public AddItemEntryModel(int id, String titleShamsi, String titleMiladi) {
    this.id = id;
    this.titleShamsi = titleShamsi;
    this.titleMiladi = titleMiladi;
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
    if (title == null) {
      return titleShamsi + " - " + titleMiladi;
    } else {
      return title;
    }
  }

  public String getTitleShamsi() {
    return titleShamsi;
  }

  public void setTitleShamsi(String titleShamsi) {
    this.titleShamsi = titleShamsi;
  }

  public String getTitleMiladi() {
    return titleMiladi;
  }

  public void setTitleMiladi(String titleMiladi) {
    this.titleMiladi = titleMiladi;
  }
}
