package ir.tdaapp.diako.shaar.Fruits.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitOrdersRepository;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.OrdersFragmentService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;

public class OrdersFragmentPresenter {

  Context context;
  OrdersFragmentService service;
  FruitOrdersRepository repository;
  Disposable get, set;

  public OrdersFragmentPresenter(Context context, OrdersFragmentService service) {
    this.context = context;
    this.service = service;
    repository = new FruitOrdersRepository();
  }

  public void start(int userId) {
    service.onPresenterStart();

    getItems(userId);
  }

  private void getItems(int userId) {
    service.onLoading(true);
    Single<List<OrderResult>> data = repository.getItems(userId);

    get = data.subscribeWith(new DisposableSingleObserver<List<OrderResult>>() {
      @Override
      public void onSuccess(@NonNull List<OrderResult> orderResults) {
        service.onLoading(false);
        setItems(orderResults);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void setItems(List<OrderResult> orderResults) {
    Observable<OrderResult> data = Observable.fromIterable(orderResults);

    set = data.subscribe(result -> {
      service.onOrderReceived(result);
    }, throwable -> {
    }, () -> {
      service.onFinish();
    });
  }
}
