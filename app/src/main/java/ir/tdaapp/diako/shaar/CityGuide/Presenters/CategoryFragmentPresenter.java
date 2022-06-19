package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryApiCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CityModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;

public class CategoryFragmentPresenter {

  Context context;
  CategoryFragmentService categoryFragmentService;
  CategoryApiCityGuide categoryApi;
  Disposable getCategoryDisposable, setCategoryDisposable, getCityDisposable, setCityDisposable;

  public CategoryFragmentPresenter(Context context, CategoryFragmentService categoryFragmentService) {
    this.context = context;
    this.categoryFragmentService = categoryFragmentService;
    categoryApi = new CategoryApiCityGuide();
  }

  public void start() {
    categoryFragmentService.onPresenterStart();
    getCities();
  }

  private void getCities() {
    Single<List<CityModel>> data = categoryApi.getCities();

    getCityDisposable = data.subscribeWith(new DisposableSingleObserver<List<CityModel>>() {

      @Override
      public void onSuccess(List<CityModel> cityModels) {
        categoryFragmentService.onCitiesReceived(cityModels);
      }

      @Override
      public void onError(Throwable e) {
        categoryFragmentService.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  public void getCategories(int cityId) {

    Single<List<CategoryModel>> data = categoryApi.getCategories(cityId);

    getCategoryDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryModel>>() {
      @Override
      public void onSuccess(List<CategoryModel> categoryModels) {
        setCategories(categoryModels);
      }

      @Override
      public void onError(Throwable e) {
        categoryFragmentService.onError(Error.getErrorVolley(e.toString()));
      }
    });
  }

  private void setCategories(List<CategoryModel> categoryModels) {
    Observable<CategoryModel> data = Observable.fromIterable(categoryModels);
    setCategoryDisposable = data.subscribe(category ->
        categoryFragmentService.onItemsReceived(category),
      throwable -> {

      }, () -> categoryFragmentService.onFinish());

  }
}
