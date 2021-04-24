package ir.tdaapp.diako.shaar.Cars.Model.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class CarListApi extends CarBaseApi {

  GetJsonArrayVolley getListVolley, getChipsVolley;

  public Single<List<CarListModel>> getCars() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getListVolley = new GetJsonArrayVolley(API_URL, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

              List<CarListModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();
              for (int i = 0; i < array.length(); i++) {
                try {

                  JSONObject object = array.getJSONObject(i);
                  CarListModel model = new CarListModel();

                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));
                  model.setBrand(object.getString("Brand"));
                  model.setMileage(object.getString("Mileage"));
                  model.setGearbox(object.getString("Gearbox"));
                  model.setPrice(object.getString("Price"));
                  model.setProductionYear(object.getString("ProductionYear"));

                  models.add(model);
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
              emitter.onSuccess(models);
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
              List<CarChipsListModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();

              for (int i = 0; i < array.length(); i++) {
                try {
                  JSONObject object = array.getJSONObject(i);
                  CarChipsListModel model = new CarChipsListModel();

                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));

                  models.add(model);
                } catch (JSONException e) {
                  e.printStackTrace();
                }
              }
              emitter.onSuccess(models);

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
