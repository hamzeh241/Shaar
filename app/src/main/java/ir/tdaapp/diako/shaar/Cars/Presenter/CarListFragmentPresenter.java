package ir.tdaapp.diako.shaar.Cars.Presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Server.CarListApi;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarListFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryApiCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;

public class CarListFragmentPresenter {

  Context context;
  CarListFragmentService service;
  CarListApi api;
  Disposable getCarChipsDisposable, setCarChipsDisposable, getCarsDisposable, setCarsDisposable;

  public CarListFragmentPresenter(Context context, CarListFragmentService service) {
    this.context = context;
    this.service = service;
    api = new CarListApi();
  }

  public void start() {
    service.onPresenterStart();
    getCarChips();
  }

  private void getCarChips() {
    service.loadingState(true);
    Single<List<CarChipsListModel>> data = api.getChips();

    getCarChipsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CarChipsListModel>>() {
      @Override
      public void onSuccess(List<CarChipsListModel> carChipsModels) {
        setCarChips(carChipsModels);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void setCarChips(List<CarChipsListModel> categoryModels) {
    Observable<CarChipsListModel> data = Observable.fromIterable(categoryModels);
    setCarChipsDisposable = data.subscribe(chips ->
        service.onChipsReceived(chips),
      throwable -> {

      }, () -> {
        getCars();
      });
  }

  private void getCars() {
    Single<List<CarListModel>> data = api.getCars();

    getCarChipsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CarListModel>>() {
      @Override
      public void onSuccess(List<CarListModel> cars) {
        setCars(cars);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void setCars(List<CarListModel> categoryModels) {
    Observable<CarListModel> data = Observable.fromIterable(categoryModels);
    setCarChipsDisposable = data.subscribe(car ->
        service.onCarReceived(car),
      throwable -> {

      }, () -> {
        service.loadingState(false);
      });

  }
}
