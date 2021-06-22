package ir.tdaapp.diako.shaar.Cars.Model.Services;

import java.util.List;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;

public interface CarSearchDialogService {

  void onPresenterStart();

  void onError(String result);

  void onFinish();

  void onProductionYearsReceived(List<AddItemEntryModel> model);

  void onBrandsReceived(List<AddItemEntryModel> model);

  void onGearBoxReceived(List<AddItemEntryModel> model);
}
