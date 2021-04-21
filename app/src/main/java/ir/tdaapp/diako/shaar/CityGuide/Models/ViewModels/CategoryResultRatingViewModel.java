package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CategoryResultRatingViewModel {

  boolean status;
  int code;
  String message;

  public CategoryResultRatingViewModel(boolean status, int code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public CategoryResultRatingViewModel() {
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
