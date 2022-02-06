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
import ir.tdaapp.diako.shaar.Fruits.Model.Services.OrdersDetailsFragmentService;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.OrdersFragmentService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderDetailsResult;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;

public class OrdersDetailsFragmentPresenter {

  Context context;
  OrdersDetailsFragmentService service;
  FruitOrdersRepository repository;
  Disposable get, set;

  public OrdersDetailsFragmentPresenter(Context context, OrdersDetailsFragmentService service) {
    this.context = context;
    this.service = service;
    repository = new FruitOrdersRepository();
  }

  public void start(int orderId) {
    service.onPresenterStart();

    getItems(orderId);
  }

  private void getItems(int orderId) {
    service.onLoading(true);
    Single<List<OrderDetailsResult>> data = repository.getOrderDetails(orderId);

    get = data.subscribeWith(new DisposableSingleObserver<List<OrderDetailsResult>>() {
      @Override
      public void onSuccess(@NonNull List<OrderDetailsResult> orderResults) {
        service.onLoading(false);
        setItems(orderResults);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void setItems(List<OrderDetailsResult> orderResults) {
    Observable<OrderDetailsResult> data = Observable.fromIterable(orderResults);

    set = data.subscribe(result -> {
      service.onOrderDetailsReceived(result);
    }, throwable -> {
    }, () -> {
      service.onFinish();
    });
  }
}
