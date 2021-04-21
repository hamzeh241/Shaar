package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryItemDetailsCommentsModel {

  int id;
  int dislikes, likes;
  String userAvatar, date, message, phoneNumber;
  boolean isLiked, isDisliked;


  public CategoryItemDetailsCommentsModel() {
  }

  public CategoryItemDetailsCommentsModel(int id, int dislikes, int likes, String userAvatar, String date, String message, String phoneNumber, boolean isLiked, boolean isDisliked) {
    this.id = id;
    this.dislikes = dislikes;
    this.likes = likes;
    this.userAvatar = userAvatar;
    this.date = date;
    this.message = message;
    this.phoneNumber = phoneNumber;
    this.isLiked = isLiked;
    this.isDisliked = isDisliked;
  }

  public int getDislikes() {
    return dislikes;
  }

  public void setDislikes(int dislikes) {
    this.dislikes = dislikes;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public String getUserAvatar() {
    return userAvatar;
  }

  public void setUserAvatar(String userAvatar) {
    this.userAvatar = userAvatar;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public boolean isLiked() {
    return isLiked;
  }

  public void setLiked(boolean liked) {
    isLiked = liked;
  }

  public boolean isDisliked() {
    return isDisliked;
  }

  public void setDisliked(boolean disliked) {
    isDisliked = disliked;
  }
}
