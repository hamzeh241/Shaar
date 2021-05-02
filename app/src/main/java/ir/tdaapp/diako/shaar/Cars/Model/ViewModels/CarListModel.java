package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class CarListModel {

  int id;
  String title, productionYear, price, gearbox, mileage, brand, color, imageUrl;

  public CarListModel() {
  }

  public CarListModel(int id, String title, String productionYear, String price, String gearbox, String mileage, String brand, String color, String imageUrl) {
    this.id = id;
    this.title = title;
    this.productionYear = productionYear;
    this.price = price;
    this.gearbox = gearbox;
    this.mileage = mileage;
    this.brand = brand;
    this.color = color;
    this.imageUrl = imageUrl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(String productionYear) {
    this.productionYear = productionYear;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getGearbox() {
    return gearbox;
  }

  public void setGearbox(String gearbox) {
    this.gearbox = gearbox;
  }

  public String getMileage() {
    return mileage;
  }

  public void setMileage(String mileage) {
    this.mileage = mileage;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
