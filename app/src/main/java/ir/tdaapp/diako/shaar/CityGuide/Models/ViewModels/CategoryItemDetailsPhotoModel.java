package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryItemDetailsPhotoModel {

  String imageName;

  public CategoryItemDetailsPhotoModel(String imageName) {
    this.imageName = imageName;
  }

  public CategoryItemDetailsPhotoModel() {
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
}
