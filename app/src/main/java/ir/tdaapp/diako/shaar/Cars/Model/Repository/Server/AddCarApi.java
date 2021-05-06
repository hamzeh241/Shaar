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
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class AddCarApi extends CarBaseApi {

  GetJsonArrayVolley getCategoriesVolley;
  PostJsonObjectVolley volley;

  public Single<ResultViewModel> sendData(JSONObject object) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          volley = new PostJsonObjectVolley(API_URL + "Car/PostCar", object, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              JSONObject jsonObject = resault.getObject();
              ResultViewModel model = new ResultViewModel();
              try {
                model.setTitle(jsonObject.getString("Titel"));
                model.setStatus(jsonObject.getBoolean("Status"));
                model.setCode(jsonObject.getInt("Code"));
              } catch (JSONException e) {
                e.printStackTrace();
              }
              emitter.onSuccess(model);
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

  public Single<List<CarChipsListModel>> getCategories() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getCategoriesVolley = new GetJsonArrayVolley(API_URL + "Car/GetCarCategories", resault -> {
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
