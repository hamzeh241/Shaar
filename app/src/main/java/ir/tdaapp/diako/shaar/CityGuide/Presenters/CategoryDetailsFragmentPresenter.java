package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryDetailsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public class CategoryDetailsFragmentPresenter {

  Context context;
  CategoryDetailsFragmentService categoryDetailsFragmentService;
  CategoryDetailsApi categoryDetailsApi;
  Disposable getChipsDisposable, setChipsDisposable;

  public CategoryDetailsFragmentPresenter(Context context, CategoryDetailsFragmentService categoryDetailsFragmentService) {
    this.context = context;
    this.categoryDetailsFragmentService = categoryDetailsFragmentService;
    categoryDetailsApi = new CategoryDetailsApi();
  }

  public void start(int categoryId) {
    categoryDetailsFragmentService.onPresenterStart();
    getChips(categoryId);
    getItems(categoryId, 0);
  }

  public void getItemByFilter(int filterId, int page) {
    getItems(filterId, page);
  }

  private void getChips(int categoryId) {
    categoryDetailsFragmentService.loadingState(true);
    Single<List<CategoryDetailsChipModel>> data = categoryDetailsApi.getChips(categoryId);

    getChipsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsChipModel>>() {
      @Override
      public void onSuccess(List<CategoryDetailsChipModel> categoryModels) {
        setChips(categoryModels);
      }

      @Override
      public void onError(Throwable e) {
        categoryDetailsFragmentService.onError(e.getMessage());
      }
    });
  }

  private void setChips(List<CategoryDetailsChipModel> categoryModels) {
    Observable<CategoryDetailsChipModel> data = Observable.fromIterable(categoryModels);
    setChipsDisposable = data.subscribe(chip ->
        categoryDetailsFragmentService.onChipsReceived(chip),
      throwable -> {

      }, () -> {
        categoryDetailsFragmentService.loadingState(false);
        categoryDetailsFragmentService.onFinish();
      });
  }

  private void getItems(int filterId, int page) {
    categoryDetailsFragmentService.loadingState(true);
    Single<List<CategoryDetailsModel>> data = categoryDetailsApi.getItems(filterId, page);

    getChipsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsModel>>() {
      @Override
      public void onSuccess(List<CategoryDetailsModel> categoryModels) {
        setItems(categoryModels);
      }

      @Override
      public void onError(Throwable e) {
        categoryDetailsFragmentService.onError(e.getMessage());
      }
    });
  }

  private void setItems(List<CategoryDetailsModel> categoryModels) {
    Observable<CategoryDetailsModel> data = Observable.fromIterable(categoryModels);
    setChipsDisposable = data.subscribe(item -> {
      categoryDetailsFragmentService.onItemsReceived(item);
    }, throwable -> {

    }, () -> {
      categoryDetailsFragmentService.onPageFinished(categoryModels);
      categoryDetailsFragmentService.onFinish();
      categoryDetailsFragmentService.loadingState(false);
    });
  }
}
