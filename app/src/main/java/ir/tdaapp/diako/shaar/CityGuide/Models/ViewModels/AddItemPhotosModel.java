package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

import android.net.Uri;

public class AddItemPhotosModel {

  Uri uri;

  public AddItemPhotosModel(Uri uri) {
    this.uri = uri;
  }

  public AddItemPhotosModel() {
  }

  public Uri getUri() {
    return uri;
  }

  public void setUri(Uri uri) {
    this.uri = uri;
  }
}
