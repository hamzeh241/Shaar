package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface OnSearchResultService {

    void onPresenterStart();

    void onPresenterRestart();

    void onResultRecived(CategoryDetailsModel model);

    void onLoading(boolean load);

    void onError(ResaultCode resaultCode);

    void onPageFinished(List<CategoryDetailsModel> models);

    void  onfinish();
}
