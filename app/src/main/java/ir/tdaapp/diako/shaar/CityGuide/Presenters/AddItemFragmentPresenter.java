package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.AddItemApiCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.AddItemFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.FileManger;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ItemStructureViewModel;
import ir.tdaapp.diako.shaar.ETC.CompressImage;
import ir.tdaapp.diako.shaar.ETC.GetRandom;
import ir.tdaapp.diako.shaar.ETC.SaveImageToMob;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.R;

public class AddItemFragmentPresenter {

  Context context;
  AddItemFragmentService service;
  AddItemApiCityGuide api;
  Disposable sendDetailsDisposable, getFiltersDisposable, setFiltersDisposable, getCategoryDisposable, setCategoryDisposable;


  public AddItemFragmentPresenter(Context context, AddItemFragmentService service) {
    this.context = context;
    this.service = service;
    api = new AddItemApiCityGuide();
  }

  public void start() {
    service.onPresenterStart();
    getCategories();
  }

  public void requestStoragePermission(Activity activity) {
    Dexter.withActivity(activity)
      .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
      .withListener(new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
          if (report.areAllPermissionsGranted()) {
            service.onStoragePermissionGranted();
          } else {
            service.onStoragePermissionDenied();
          }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

        }
      }).check();
  }

  public void openImageSelector(FragmentActivity activity) {
    Handler handler = new Handler();
    List<Uri> selectedUriList = new ArrayList<>();
    ArrayList<FileManger.FileResponse> images = new ArrayList<>();
    TedBottomPicker.with(activity)
      .setPeekHeight(activity.getResources().getDisplayMetrics().heightPixels / 2)
      .showTitle(false)
      .setSelectMaxCount(6)
      .setSelectMaxCountErrorText(activity.getString(R.string.maximumPhotoSelectionReached6))
      .setCompleteButtonText(activity.getString(R.string.submit))
      .setEmptySelectionText(activity.getString(R.string.noPhotoSelected))
      .setSelectedUriList(selectedUriList)
      .showMultiImage(uriList -> {
        new Thread(() -> {
          service.onImageUploading(true);
          FileManger fileManger = new FileManger(CityGuideBaseApi.API_URL + "CityGuide");
          CompressImage compressImage = new CompressImage(320, 450, 75, activity);
          for (Uri uri : uriList) {
            String imagePath = uri.getPath();
            Bitmap b = compressImage.Compress(imagePath);
            String name = GetRandom.GetLong() + ".jpg";
            FileManger.FileResponse response = fileManger.uploadFile(SaveImageToMob.SaveImageToSdCard(name, b));
            images.add(response);
          }
          service.onImageUploading(false);
          handler.post(() -> service.onImagesUploaded(images, uriList));
        }).start();
      });
  }

  public void sendDetails(ItemStructureViewModel model) {
    service.onDataSendingState(true);
    JSONObject object = new JSONObject();
    JSONArray array = new JSONArray();
    ArrayList<String> images = model.getImages();
    try {
      object.put("Title", model.getTitle());
      object.put("CellPhone", model.getCellPhone());
      object.put("Telephone1", model.getTel1());
      object.put("Telephone2", model.getTel2());
      object.put("InstagramId", model.getInstagramId());
      object.put("TelegramId", model.getTelegramId());
      object.put("Address", model.getAddress());
      object.put("Description", model.getDesciption());
      object.put("CityId", model.getCityId());

      for (String image : images) {
        array.put(image);
      }

      object.put("ImagesSlider", array);
      object.put("FilterId", model.getFilterId());
      object.put("UserId", model.getUserId());

    } catch (JSONException e) {
      e.printStackTrace();
    }

    Single<ResultViewModel> data = api.sendData(object);

    sendDetailsDisposable = data.subscribeWith(new DisposableSingleObserver<ResultViewModel>() {

      @Override
      public void onSuccess(@NonNull ResultViewModel model) {
        service.onDataSendingState(false);
        service.onResultReceived(model);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onDataSendingState(false);
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void getCategories() {

    Single<List<CategoryModel>> data = api.getCategories();

    getCategoryDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryModel>>() {
      @Override
      public void onSuccess(List<CategoryModel> categoryModels) {
        service.onCategoryReceived(categoryModels);
        getChipsById(1);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  public void getChipsById(int categoryId) {
    service.onLoading(true);
    Single<List<CategoryDetailsChipModel>> data = api.getFilters(categoryId);

    getFiltersDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsChipModel>>() {
      @Override
      public void onSuccess(List<CategoryDetailsChipModel> categoryModels) {
        service.onLoading(false);
        service.onFilterReceived(categoryModels);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }
}
