package ir.tdaapp.diako.shaar.Fruits.Model.Services;

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderDetailsResult;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface OrdersDetailsFragmentService {

  void onPresenterStart();

  void onLoading(boolean state);

  void onOrderDetailsReceived(OrderDetailsResult result);

  void onError(ResaultCode code);

  void onFinish();
}
