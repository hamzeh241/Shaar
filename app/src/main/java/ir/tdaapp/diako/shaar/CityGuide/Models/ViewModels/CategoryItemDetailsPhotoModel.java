package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryItemDetailsPhotoModel {

  int id;
  String url;

  public CategoryItemDetailsPhotoModel(int id, String url) {
    this.id = id;
    this.url = url;
  }

  public CategoryItemDetailsPhotoModel() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
