package ir.tdaapp.diako.shaar.Cars.Model.Services;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;

public interface CarSearchDialogService {

  void onPresenterStart();

  void onError(String result);

  void onFinish();

  void onProductionYearsReceived(List<AddItemEntryModel> model);

  void onBrandsReceived(List<AddItemEntryModel> model);

  void onGearBoxReceived(List<AddItemEntryModel> model);
}
