package ir.tdaapp.diako.shaar.Cars.Model.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface AddCarFragmentService {

  void onPresenterStart();

  void onError(ResaultCode resaultCode);

  void onFinish();

  void onLoading(boolean state);

  void onCategoriesReceived(List<CarChipsListModel> model);

  void onProductionYearsReceived(List<AddItemEntryModel> model);

  void onBrandsReceived(List<AddItemEntryModel> model);

  void onModelsReceived(List<AddItemEntryModel> model);

  void onEngineStatusReceived(List<AddItemEntryModel> model);

  void onChassisStatusReceived(List<AddItemEntryModel> model);

  void onBodyStatusReceived(List<AddItemEntryModel> model);

  void onInsuranceDeadlineReceived(List<AddItemEntryModel> model);

  void onGearBoxReceived(List<AddItemEntryModel> model);

  void onDocumentReceived(List<AddItemEntryModel> model);

  void onSellTypeReceived(List<AddItemEntryModel> model);

  void onResultReceived(ResultViewModel model);

  void onStoragePermissionGranted();

  void onStoragePermissionDenied();

  void onImagesUploaded(ArrayList<String> arrayList, List<Uri> uris);

  void onImageUploading(boolean loading);

  void onDataSendingState(boolean loading);

  void onColorReceived(List<AddItemEntryModel> models);
}
