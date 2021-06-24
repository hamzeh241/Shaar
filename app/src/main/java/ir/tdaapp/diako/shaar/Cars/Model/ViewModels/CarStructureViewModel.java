package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

import java.math.BigInteger;
import java.util.ArrayList;

public class CarStructureViewModel {

  int brandId, engineStatusId, chassisStatusId, bodyStatusId,
    insuranceDeadlineId, gearboxId, documentId, categoryId, sellTypeId, productionYearId, userId, color;
  BigInteger price,function;
  boolean exchange;
  String title, description, address, phone;
  ArrayList<String> images;

  public CarStructureViewModel() {
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public BigInteger getFunction() {
    return function;
  }

  public void setFunction(BigInteger function) {
    this.function = function;
  }

  public BigInteger getPrice() {
    return price;
  }

  public void setPrice(BigInteger price) {
    this.price = price;
  }

  public int getBrandId() {
    return brandId;
  }

  public void setBrandId(int brandId) {
    this.brandId = brandId;
  }

  public int getEngineStatusId() {
    return engineStatusId;
  }

  public void setEngineStatusId(int engineStatusId) {
    this.engineStatusId = engineStatusId;
  }

  public int getChassisStatusId() {
    return chassisStatusId;
  }

  public void setChassisStatusId(int chassisStatusId) {
    this.chassisStatusId = chassisStatusId;
  }

  public int getBodyStatusId() {
    return bodyStatusId;
  }

  public void setBodyStatusId(int bodyStatusId) {
    this.bodyStatusId = bodyStatusId;
  }

  public int getInsuranceDeadlineId() {
    return insuranceDeadlineId;
  }

  public void setInsuranceDeadlineId(int insuranceDeadlineId) {
    this.insuranceDeadlineId = insuranceDeadlineId;
  }

  public int getGearboxId() {
    return gearboxId;
  }

  public void setGearboxId(int gearboxIs) {
    this.gearboxId = gearboxIs;
  }

  public int getDocumentId() {
    return documentId;
  }

  public void setDocumentId(int documentId) {
    this.documentId = documentId;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getSellTypeId() {
    return sellTypeId;
  }

  public void setSellTypeId(int sellTypeId) {
    this.sellTypeId = sellTypeId;
  }

  public int getProductionYearId() {
    return productionYearId;
  }

  public void setProductionYearId(int productionYearId) {
    this.productionYearId = productionYearId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public boolean isExchange() {
    return exchange;
  }

  public void setExchange(boolean exchange) {
    this.exchange = exchange;
  }

  public ArrayList<String> getImages() {
    return images;
  }

  public void setImages(ArrayList<String> images) {
    this.images = images;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
