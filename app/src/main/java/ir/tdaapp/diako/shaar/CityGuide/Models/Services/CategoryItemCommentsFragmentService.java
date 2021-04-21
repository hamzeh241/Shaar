package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultCommentsViewModel;

public interface CategoryItemCommentsFragmentService {

  void onPresenterStart();

  void onCommentsReceived(CategoryItemDetailsCommentsModel model);

  void onLoading(boolean state);

  void onCommentPostFinished(CategoryResultCommentsViewModel model);

  void onCommentPostLoading(boolean state);

  void onLikeState(int position, CategoryItemDetailsCommentsModel model, boolean liked);

  void onError(String result);

  void onFinish();
}