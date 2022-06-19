package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

public class CityModel {

  private int id;
  private String name;

  public CityModel(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public CityModel() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
