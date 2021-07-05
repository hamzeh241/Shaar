package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryItemDetailsApiCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.RateDialogService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.FileManger;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultRatingViewModel;
import ir.tdaapp.diako.shaar.ETC.CompressImage;
import ir.tdaapp.diako.shaar.ETC.GetRandom;
import ir.tdaapp.diako.shaar.ETC.SaveImageToMob;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemDetailsFragmentPresenter {

  Context context;
  CategoryItemDetailsFragmentService service;
  RateDialogService rateService;
  CategoryItemDetailsApiCityGuide api;
  Disposable getDetailsDisposable, sendRatingDisposable, sendPhotosDisposable;
  Handler handler;

  public CategoryItemDetailsFragmentPresenter(Context context, CategoryItemDetailsFragmentService service, RateDialogService rateService) {
    this.context = context;
    this.service = service;
    this.rateService = rateService;
    api = new CategoryItemDetailsApiCityGuide();
    handler = new Handler();
  }

  public void start(int userId, int itemId) {
    service.onPresenterStart();
    service.loadingState(true);
    getDetails(userId, itemId);
  }

  public void requestStoragePermission(Activity activity) {
    Dexter.withActivity(activity)
      .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
      .withListener(new MultiplePermissionsListener() {
        @Override
        public void onPermissionsChecked(MultiplePermissionsReport report) {
          if (report.areAllPermissionsGranted())
            service.onStoragePermissionGranted();
          else service.onStoragePermissionDenied();
        }

        @Override
        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

        }
      }).check();
  }

  public void sendUserPhotos(int userId, JSONArray array) {

    Single<CategoryResultRatingViewModel> data = api.sendUserPhotos(userId, array);

    sendPhotosDisposable = data.subscribeWith(new DisposableSingleObserver<CategoryResultRatingViewModel>() {
      @Override
      public void onSuccess(@NonNull CategoryResultRatingViewModel model) {
        service.onUserPhotosUploaded(model);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  public void openImageSelector(FragmentActivity activity, int itemId) {
    List<Uri> selectedUriList = new ArrayList<>();
    ArrayList<FileManger.FileResponse> responses = new ArrayList<>();
    JSONArray array = new JSONArray();
    TedBottomPicker.with(activity)
      .setPeekHeight(activity.getResources().getDisplayMetrics().heightPixels / 2)
      .showTitle(false)
      .setSelectMaxCount(3)
      .setSelectMaxCountErrorText(activity.getString(R.string.maximumPhotoSelectionReached3))
      .setCompleteButtonText(activity.getString(R.string.submit))
      .setEmptySelectionText(activity.getString(R.string.noPhotoSelected))
      .setSelectedUriList(selectedUriList)
      .showMultiImage(uriList -> {
        new Thread(() -> {
//          FileManger fileManger = new FileManger(CityGuideBaseApi.API_URL + "CityGuide/PostUserImageCityGuide");
//          for (Uri uri : uriList) {

//          }
//          service.onImagesUploaded(array);
          service.onImagesUploading(true);

          FileManger fileManger = new FileManger(CityGuideBaseApi.API_URL + "CityGuide/PostUserImageCityGuide");
          CompressImage compressImage = new CompressImage(320, 450, 75, activity);
          for (Uri uri : uriList) {
            String imagePath = uri.getPath();
            Bitmap b = compressImage.Compress(imagePath);
            String Name = GetRandom.GetLong() + ".jpg";
            FileManger.FileResponse response = fileManger.uploadFile(SaveImageToMob.SaveImageToSdCard(Name, b));

            if (response.getCode() == 200) {
              responses.add(response);
              try {
                JSONObject object = new JSONObject();
                object.put("CityGuideId", itemId);
                object.put("ImageUrl", response.getResponse());
                array.put(object);
              } catch (JSONException e) {
                e.printStackTrace();
              }
            } else {
              responses.add(response);
              service.onImagesUploading(false);
            }
          }
          service.onImagesUploading(false);
          handler.post(() -> service.onImagesUploaded(responses, array));
        }).start();
      });
  }

  public void rate(int userId, int itemId, int rate) {
    JSONObject object = createObject(userId, itemId, rate);
    Single<CategoryResultRatingViewModel> data = api.sendRating(object);

    sendRatingDisposable = data.subscribeWith(new DisposableSingleObserver<CategoryResultRatingViewModel>() {
      @Override
      public void onSuccess(@NonNull CategoryResultRatingViewModel categoryResultRatingViewModel) {
        rateService.onRateSendingState(categoryResultRatingViewModel.getStatus(), categoryResultRatingViewModel.getMessage());
      }

      @Override
      public void onError(@NonNull Throwable e) {
        rateService.onRateSendingState(false, "خطا در ارتباط");
      }
    });
  }

  private void getDetails(int userId, int itemId) {
    Single<CategoryItemDetailsViewModel> data = api.getDetails(userId, itemId);
    service.loadingState(true);

    getDetailsDisposable = data.subscribeWith(new DisposableSingleObserver<CategoryItemDetailsViewModel>() {
      @Override
      public void onSuccess(CategoryItemDetailsViewModel models) {
        service.onDetailsReceived(models);
        service.loadingState(false);
        service.onFinish();
      }

      @Override
      public void onError(Throwable e) {
        service.loadingState(false);
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private JSONObject createObject(int userId, int itemId, int rate) {
    JSONObject object = new JSONObject();
    try {
      object.put("UserId", userId);
      object.put("CityguideId", itemId);
      object.put("CountStars", rate);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object;
  }
}
