package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonArrayVolley;

public class CategoryFavoriteItemsApi extends BaseApi {

  PostJsonArrayVolley postJsonArrayVolley;

  public Single<List<CategoryDetailsModel>> sendRequest(List<Integer> ids) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          JSONArray array=new JSONArray();
          for (int i:ids){
            array.put(i);
          }

          postJsonArrayVolley = new PostJsonArrayVolley(API_URL + "CityGuide/GetFavoritesCityGuide", array, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              JSONArray jsonArray = resault.getJsonArray();
              List<CategoryDetailsModel> models = new ArrayList<>();
              for (int i = 0; i < jsonArray.length(); i++) {
                try {
                  JSONObject object = jsonArray.getJSONObject(i);
                  CategoryDetailsModel model = new CategoryDetailsModel();

                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));
                  model.setImageUrl(object.getString("ImageCityGuide"));
                  model.setAddress(object.getString("Address"));
                  model.setRating(object.getInt("Stars"));
                  model.setRateCount(object.getInt("RateCount"));

                  models.add(model);
                } catch (JSONException e) {
                  emitter.onError(e);
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
