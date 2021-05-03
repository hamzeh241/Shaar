package ir.tdaapp.diako.shaar.Cars.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface CarFavoriteItemfragmentService {

    void loadingState(boolean state);

    void onItemReceived(CarListModel model);

    void onPresenterStart();

    void onError(String message);

    void onFinish();
}


