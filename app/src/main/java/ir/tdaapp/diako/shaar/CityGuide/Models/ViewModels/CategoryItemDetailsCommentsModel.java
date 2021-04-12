package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryItemDetailsCommentsModel {

  int dislikes, likes;
  String user, date, message;

  public CategoryItemDetailsCommentsModel(int dislikes, int likes, String user, String date, String message) {
    this.dislikes = dislikes;
    this.likes = likes;
    this.user = user;
    this.date = date;
    this.message = message;
  }

  public CategoryItemDetailsCommentsModel() {
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

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
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
}
