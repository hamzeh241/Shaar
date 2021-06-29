package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.SearchResultApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnSearchResultService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;

public class SearchResultFragmentPresenter {
    Context context;
    OnSearchResultService service;
    SearchResultApi api;
    Disposable setItemDisposable,getItemDisposable;

    public SearchResultFragmentPresenter(Context context, OnSearchResultService service) {
        this.context = context;
        this.service = service;
        api = new SearchResultApi();
    }

    public void start(){
        service.onPresenterRestart();
        getItems("",0);

    }

    public void start(String title, int page) {

        service.onPresenterRestart();
        getItems(title, page);


    }

    public void getItemByPage(String title , int page){
        getItems(title,page);
    }

    private void getItems(String title, int page) {
        service.onLoading(true);
        Single<List<CategoryDetailsModel>> data = api.getItems(title, page);

        getItemDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryDetailsModel>>() {
            @Override
            public void onSuccess(List<CategoryDetailsModel> categoryModels) {
                setItems(categoryModels);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });
    }

    private void setItems(List<CategoryDetailsModel> categoryModels) {
        Observable<CategoryDetailsModel> data = Observable.fromIterable(categoryModels);
        setItemDisposable = data.subscribe(categoryDetailsModel -> {
            service.onLoading(false);
            service.onResultRecived(categoryDetailsModel);
        }, throwable -> {

        }, () -> {
            service.onfinish();
            service.onPageFinished(categoryModels);
            service.onLoading(false);
        });
    }
}
