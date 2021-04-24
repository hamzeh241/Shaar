package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.UserItemsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.UserItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public class UserItemsFragmentPresenter {


  Context context;
  UserItemsFragmentService service;
  UserItemsApi api;
  Disposable getItemsDisposable, setItemsDisposable;

  public UserItemsFragmentPresenter(Context context, UserItemsFragmentService service) {
    this.context = context;
    this.service = service;
    api = new UserItemsApi();
  }

  public void start(int userId) {

    getItems(userId);
  }

  private void getItems(int userId) {
    service.loadingState(true);
    Single<List<CategoryDetailsModel>> data = api.getItems(userId);

    getItemsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsModel>>() {
      @Override
      public void onSuccess(List<CategoryDetailsModel> userItems) {
        setItems(userItems);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void setItems(List<CategoryDetailsModel> categoryModels) {
    Observable<CategoryDetailsModel> data = Observable.fromIterable(categoryModels);
    setItemsDisposable = data.subscribe(item -> {
      service.onItemReceived(item);
    }, throwable -> {

    }, () -> {
      service.loadingState(false);
      service.onFinished();
    });
  }
}
