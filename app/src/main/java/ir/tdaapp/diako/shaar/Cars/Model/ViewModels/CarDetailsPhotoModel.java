package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class CarDetailsPhotoModel {

  String imageName;

  public CarDetailsPhotoModel(String imageName) {
    this.imageName = imageName;
  }

  public CarDetailsPhotoModel() {
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
}
