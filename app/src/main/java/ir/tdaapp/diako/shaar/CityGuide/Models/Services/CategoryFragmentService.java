package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;

public interface CategoryFragmentService {

  void onPresenterStart();

  void onItemsReceived(CategoryModel model);

  void onFinish();

  void onError(String result);
}
