package ir.tdaapp.diako.shaar.Fruits.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database.TblCart;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitCartRepository;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.FruitCartService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ResultFruitCart;

public class FruitCartPresenter {

  Context context;
  FruitCartService service;
  FruitCartRepository repository;
  TblCart cart;
  Disposable get, set, sendItems;

  public FruitCartPresenter(Context context, FruitCartService service) {
    this.context = context;
    this.service = service;
    repository = new FruitCartRepository();
    cart = new TblCart(context);
  }

  public void start() {
    service.onPresenterStart();
    setItems(cart.getItems());
  }

  public void changeWeight(FruitModel model) {
    cart.updateWeight(model);
    service.onFinish();
  }

  public void deleteItem(FruitModel model) {
    cart.deleteItem(model);
  }

  public void clearBasket() {
    for (FruitModel model : cart.getItems()) {
      cart.deleteItem(model);
    }
  }

  private void getIds() {
    service.onIdsReceived(cart.getIds());
  }

  private void setItems(List<FruitModel> fruitModels) {
    Observable<FruitModel> data = Observable.fromIterable(fruitModels);

    set = data.subscribe(model -> {
      service.onItemReceived(model);
    }, throwable -> {

    }, () -> {
      service.onFinish();
    });
  }

  public void sendItems(int userId, ArrayList<FruitModel> fruitModels, String address, String phone, String description) {
    service.onLoading(true);

    Single<ResultFruitCart> data = repository.getItems(createObject(userId, fruitModels, address, phone, description));

    sendItems = data.subscribeWith(new DisposableSingleObserver<ResultFruitCart>() {
      @Override
      public void onSuccess(@NonNull ResultFruitCart resultFruitCart) {
        service.onLoading(false);

        service.onSendResultReceived(resultFruitCart);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private JSONObject createObject(int userId, ArrayList<FruitModel> fruitModels, String address, String phone, String description) {
    JSONObject object = new JSONObject();
    JSONArray array = new JSONArray();
    try {
      for (FruitModel model : fruitModels) {
        JSONObject modelObject = new JSONObject();

        modelObject.put("FruitId", model.getId());
        modelObject.put("UnitPrice", model.getPrice());
        modelObject.put("Weight", model.getWeight());

        array.put(modelObject);
      }
      object.put("UserId", userId);
      object.put("Fruits", array);
      object.put("Address", address);
      object.put("PhoneNumber", phone);
      object.put("Description", description);
    } catch (JSONException ignored) {
    }
    return object;
  }
}
