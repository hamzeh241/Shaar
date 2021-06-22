package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import org.json.JSONArray;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;

public interface CategoryItemDetailsFragmentService {

  void onPresenterStart();

  void loadingState(boolean state);

  void onDetailsReceived(CategoryItemDetailsViewModel model);

  void onStoragePermissionGranted();

  void onStoragePermissionDenied();

  void onImagesUploaded(JSONArray array);

  void onCommentLiked(int position, CategoryItemDetailsCommentsModel model, boolean liked);

  void onError(String error);

  void onFinish();
}
