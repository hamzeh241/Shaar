package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryDetailsApiCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;

public class CategoryDetailsFragmentPresenter {

    Context context;
    CategoryDetailsFragmentService categoryDetailsFragmentService;
    CategoryDetailsApiCityGuide categoryDetailsApi;
    Disposable getChipsDisposable, setChipsDisposable;

    public CategoryDetailsFragmentPresenter(Context context, CategoryDetailsFragmentService categoryDetailsFragmentService) {
        this.context = context;
        this.categoryDetailsFragmentService = categoryDetailsFragmentService;
        categoryDetailsApi = new CategoryDetailsApiCityGuide();
    }

    public void start(int categoryId) {
        categoryDetailsFragmentService.onPresenterStart();
        getChips(categoryId);
        getItems("", categoryId, 0);
    }

    public void start(String search, int filterId, int page) {
        categoryDetailsFragmentService.onPresenterRestart();
        getItems(search, filterId, page);
    }

    public void getItemByFilter(String search, int filterId, int page) {
        getItems(search, filterId, page);
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
                categoryDetailsFragmentService.onError(Error.getErrorVolley(e.toString()));
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

    private void getItems(String search, int filterId, int page) {
        categoryDetailsFragmentService.loadingState(true);
        Log.i("TAG", "getItems: " + search + " - " + filterId + " - " + page);
        Single<List<CategoryDetailsModel>> data = categoryDetailsApi.getItems(search, filterId, page);

        getChipsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsModel>>() {
            @Override
            public void onSuccess(List<CategoryDetailsModel> categoryModels) {
                setItems(categoryModels);
            }

            @Override
            public void onError(Throwable e) {
                categoryDetailsFragmentService.onError(Error.getErrorVolley(e.toString()));
            }
        });
    }

    private void setItems(List<CategoryDetailsModel> categoryModels) {
        Observable<CategoryDetailsModel> data = Observable.fromIterable(categoryModels);
        setChipsDisposable = data.subscribe(item -> {
            categoryDetailsFragmentService.loadingState(false);
            categoryDetailsFragmentService.onItemsReceived(item);
        }, throwable -> {

        }, () -> {
            categoryDetailsFragmentService.onFinish();
            categoryDetailsFragmentService.onPageFinished(categoryModels);
            categoryDetailsFragmentService.loadingState(false);
        });
    }
}
