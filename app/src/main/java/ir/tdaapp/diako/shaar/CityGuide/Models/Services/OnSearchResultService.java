package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface OnSearchResultService {

    void onPresenterStart();

    void onPresenterRestart();

    void onResultRecived(CategoryDetailsModel model);

    void onLoading(boolean load);

    void onError(String error);

    void onPageFinished(List<CategoryDetailsModel> models);

    void  onfinish();
}
