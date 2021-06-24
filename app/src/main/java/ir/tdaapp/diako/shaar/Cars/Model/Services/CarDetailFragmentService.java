package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CarDetailFragmentService {

    void onCarReceived(CarDetailModel model);

    void onPresenterStart();

    void onError(ResaultCode resaultCode);

    void onFinish();

    void loadingState(boolean loading);

}
