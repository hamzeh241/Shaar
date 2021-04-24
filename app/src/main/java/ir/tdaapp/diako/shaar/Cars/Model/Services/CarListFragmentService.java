package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;

public interface CarListFragmentService {

  void onCarReceived(CarListModel model);

  void onChipsReceived(CarChipsListModel model);

  void onPresenterStart();

  void onError(String s);

  void onFinish();

  void loadingState(boolean loading);
}
