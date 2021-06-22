package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;

public interface CarFavoriteItemfragmentService {

    void loadingState(boolean state);

    void onItemReceived(CarListModel model);

    void onPresenterStart();

    void onError(String message);

    void onFinish();
}


