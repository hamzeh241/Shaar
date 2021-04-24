package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

import java.util.ArrayList;

public class CategoryItemDetailsViewModel {

  int starCount, rateCount, userScore;
  String title, categoryTitle, cellPhone, tel1, tel2, instagramId, telegramId, address, description;

  ArrayList<CategoryItemDetailsCommentsModel> commentsModels;
  ArrayList<CategoryItemDetailsPhotoModel> photoModels;
  ArrayList<CategoryItemDetailsPhotoModel> sliderModels;

  public CategoryItemDetailsViewModel() {
  }

  public CategoryItemDetailsViewModel(int starCount, int rateCount, int userScore, String title, String categoryTitle, String cellPhone, String tel1, String tel2, String instagramId, String telegramId, String address, String description, ArrayList<CategoryItemDetailsCommentsModel> commentsModels, ArrayList<CategoryItemDetailsPhotoModel> photoModels, ArrayList<CategoryItemDetailsPhotoModel> sliderModels) {
    this.starCount = starCount;
    this.rateCount = rateCount;
    this.userScore = userScore;
    this.title = title;
    this.categoryTitle = categoryTitle;
    this.cellPhone = cellPhone;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.instagramId = instagramId;
    this.telegramId = telegramId;
    this.address = address;
    this.description = description;
    this.commentsModels = commentsModels;
    this.photoModels = photoModels;
    this.sliderModels = sliderModels;
  }

  public int getStarCount() {
    return starCount;
  }

  public void setStarCount(int starCount) {
    this.starCount = starCount;
  }

  public int getRateCount() {
    return rateCount;
  }

  public void setRateCount(int rateCount) {
    this.rateCount = rateCount;
  }

  public int getUserScore() {
    return userScore;
  }

  public void setUserScore(int userScore) {
    this.userScore = userScore;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategoryTitle() {
    return categoryTitle;
  }

  public void setCategoryTitle(String categoryTitle) {
    this.categoryTitle = categoryTitle;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getTel1() {
    return tel1;
  }

  public void setTel1(String tel1) {
    this.tel1 = tel1;
  }

  public String getTel2() {
    return tel2;
  }

  public void setTel2(String tel2) {
    this.tel2 = tel2;
  }

  public String getInstagramId() {
    return instagramId;
  }

  public void setInstagramId(String instagramId) {
    this.instagramId = instagramId;
  }

  public String getTelegramId() {
    return telegramId;
  }

  public void setTelegramId(String telegramId) {
    this.telegramId = telegramId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<CategoryItemDetailsCommentsModel> getCommentsModels() {
    return commentsModels;
  }

  public void setCommentsModels(ArrayList<CategoryItemDetailsCommentsModel> commentsModels) {
    this.commentsModels = commentsModels;
  }

  public ArrayList<CategoryItemDetailsPhotoModel> getPhotoModels() {
    return photoModels;
  }

  public void setPhotoModels(ArrayList<CategoryItemDetailsPhotoModel> photoModels) {
    this.photoModels = photoModels;
  }

  public ArrayList<CategoryItemDetailsPhotoModel> getSliderModels() {
    return sliderModels;
  }

  public void setSliderModels(ArrayList<CategoryItemDetailsPhotoModel> sliderModels) {
    this.sliderModels = sliderModels;
  }
}
