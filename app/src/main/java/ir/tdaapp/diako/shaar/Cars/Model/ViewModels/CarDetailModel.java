package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

import java.util.ArrayList;

public class CarDetailModel {
  int id;
  String name, productionYear, mileage, carBodyStatus, brand,
    chassisStatus, engineStatus, insuranceTime, gearBox, document,
    salesType, price, description, expertName, expertImage, phone, address ,color;
  boolean exchange;
  ArrayList<CarDetailsPhotoModel> photos;

  public CarDetailModel() {

  }

  public CarDetailModel(int id,String color, String name, String productionYear, String mileage, String carBodyStatus, String brand, String chassisStatus, String engineStatus, String insuranceTime, String gearBox, String document, String salesType, String price, String description, String expertName, String expertImage, String phone, String address, boolean exchange, ArrayList<CarDetailsPhotoModel> photos) {
    this.id = id;
    this.name = name;
    this.productionYear = productionYear;
    this.mileage = mileage;
    this.carBodyStatus = carBodyStatus;
    this.brand = brand;
    this.chassisStatus = chassisStatus;
    this.engineStatus = engineStatus;
    this.insuranceTime = insuranceTime;
    this.gearBox = gearBox;
    this.document = document;
    this.salesType = salesType;
    this.price = price;
    this.description = description;
    this.expertName = expertName;
    this.expertImage = expertImage;
    this.phone = phone;
    this.address = address;
    this.exchange = exchange;
    this.photos = photos;
    this.color = color;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
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

  public String getProductionYear() {
    return productionYear;
  }

  public void setProductionYear(String productionYear) {
    this.productionYear = productionYear;
  }

  public String getMileage() {
    return mileage;
  }

  public void setMileage(String mileage) {
    this.mileage = mileage;
  }

  public String getCarBodyStatus() {
    return carBodyStatus;
  }

  public void setCarBodyStatus(String carBodyStatus) {
    this.carBodyStatus = carBodyStatus;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getChassisStatus() {
    return chassisStatus;
  }

  public void setChassisStatus(String chassisStatus) {
    this.chassisStatus = chassisStatus;
  }

  public String getInsuranceTime() {
    return insuranceTime;
  }

  public void setInsuranceTime(String insuranceTime) {
    this.insuranceTime = insuranceTime;
  }

  public String getGearBox() {
    return gearBox;
  }

  public void setGearBox(String gearBox) {
    this.gearBox = gearBox;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(String document) {
    this.document = document;
  }

  public String getSalesType() {
    return salesType;
  }

  public void setSalesType(String salesType) {
    this.salesType = salesType;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ArrayList<CarDetailsPhotoModel> getPhotos() {
    return photos;
  }

  public void setPhotos(ArrayList<CarDetailsPhotoModel> photos) {
    this.photos = photos;
  }

  public String getEngineStatus() {
    return engineStatus;
  }

  public void setEngineStatus(String engineStatus) {
    this.engineStatus = engineStatus;
  }

  public String getExpertName() {
    return expertName;
  }

  public void setExpertName(String expertName) {
    this.expertName = expertName;
  }

  public String getExpertImage() {
    return expertImage;
  }

  public void setExpertImage(String expertImage) {
    this.expertImage = expertImage;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isExchange() {
    return exchange;
  }

  public void setExchange(boolean exchange) {
    this.exchange = exchange;
  }
}

