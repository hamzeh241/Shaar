package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import es.dmoral.toasty.Toasty;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedRxBottomPicker;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryItemDetailsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.RateDialogService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultRatingViewModel;
import ir.tdaapp.diako.shaar.ETC.FileManger;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonArrayVolley;

public class CategoryItemDetailsFragmentPresenter {

  Context context;
  CategoryItemDetailsFragmentService service;
  RateDialogService rateService;
  CategoryItemDetailsApi api;
  Disposable getDetailsDisposable, sendRatingDisposable;

  public CategoryItemDetailsFragmentPresenter(Context context, CategoryItemDetailsFragmentService service, RateDialogService rateService) {
    this.context = context;
    this.service = service;
    this.rateService = rateService;
    api = new CategoryItemDetailsApi();
  }

  public void start(int userId, int itemId) {
    service.onPresenterStart();
    service.loadingState(true);
    getDetails(userId, itemId);
  }

  public void requestStoragePermission(Activity activity) {
    Dexter.withActivity(activity).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
      @Override
      public void onPermissionGranted(PermissionGrantedResponse response) {
        service.onStoragePermissionGranted();
      }

      @Override
      public void onPermissionDenied(PermissionDeniedResponse response) {
        service.onStoragePermissionDenied();
      }

      @Override
      public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

      }
    }).check();
  }

  public void openImageSelector(FragmentActivity activity, int itemId) {
    List<Uri> selectedUriList = new ArrayList<>();
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
          FileManger fileManger = new FileManger(BaseApi.API_URL + "CityGuide/PostUserImageCityGuide");
          for (Uri uri : uriList) {
            try {
              JSONObject object = new JSONObject();
              object.put("CityGuideId", itemId);
              object.put("ImageUrl", fileManger.uploadFile(uri.getPath()));
              array.put(object);
            } catch (JSONException e) {
              e.printStackTrace();
            }
          }
          service.onImagesUploaded(array);
        }).start();
      });
  }
  public void rate(int userId, int itemId, int rate) {
    JSONObject object = createObject(userId, itemId, rate);
    Single<CategoryResultRatingViewModel> data = api.sendRating(object);

    sendRatingDisposable = data.subscribeWith(new DisposableSingleObserver<CategoryResultRatingViewModel>() {

      @Override
      public void onSuccess(@NonNull CategoryResultRatingViewModel categoryResultRatingViewModel) {
        if (categoryResultRatingViewModel.getStatus()) {
          rateService.onRateSendingState(true, categoryResultRatingViewModel.getMessage());
        } else {
          rateService.onRateSendingState(false, categoryResultRatingViewModel.getMessage());
        }
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
        service.onError(e.getMessage());
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
