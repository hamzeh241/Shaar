package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;

public interface CategoryDetailsFragmentService {

  void onPresenterStart();

  void onItemsReceived(CategoryDetailsModel model);

  void onChipsReceived(CategoryDetailsChipModel model);

  void loadingState(boolean load);

  void onLoadingPaging(boolean load);

  void onFinish();

  void onError(String result);
}
