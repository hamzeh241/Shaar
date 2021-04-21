package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface CategoryFavoriteItemsFragmentService {

  void loadingState(boolean state);

  void onItemReceived(CategoryDetailsModel model);

  void onPresenterStart();

  void onError(String message);

  void onFinish();
}
