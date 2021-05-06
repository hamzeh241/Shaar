package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class AddItemApiCityGuide extends CityGuideBaseApi {

  PostJsonObjectVolley volley;
  GetJsonArrayVolley getCategoriesVolley, getFiltersVolley;

  public Single<ResultViewModel> sendData(JSONObject object) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          volley = new PostJsonObjectVolley(API_URL + "CityGuide/PostCityGuide", object, resault -> {

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

  public Single<List<CategoryModel>> getCategories() {

    return Single.create(emitter -> {

      new Thread(() -> {
        try {

          getCategoriesVolley = new GetJsonArrayVolley(API_URL + "Category/GetCategories", resault -> {

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

  public Single<List<CategoryDetailsChipModel>> getFilters(int categoryId) {
    return Single.create(emitter -> {
      new Thread(() -> {

        try {

          getFiltersVolley = new GetJsonArrayVolley(API_URL + "Category/GetFilters?CategoriesId=" + categoryId, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              List<CategoryDetailsChipModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();

              for (int i = 0; i < array.length(); i++) {
                CategoryDetailsChipModel model = new CategoryDetailsChipModel();

                try {
                  JSONObject object = array.getJSONObject(i);
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
      }).start();
    });
  }

}
