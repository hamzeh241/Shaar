package ir.tdaapp.diako.shaar.Fruits.Model.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ResultFruitCart;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface FruitCartService {

    void onPresenterStart();

    void onLoading(boolean state);

    void onIdsReceived(List<Integer> ids);

    void onItemReceived(FruitModel model);

    void onSendResultReceived(ResultFruitCart result);

    void onError(ResaultCode code);

    void onFinish();

}
