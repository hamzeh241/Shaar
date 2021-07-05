package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

import android.net.Uri;

public class AddItemPhotosModel {

  Uri uri;
  String filePath;

  public AddItemPhotosModel(Uri uri) {
    this.uri = uri;
  }

  public AddItemPhotosModel() {
  }

  public AddItemPhotosModel(String filePath) {
    this.filePath = filePath;
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
}
