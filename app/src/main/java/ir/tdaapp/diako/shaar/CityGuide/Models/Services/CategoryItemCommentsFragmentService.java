package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CategoryItemCommentsFragmentService {

  void onPresenterStart();

  void onCommentsReceived(CategoryItemDetailsCommentsModel model);

  void onLoading(boolean state);

  void onCommentPostFinished(ResultViewModel model);

  void onCommentPostLoading(boolean state);

  void onLikeState(int position, CategoryItemDetailsCommentsModel model, boolean liked);

  void onError(ResaultCode resaultCode);

  void onFinish();
}
