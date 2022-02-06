package ir.tdaapp.diako.shaar.Fruits.Model.Services;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface FruitsListService {

    void onPresenterStart();

    void onLoading(boolean state);

    void onSuggestedFruitReceived(FruitModel model);

    void onNewFruitReceived(FruitModel model);

    void onMostSoldFruitReceived(FruitModel model);

    void onCategoriesReceived(CarChipsListModel model);

    void onError(ResaultCode code);

    void onFinish();

}
