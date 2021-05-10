package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class FilterModel {

  int brandId, gearboxId, fromDateId, toDateId;
  double fromPrice, toPrice;

  public FilterModel(int brandId, int gearboxId, int fromDateId, int toDateId, double fromPrice, double toPrice) {
    this.brandId = brandId;
    this.gearboxId = gearboxId;
    this.fromDateId = fromDateId;
    this.toDateId = toDateId;
    this.fromPrice = fromPrice;
    this.toPrice = toPrice;
  }

  public FilterModel() {
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
}
