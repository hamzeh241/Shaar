package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;

public interface UsersCarsFragmentService {

  void onCarReceived(CarListModel model);

  void onPresenterStart();

  void onError(String s);

  void onFinish();

  void loadingState(boolean loading);
}
