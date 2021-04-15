package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryDetailsFragment;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class CategoryDetailsApi extends BaseApi {

  GetJsonArrayVolley getChipsVolley, getItemVolley;

  public Single<List<CategoryDetailsChipModel>> getChips(int categoryId) {
    return Single.create(emitter -> {
      new Thread(() -> {

        try {

          getChipsVolley = new GetJsonArrayVolley(apiUrl + "Category/GetFilters?CategoriesId=" + categoryId, resault -> {

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

  public Single<List<CategoryDetailsModel>> getItems(int filterId, int page) {
    return Single.create(emitter -> {

      new Thread(() -> {
        try {
          getItemVolley = new GetJsonArrayVolley(apiUrl + "CityGuide/GetCityGuideLists?FilterId=" + filterId + "&Page=" + page, resault -> {
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
                  model.setImageUrl("ImageCityGuide");
                  model.setRating(object.getInt("Stars"));

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
