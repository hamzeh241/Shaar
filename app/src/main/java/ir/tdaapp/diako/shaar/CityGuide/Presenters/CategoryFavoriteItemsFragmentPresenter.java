package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;

import org.json.JSONArray;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryDetailsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryFavoriteItemsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database.TblFavorites;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFavoriteItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;

public class CategoryFavoriteItemsFragmentPresenter {

  Context context;
  CategoryFavoriteItemsFragmentService service;
  CategoryFavoriteItemsApi api;
  Disposable getFavoritesDisposable, setFavoritesDisposable;
  TblFavorites tblFavorites;


  public CategoryFavoriteItemsFragmentPresenter(Context context, CategoryFavoriteItemsFragmentService service) {
    this.context = context;
    this.service = service;
    api = new CategoryFavoriteItemsApi();
    tblFavorites=new TblFavorites(context);
  }

  public void start() {
    getFavorites(tblFavorites.getIds());
  }


  private void getFavorites(List<Integer>ids) {
    service.loadingState(true);
    Single<List<CategoryDetailsModel>> data = api.sendRequest(ids);

    getFavoritesDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsModel>>() {
      @Override
      public void onSuccess(List<CategoryDetailsModel> categoryModels) {
        setFavorites(categoryModels);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void setFavorites(List<CategoryDetailsModel> categoryModels) {
    Observable<CategoryDetailsModel> data = Observable.fromIterable(categoryModels);
    setFavoritesDisposable = data.subscribe(model -> {
        service.loadingState(false);
        service.onItemReceived(model);
      },
      throwable -> {

      }, () -> {
        service.loadingState(false);
        service.onFinish();
      });
  }
}
