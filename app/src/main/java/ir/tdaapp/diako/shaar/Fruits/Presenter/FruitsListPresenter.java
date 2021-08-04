package ir.tdaapp.diako.shaar.Fruits.Presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitsListRepositoryFruits;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.FruitsListService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;

public class FruitsListPresenter {

    Context context;
    FruitsListService service;
    FruitsListRepositoryFruits repository;
    Disposable suggested, setSuggested, newFruits, setNewFruits, mostSold, setMostSold;

    public FruitsListPresenter(Context context, FruitsListService service) {
        this.context = context;
        this.service = service;
        repository = new FruitsListRepositoryFruits();
    }

    public void start() {
        service.onPresenterStart();
        getSuggested();
    }

    private void getSuggested() {
        service.onLoading(true);
        Single<List<FruitModel>> data = repository.getSuggested();

        suggested = data.subscribeWith(new DisposableSingleObserver<List<FruitModel>>() {
            @Override
            public void onSuccess(List<FruitModel> fruitModels) {
                setSuggested(fruitModels);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });
    }

    private void setSuggested(List<FruitModel> models) {
        Observable<FruitModel> data = Observable.fromIterable(models);

        setSuggested = data.subscribe(model -> {
            service.onSuggestedFruitReceived(model);
        }, throwable -> {

        }, () -> getNewFruits());
    }

    private void getNewFruits() {
        Single<List<FruitModel>> data = repository.getNewFruits();

        newFruits = data.subscribeWith(new DisposableSingleObserver<List<FruitModel>>() {
            @Override
            public void onSuccess(List<FruitModel> fruitModels) {
                setNewFruits(fruitModels);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });
    }

    private void setNewFruits(List<FruitModel> models) {
        Observable<FruitModel> data = Observable.fromIterable(models);

        setNewFruits = data.subscribe(model -> {
            service.onNewFruitReceived(model);
        }, throwable -> {

        }, () -> getMostSold());
    }

    private void getMostSold() {
        Single<List<FruitModel>> data = repository.getMostSold();

        mostSold = data.subscribeWith(new DisposableSingleObserver<List<FruitModel>>() {
            @Override
            public void onSuccess(List<FruitModel> fruitModels) {
                setMostSold(fruitModels);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
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


}
