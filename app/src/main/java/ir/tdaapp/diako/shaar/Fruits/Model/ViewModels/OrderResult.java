package ir.tdaapp.diako.shaar.Fruits.Model.ViewModels;

public class OrderResult {

  int id;
  double totalPrice;
  String statusPayment, statusBasket;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(double totalPrice) {
    this.totalPrice = totalPrice;
  }

  public String getStatusPayment() {
    return statusPayment;
  }

  public void setStatusPayment(String statusPayment) {
    this.statusPayment = statusPayment;
  }

  public String getStatusBasket() {
    return statusBasket;
  }

  public void setStatusBasket(String statusBasket) {
    this.statusBasket = statusBasket;
  }
}
