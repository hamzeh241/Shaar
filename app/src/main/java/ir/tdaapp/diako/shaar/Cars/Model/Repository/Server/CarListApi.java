package ir.tdaapp.diako.shaar.Cars.Model.Repository.Server;

import java.io.IOException;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class CarListApi extends CarBaseApi {

  GetJsonArrayVolley getListVolley, getChipsVolley;

  public Single<List<CarListModel>> getCars() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getListVolley = new GetJsonArrayVolley(API_URL, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

            } else {
              emitter.onError(new IOException(resault.getResault().toString()));
            }

          });
        } catch (Exception e) {
          emitter.onError(e);
        }
      });
    });
  }

  public Single<List<CarChipsListModel>> getChips() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getChipsVolley = new GetJsonArrayVolley(API_URL, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

            } else {
              emitter.onError(new IOException(resault.getResault().toString()));
            }
          });
        } catch (Exception e) {
          emitter.onError(e);
        }
      });
    });
  }

}
