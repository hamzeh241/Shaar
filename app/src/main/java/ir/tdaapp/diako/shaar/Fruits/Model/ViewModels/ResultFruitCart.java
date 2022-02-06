package ir.tdaapp.diako.shaar.Fruits.Model.ViewModels;

import java.util.List;

public class ResultFruitCart {

  double sumPrice;
  List<FruitModel> fruitModels;
  boolean result,totalResult;
  String message;

  public double getSumPrice() {
    return sumPrice;
  }

  public void setSumPrice(double sumPrice) {
    this.sumPrice = sumPrice;
  }

  public List<FruitModel> getFruitModels() {
    return fruitModels;
  }

  public void setFruitModels(List<FruitModel> fruitModels) {
    this.fruitModels = fruitModels;
  }

  public boolean isResult() {
    return result;
  }

  public void setResult(boolean result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isTotalResult() {
    return totalResult;
  }

  public void setTotalResult(boolean totalResult) {
    this.totalResult = totalResult;
  }
}
