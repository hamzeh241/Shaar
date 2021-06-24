package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface UsersCarsFragmentService {

    void onCarReceived(CarListModel model);

    void onPresenterStart();

    void onError(ResaultCode resaultCode);

    void onFinish();

    void loadingState(boolean loading);
}
