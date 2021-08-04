package ir.tdaapp.diako.shaar.Fruits.Presenter;

import android.content.Context;

import org.json.JSONArray;

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

public class FruitCartPresenter {

    Context context;
    FruitCartService service;
    FruitCartRepository repository;
    TblCart cart;
    Disposable get, set;

    public FruitCartPresenter(Context context, FruitCartService service) {
        this.context = context;
        this.service = service;
        repository = new FruitCartRepository();
        cart = new TblCart(context);
    }

    public void start() {
        service.onPresenterStart();
        getIds();
    }

    private void getIds() {
        service.onIdsReceived(cart.getIds());
    }

    public void getItems(List<Integer> ids) {
        JSONArray array = new JSONArray();

        for (Integer i : ids) {
            array.put(i);
        }

        Single<List<FruitModel>> data = repository.getItems(array);

        get = data.subscribeWith(new DisposableSingleObserver<List<FruitModel>>() {
            @Override
            public void onSuccess(List<FruitModel> fruitModels) {
                setItems(fruitModels);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });
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
}
