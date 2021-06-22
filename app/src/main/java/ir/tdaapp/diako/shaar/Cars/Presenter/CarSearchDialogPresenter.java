package ir.tdaapp.diako.shaar.Cars.Presenter;

import android.content.Context;

import ir.tdaapp.diako.shaar.Cars.Model.Repository.Database.TblAddCar;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarSearchDialogService;

public class CarSearchDialogPresenter {

  Context context;
  CarSearchDialogService service;
  TblAddCar addCar;

  public CarSearchDialogPresenter(Context context, CarSearchDialogService service) {
    this.context = context;
    this.service = service;
    addCar = new TblAddCar(context);
  }

  public void start() {
    service.onPresenterStart();
    getFromDatabase();
  }

  private void getFromDatabase() {
    service.onBrandsReceived(addCar.getBrands());
    service.onProductionYearsReceived(addCar.getConstructionYears());
    service.onGearBoxReceived(addCar.getGearboxes());
  }
}
