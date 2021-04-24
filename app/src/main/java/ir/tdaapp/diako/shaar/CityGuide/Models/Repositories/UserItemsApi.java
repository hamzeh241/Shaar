package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class UserItemsApi {

  GetJsonArrayVolley getItemVolley;

  public Single<List<CategoryDetailsModel>> getItems(int userId) {
    return Single.create(emitter -> {

      new Thread(() -> {
        try {
          getItemVolley = new GetJsonArrayVolley(CityGuideBaseApi.API_URL + "CityGuide/GetCityGuideUser?UserId="+userId, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

              List<CategoryDetailsModel> models = new ArrayList<>();
              JSONArray array = resault.getJsonArray();

              for (int i = 0; i < array.length(); i++) {
                CategoryDetailsModel model = new CategoryDetailsModel();

                try {
                  JSONObject object = array.getJSONObject(i);
                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));
                  model.setAddress(object.getString("Address"));
                  model.setImageUrl(object.getString("ImageCityGuide"));
                  model.setRating(object.getInt("Stars"));
                  model.setRateCount(object.getInt("RateCount"));

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
