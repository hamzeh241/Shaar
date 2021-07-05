package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import org.json.JSONArray;

import java.util.ArrayList;

import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.FileManger;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultRatingViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CategoryItemDetailsFragmentService {

  void onPresenterStart();

  void loadingState(boolean state);

  void onDetailsReceived(CategoryItemDetailsViewModel model);

  void onStoragePermissionGranted();

  void onStoragePermissionDenied();

  void onImagesUploaded(ArrayList<FileManger.FileResponse> images, JSONArray array);

  void onImagesUploading(boolean state);

  void onUserPhotosUploaded(CategoryResultRatingViewModel model);

  void onCommentLiked(int position, CategoryItemDetailsCommentsModel model, boolean liked);

  void onError(ResaultCode resaultCode);

  void onFinish();
}
