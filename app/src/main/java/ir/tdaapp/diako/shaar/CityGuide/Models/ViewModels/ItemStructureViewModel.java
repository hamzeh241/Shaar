package ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels;

import java.util.ArrayList;

public class ItemStructureViewModel {

  String title, desciption, instagramId, telegramId, address, cellPhone, tel1, tel2;
  int filterId, userId, cityId;

  ArrayList<String> images;

  public ItemStructureViewModel() {
  }

  public ItemStructureViewModel(String title, String desciption, String instagramId, String telegramId, String address, String cellPhone, String tel1, String tel2, int filterId, int userId, ArrayList<String> images) {
    this.title = title;
    this.desciption = desciption;
    this.instagramId = instagramId;
    this.telegramId = telegramId;
    this.address = address;
    this.cellPhone = cellPhone;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.filterId = filterId;
    this.userId = userId;
    this.images = images;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesciption() {
    return desciption;
  }

  public void setDesciption(String desciption) {
    this.desciption = desciption;
  }

  public String getInstagramId() {
    return instagramId;
  }

  public void setInstagramId(String instagramId) {
    this.instagramId = instagramId;
  }

  public String getTelegramId() {
    return telegramId;
  }

  public void setTelegramId(String telegramId) {
    this.telegramId = telegramId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCellPhone() {
    return cellPhone;
  }

  public void setCellPhone(String cellPhone) {
    this.cellPhone = cellPhone;
  }

  public String getTel1() {
    return tel1;
  }

  public void setTel1(String tel1) {
    this.tel1 = tel1;
  }

  public String getTel2() {
    return tel2;
  }

  public void setTel2(String tel2) {
    this.tel2 = tel2;
  }

  public int getFilterId() {
    return filterId;
  }

  public void setFilterId(int filterId) {
    this.filterId = filterId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public ArrayList<String> getImages() {
    return images;
  }

  public void setImages(ArrayList<String> images) {
    this.images = images;
  }

  public int getCityId() {
    return cityId;
  }

  public void setCityId(int cityId) {
    this.cityId = cityId;
  }
}
