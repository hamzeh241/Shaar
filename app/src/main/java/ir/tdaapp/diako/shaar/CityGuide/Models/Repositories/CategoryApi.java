package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class CategoryApi extends BaseApi {

  GetJsonArrayVolley getCategoriesVolley;

  public Single<List<CategoryModel>> getCategories() {

    return Single.create(emitter -> {

      new Thread(() -> {
        try {

          getCategoriesVolley = new GetJsonArrayVolley(apiUrl + "Category/GetCategories", resault -> {

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
}
