package ir.tdaapp.diako.shaar.Fruits.Model.Services;

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface OrdersFragmentService {

  void onPresenterStart();

  void onLoading(boolean state);

  void onOrderReceived(OrderResult result);

  void onError(ResaultCode code);

  void onFinish();
}
