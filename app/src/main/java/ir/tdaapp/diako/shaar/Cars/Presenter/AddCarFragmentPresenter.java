package ir.tdaapp.diako.shaar.Cars.Presenter;

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
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Database.TblAddCar;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Server.AddCarApi;
import ir.tdaapp.diako.shaar.Cars.Model.Services.AddCarFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarStructureViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.ETC.CompressImage;
import ir.tdaapp.diako.shaar.ETC.FileManger;
import ir.tdaapp.diako.shaar.ETC.GetRandom;
import ir.tdaapp.diako.shaar.ETC.SaveImageToMob;
import ir.tdaapp.diako.shaar.ErrorHandeling.Error;
import ir.tdaapp.diako.shaar.R;

public class AddCarFragmentPresenter {

  Context context;
  AddCarFragmentService service;
  AddCarApi api;
  Disposable sendDetailsDisposable, getCategoryDisposable;
  TblAddCar addCar;


  public AddCarFragmentPresenter(Context context, AddCarFragmentService service) {
    this.context = context;
    this.service = service;
    api = new AddCarApi();
    addCar = new TblAddCar(context);
  }

  public void start() {
    service.onPresenterStart();
    service.onLoading(true);
    getCategories();
    getFromDataBase();
  }

  public void getModels(int brandId) {
    service.onModelsReceived(addCar.getModels(brandId));
  }

  public void sendDetails(CarStructureViewModel model) {
    service.onDataSendingState(true);
    JSONObject object = new JSONObject();
    JSONArray array = new JSONArray();
    ArrayList<String> images = model.getImages();
    try {
      object.put("Function", model.getFunction());
      object.put("Price", model.getPrice());
      object.put("BrandId", model.getBrandId());
      object.put("EngineStatusId", model.getEngineStatusId());
      object.put("ChassisConditionId", model.getChassisStatusId());
      object.put("BodyConditionId", model.getBodyStatusId());
      object.put("InsuranceDeadlineId", model.getInsuranceDeadlineId());
      object.put("GearboxId", model.getGearboxId());
      object.put("DocumentId", model.getDocumentId());
      object.put("CategoryId", model.getCategoryId());
      object.put("HowToSellId", model.getSellTypeId());
      object.put("Exchange", model.isExchange());
      object.put("Title", model.getTitle());
      object.put("Description", model.getDescription());
      object.put("Phone", model.getPhone());
      object.put("Address", model.getAddress());
      object.put("YearOfConstructionId", model.getProductionYearId());
      object.put("ColorId",model.getColor());

      for (String image : images) {
        array.put(image);
      }

      object.put("ImageCar", array);
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
    Single<List<CarChipsListModel>> data = api.getCategories();

    getCategoryDisposable = data.subscribeWith(new DisposableSingleObserver<List<CarChipsListModel>>() {
      @Override
      public void onSuccess(List<CarChipsListModel> categoryModels) {
        service.onCategoriesReceived(categoryModels);
        service.onLoading(false);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void getFromDataBase() {
    service.onBrandsReceived(addCar.getBrands());
    service.onBodyStatusReceived(addCar.getBodyConditions());
    service.onChassisStatusReceived(addCar.getChassisStatus());
    service.onProductionYearsReceived(addCar.getConstructionYears());
    service.onDocumentReceived(addCar.getDocuments());
    service.onEngineStatusReceived(addCar.getEngineStatus());
    service.onGearBoxReceived(addCar.getGearboxes());
    service.onInsuranceDeadlineReceived(addCar.getInsuranceDeadlines());
    service.onSellTypeReceived(addCar.getSellType());
    service.onColorReceived(addCar.getColorCar());
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
    ArrayList<String> images = new ArrayList<>();
    TedBottomPicker.with(activity)
      .setPeekHeight(context.getResources().getDisplayMetrics().heightPixels / 2)
      .showTitle(false)
      .setSelectMaxCount(6)
      .setSelectMaxCountErrorText(context.getString(R.string.maximumPhotoSelectionReached3))
      .setCompleteButtonText(activity.getString(R.string.submit))
      .setEmptySelectionText(activity.getString(R.string.noPhotoSelected))
      .setSelectedUriList(selectedUriList)
      .showMultiImage(uriList -> {
        new Thread(() -> {
          service.onImageUploading(true);
          FileManger fileManger = new FileManger(CarBaseApi.API_IMAGE_ADD_CAR);
          CompressImage compressImage = new CompressImage(320, 450, 75, activity);
          for (Uri uri : uriList) {
            String imagePath = uri.getPath();
            Bitmap b = compressImage.Compress(imagePath);
            String name = GetRandom.GetLong() + ".jpg";
            images.add(fileManger.uploadFile(SaveImageToMob.SaveImageToSdCard(name, b)));
          }
          service.onImageUploading(false);
          handler.post(() -> service.onImagesUploaded(images, uriList));
        }).start();
      });
  }
}
