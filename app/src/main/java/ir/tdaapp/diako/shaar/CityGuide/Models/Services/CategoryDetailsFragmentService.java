package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface CategoryDetailsFragmentService {

  void onPresenterStart();

  void onPresenterRestart();

  void onItemsReceived(CategoryDetailsModel model);

  void onChipsReceived(CategoryDetailsChipModel model);

  void loadingState(boolean load);

  void onPageFinished(List<CategoryDetailsModel> categoryDetailsModels);

  void onFinish();

  void onError(String result);
}
