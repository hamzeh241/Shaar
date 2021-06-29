package ir.tdaapp.diako.shaar.Cars.Presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Server.CarFavoriteitemsApi;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Database.TblCarFavoriets;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarFavoriteItemfragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;

public class CarFavoriteItemPresenter {

    Context context;
    CarFavoriteItemfragmentService service;
    CarFavoriteitemsApi api;
    TblCarFavoriets tblCarFavoriets;
    Disposable getFavoriteDisposable, setFavoriteDisposable;

    public CarFavoriteItemPresenter(Context context, CarFavoriteItemfragmentService service) {
        this.context = context;
        this.service = service;
        api = new CarFavoriteitemsApi();
        this.tblCarFavoriets = new TblCarFavoriets(context);
    }

    public void start() {
        getFavoritesCar(tblCarFavoriets.getIds());
    }

    private void getFavoritesCar(List<Integer> ids) {
        service.loadingState(true);
        Single<List<CarListModel>> data = api.sendRequest(ids);

        getFavoriteDisposable = data.subscribeWith(new DisposableSingleObserver<List<CarListModel>>() {
            @Override
            public void onSuccess(@NonNull List<CarListModel> carListModels) {
                setFavorites(carListModels);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });

    }

    private void setFavorites(List<CarListModel> carListModels) {

        Observable<CarListModel> data = Observable.fromIterable(carListModels);
        setFavoriteDisposable = data.subscribe(carListModel -> {
            service.loadingState(false);
            service.onItemReceived(carListModel);

        }, throwable -> {

        }, () -> {
            service.loadingState(false);
            service.onFinish();
        });

    }
}
