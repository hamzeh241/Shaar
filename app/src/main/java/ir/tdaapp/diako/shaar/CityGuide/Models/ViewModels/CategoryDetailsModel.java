package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryDetailsModel {

  String title, address, imageUrl;
  int id, rateCount,rating;
  boolean isFavorite;

  public CategoryDetailsModel() {
  }

  public CategoryDetailsModel(String title, String address, String imageUrl, int id, int rateCount, int rating, boolean isFavorite) {
    this.title = title;
    this.address = address;
    this.imageUrl = imageUrl;
    this.id = id;
    this.rateCount = rateCount;
    this.rating = rating;
    this.isFavorite = isFavorite;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRateCount() {
    return rateCount;
  }

  public void setRateCount(int rateCount) {
    this.rateCount = rateCount;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }
}
