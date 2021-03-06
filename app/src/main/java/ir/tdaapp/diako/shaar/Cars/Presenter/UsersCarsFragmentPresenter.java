package ir.tdaapp.diako.shaar.Cars.Presenter;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Server.UsersCarsApi;
import ir.tdaapp.diako.shaar.Cars.Model.Services.UsersCarsFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;

public class UsersCarsFragmentPresenter {

    Context context;
    UsersCarsFragmentService service;
    UsersCarsApi api;
    Disposable setItemsDisposable, getItemsDisposable;


    public UsersCarsFragmentPresenter(Context context, UsersCarsFragmentService service) {
        this.context = context;
        this.service = service;
        api = new UsersCarsApi();
    }

    public void start(int userId) {
        service.onPresenterStart();
        getCars(userId);
    }

    private void getCars(int userId) {
        service.loadingState(true);
        Single<List<CarListModel>> data = api.getCars(userId);

        getItemsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CarListModel>>() {
            @Override
            public void onSuccess(List<CarListModel> cars) {
                setCars(cars);
            }

            @Override
            public void onError(Throwable e) {
                service.onError(Error.getErrorVolley(e.toString()));
            }
        });
    }

    private void setCars(List<CarListModel> categoryModels) {
        Observable<CarListModel> data = Observable.fromIterable(categoryModels);
        setItemsDisposable = data.subscribe(car -> {
                    service.loadingState(false);
                    service.onCarReceived(car);
                },
                throwable -> {
                    service.loadingState(false);
                    service.onError(Error.getErrorVolley(throwable.toString()));
                }, () -> {
                    service.loadingState(false);
                    service.onFinish();
                });

    }
}
