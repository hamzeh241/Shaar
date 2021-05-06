package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;

public interface AddItemFragmentService {

  void onPresenterStart();

  void onError(String result);

  void onFinish();

  void onLoading(boolean state);

  void onFilterReceived(List<CategoryDetailsChipModel> model);

  void onCategoryReceived(List<CategoryModel> model);

  void onResultReceived(ResultViewModel model);

  void onStoragePermissionGranted();

  void onStoragePermissionDenied();

  void onImagesUploaded(ArrayList<String> arrayList, List<Uri> uris);

  void onImageUploading(boolean loading);

  void onDataSendingState(boolean loading);
}
