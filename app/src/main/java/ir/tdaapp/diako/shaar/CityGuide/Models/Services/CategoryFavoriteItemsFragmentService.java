package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CategoryFavoriteItemsFragmentService {

  void loadingState(boolean state);

  void onItemReceived(CategoryDetailsModel model);

  void onPresenterStart();

  void onError(ResaultCode resaultCode);

  void onFinish();
}
