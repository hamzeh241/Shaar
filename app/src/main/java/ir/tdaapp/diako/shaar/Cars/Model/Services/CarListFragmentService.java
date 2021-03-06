package ir.tdaapp.diako.shaar.Cars.Model.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CarListFragmentService {

  void onCarReceived(CarListModel model);

  void onCarsReceived(List<CarListModel> model);

  void onChipsReceived(CarChipsListModel model);

  void onPresenterStart();

  void onError(ResaultCode resaultCode);

  void onFinish();

  void loadingState(boolean loading);
}
