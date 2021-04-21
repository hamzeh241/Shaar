package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface UserItemsFragmentService {

  void loadingState(boolean state);

  void onItemReceived(CategoryDetailsModel model);

  void onError(String s);

  void onFinished();
}
