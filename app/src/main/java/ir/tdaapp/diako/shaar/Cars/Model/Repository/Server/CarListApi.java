package ir.tdaapp.diako.shaar.Cars.Model.Repository.Server;

import android.util.Log;

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
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObject_And_GetJsonArrayVolley;

public class CarListApi extends CarBaseApi {

  GetJsonArrayVolley getChipsVolley;
  PostJsonObject_And_GetJsonArrayVolley getCars;

  public Single<List<CarListModel>> getCars(JSONObject carObject) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getCars = new PostJsonObject_And_GetJsonArrayVolley(API_URL + "Car/PostCarLists", carObject, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

              List<CarListModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();
              for (int i = 0; i < array.length(); i++) {
                try {

                  JSONObject object = array.getJSONObject(i);
                  CarListModel model = new CarListModel();

                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));
                  //model.setBrand(object.getString("Brand"));
                  model.setMileage(object.getString("Function"));
                  model.setGearbox(object.getString("Gearbox"));
                  model.setPrice(object.getString("Price"));
                  model.setProductionYear(object.getString("ShamsiDate"));
                  model.setImageUrl(object.getString("ImageUrl"));

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
      }).start();
    });
  }

  public Single<List<CarChipsListModel>> getChips() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getChipsVolley = new GetJsonArrayVolley(API_URL + "Car/GetCarCategories", resault -> {
            if (resault.getResault() == ResaultCode.Success) {
              List<CarChipsListModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();

              for (int i = 0; i < array.length(); i++) {
                try {
                  JSONObject object = array.getJSONObject(i);
                  CarChipsListModel model = new CarChipsListModel();

                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title").replace("\n", "").replace("\r", ""));

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
      }).start();
    });
  }

}
