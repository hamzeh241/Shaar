package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CityModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class CategoryApiCityGuide extends CityGuideBaseApi {

  GetJsonArrayVolley getCategoriesVolley;
  GetJsonArrayVolley getCitiesVolley;

  public Single<List<CategoryModel>> getCategories(int cityId) {

    return Single.create(emitter -> {

      new Thread(() -> {
        try {

          getCategoriesVolley = new GetJsonArrayVolley(API_URL + "Category/GetCategories?cityId="+cityId, resault -> {

            if (resault.getResault() == ResaultCode.Success) {

              List<CategoryModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();

              for (int i = 0; i < array.length(); i++) {
                CategoryModel model = new CategoryModel();

                try {
                  JSONObject object = array.getJSONObject(i);
                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));
                  model.setImage(object.getString("Image"));

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

  public Single<List<CityModel>> getCities() {
    return Single.create(emitter -> {
      try {

        getCitiesVolley = new GetJsonArrayVolley(API_URL + "CityGuide/GetCities", resault -> {

          if (resault.getResault() == ResaultCode.Success) {

            List<CityModel> models = new ArrayList<>();
            JSONArray array = resault.getJsonArray();

            for (int i = 0; i < array.length(); i++) {
              CityModel model = new CityModel();

              try {
                JSONObject object = array.getJSONObject(i);
                model.setId(object.getInt("Id"));
                model.setName(object.getString("Name"));

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
  }
}
