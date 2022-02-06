package ir.tdaapp.diako.shaar.Fruits.Model.ViewModels;

import java.util.List;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;

public class ListModel {

  private List<FruitModel> justArrivedFruits;
  private List<FruitModel> mostSoldFruits;
  private List<FruitModel> allFruits;
  private List<CarChipsListModel> categories;

  public ListModel() {
  }

  public List<FruitModel> getJustArrivedFruits() {
    return justArrivedFruits;
  }

  public void setJustArrivedFruits(List<FruitModel> justArrivedFruits) {
    this.justArrivedFruits = justArrivedFruits;
  }

  public List<FruitModel> getMostSoldFruits() {
    return mostSoldFruits;
  }

  public void setMostSoldFruits(List<FruitModel> mostSoldFruits) {
    this.mostSoldFruits = mostSoldFruits;
  }

  public List<FruitModel> getAllFruits() {
    return allFruits;
  }

  public void setAllFruits(List<FruitModel> allFruits) {
    this.allFruits = allFruits;
  }

  public List<CarChipsListModel> getCategories() {
    return categories;
  }

  public void setCategories(List<CarChipsListModel> categories) {
    this.categories = categories;
  }
}
