package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;

public interface CarDetailFragmentService {

    void onCarReceived(CarDetailModel model);

    void onPresenterStart();

    void onError();

    void onFinish();

    void loadingState(boolean loading);

}
