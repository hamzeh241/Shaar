package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class ResultViewModel {

  String title;
  int code;
  boolean status;

  public ResultViewModel() {
  }

  public ResultViewModel(String title, int code, boolean status) {
    this.title = title;
    this.code = code;
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public boolean getStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
