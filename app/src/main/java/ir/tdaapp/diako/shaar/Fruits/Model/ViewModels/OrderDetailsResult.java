package ir.tdaapp.diako.shaar.Fruits.Model.ViewModels;

public class OrderDetailsResult {

  int id;
  String dateCreate, fruitName, range, imageUrl;
  double unitPrice, weight;
  boolean typeEnum;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDateCreate() {
    return dateCreate;
  }

  public void setDateCreate(String dateCreate) {
    this.dateCreate = dateCreate;
  }

  public String getFruitName() {
    return fruitName;
  }

  public void setFruitName(String fruitName) {
    this.fruitName = fruitName;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public boolean isTypeEnum() {
    return typeEnum;
  }

  public void setTypeEnum(boolean typeEnum) {
    this.typeEnum = typeEnum;
  }
}
