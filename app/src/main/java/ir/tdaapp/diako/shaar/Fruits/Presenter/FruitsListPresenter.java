package ir.tdaapp.diako.shaar.Fruits.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.SearchModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitsListRepositoryFruits;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.FruitsListService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ListModel;

public class FruitsListPresenter {

  Context context;
  FruitsListService service;
  FruitsListRepositoryFruits repository;
  Disposable suggested, categories, setSuggested, newFruits, setNewFruits, setCategories, setMostSold;

  public FruitsListPresenter(Context context, FruitsListService service) {
    this.context = context;
    this.service = service;
    repository = new FruitsListRepositoryFruits();
  }

  public void start() {
    service.onPresenterStart();
    getCategories();
  }

  public void getSpecificFruit(@NonNull SearchModel model) {
    service.onPresenterStart();
    getEveryFruit(createObject(model.getText() == null ? "" : model.getText(), model.getCategoryId(), model.getPage()));
  }

  private void getCategories() {
    service.onLoading(true);
    Single<List<CarChipsListModel>> data = repository.getCategories();

    categories = data.subscribeWith(new DisposableSingleObserver<List<CarChipsListModel>>() {
      @Override
      public void onSuccess(@NonNull List<CarChipsListModel> categoryDetailsChipModels) {
        service.onLoading(false);
        setCategories(categoryDetailsChipModels);
      }

      @Override
      public void onError(@NonNull Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void getEveryFruit(JSONObject object) {
    service.onLoading(true);
    Single<ListModel> data = repository.getSuggested(object);

    suggested = data.subscribeWith(new DisposableSingleObserver<ListModel>() {
      @Override
      public void onSuccess(ListModel listModels) {
        service.onLoading(false);
        setAll(listModels.getAllFruits());
        setNewFruits(listModels.getJustArrivedFruits());
        setMostSold(listModels.getMostSoldFruits());
      }

      @Override
      public void onError(Throwable e) {
        service.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void setAll(List<FruitModel> models) {
    Observable<FruitModel> data = Observable.fromIterable(models);

    setSuggested = data.subscribe(model -> {
      service.onSuggestedFruitReceived(model);
    }, throwable -> {

    }, () -> {
      service.onFinish();
    });
  }

  private void setNewFruits(List<FruitModel> models) {
    Observable<FruitModel> data = Observable.fromIterable(models);

    setNewFruits = data.subscribe(model -> {
      service.onNewFruitReceived(model);
    }, throwable -> {

    }, () -> {
      service.onFinish();
    });
  }

  private void setMostSold(List<FruitModel> models) {
    Observable<FruitModel> data = Observable.fromIterable(models);

    setMostSold = data.subscribe(model -> {
      service.onMostSoldFruitReceived(model);
    }, throwable -> {

    }, () -> {
      service.onLoading(false);
      service.onFinish();
    });
  }

  private void setCategories(List<CarChipsListModel> models) {
    Observable<CarChipsListModel> data = Observable.fromIterable(models);

    setCategories = data.subscribe(model -> {
      service.onCategoriesReceived(model);
    }, throwable -> {

    }, () -> {
      getEveryFruit(createObject("", 0, 0));
    });
  }

  private JSONObject createObject(String search, int categoryId, int page) {
    JSONObject object = new JSONObject();

    try {
      object.put("TextSearch", search == null ? "" : search);
      object.put("CategoryId", categoryId);
      object.put("Page", page);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    return object;
  }
}
