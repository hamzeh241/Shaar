package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class SearchModel {

  String text;
  int categoryId, page, brandId, gearboxId, fromDateId, toDateId;
  double fromPrice, toPrice;

  public SearchModel() {
  }

  public SearchModel(String text, int categoryId, int page, int brandId, int gearboxId, int fromDateId, int toDateId, double fromPrice, double toPrice) {
    this.text = text;
    this.categoryId = categoryId;
    this.page = page;
    this.brandId = brandId;
    this.gearboxId = gearboxId;
    this.fromDateId = fromDateId;
    this.toDateId = toDateId;
    this.fromPrice = fromPrice;
    this.toPrice = toPrice;
  }

  public int getBrandId() {
    return brandId;
  }

  public void setBrandId(int brandId) {
    this.brandId = brandId;
  }

  public int getGearboxId() {
    return gearboxId;
  }

  public void setGearboxId(int gearboxId) {
    this.gearboxId = gearboxId;
  }

  public int getFromDateId() {
    return fromDateId;
  }

  public void setFromDateId(int fromDateId) {
    this.fromDateId = fromDateId;
  }

  public int getToDateId() {
    return toDateId;
  }

  public void setToDateId(int toDateId) {
    this.toDateId = toDateId;
  }

  public double getFromPrice() {
    return fromPrice;
  }

  public void setFromPrice(double fromPrice) {
    this.fromPrice = fromPrice;
  }

  public double getToPrice() {
    return toPrice;
  }

  public void setToPrice(double toPrice) {
    this.toPrice = toPrice;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }
}
