package ir.tdaapp.diako.shaar.Cars.Presenter;

import android.content.Context;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Server.CarDetailsApi;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarDetailFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;

public class CarDetailFragmentPresenter {

  Context context;
  CarDetailFragmentService service;
  CarDetailsApi api;
  Disposable getCarDisposable;

  public CarDetailFragmentPresenter(Context context, CarDetailFragmentService service) {
    this.context = context;
    this.service = service;
    api = new CarDetailsApi();
  }

  public void start(int itemId) {
    service.onPresenterStart();
    service.loadingState(true);
    getDetails(itemId);
  }

  private void getDetails(int itemId) {
    Single<CarDetailModel> data = api.getDetails(itemId);
    service.loadingState(true);

    getCarDisposable = data.subscribeWith(new DisposableSingleObserver<CarDetailModel>() {
      @Override
      public void onSuccess(CarDetailModel model) {
        service.onCarReceived(model);
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
}
