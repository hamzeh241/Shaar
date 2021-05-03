package ir.tdaapp.diako.shaar.Cars.Model.ViewModels;

public class CarDetailModel {
    int id;
    String name, productionYear, mileage, carBodyStatus, brand, chasisStatus, insuranceTime, gearBox, ducument, salesType, price, description;


    public CarDetailModel(int id, String name, String productionYear, String mileage, String carBodyStatus, String brand, String chasisStatus, String insuranceTime, String gearBox, String ducument, String salesType, String price, String description) {
        this.id = id;
        this.name = name;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.carBodyStatus = carBodyStatus;
        this.brand = brand;
        this.chasisStatus = chasisStatus;
        this.insuranceTime = insuranceTime;
        this.gearBox = gearBox;
        this.ducument = ducument;
        this.salesType = salesType;
        this.price = price;
        this.description = description;
    }

    public CarDetailModel() {

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

    public String getChasisStatus() {
        return chasisStatus;
    }

    public void setChasisStatus(String chasisStatus) {
        this.chasisStatus = chasisStatus;
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

    public String getDucument() {
        return ducument;
    }

    public void setDucument(String ducument) {
        this.ducument = ducument;
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
}

