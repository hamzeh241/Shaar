package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultCommentsViewModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonObject;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class CategoryItemCommentsApi extends BaseApi {

  GetJsonArrayVolley getJsonArrayVolley;
  PostJsonObjectVolley postJsonObjectVolley;

  public Single<List<CategoryItemDetailsCommentsModel>> getComments(int itemId, int userId) {
    return Single.create(emitter -> {
      new Thread(() -> {

        try {

          getJsonArrayVolley = new GetJsonArrayVolley(API_URL + "CityGuide/GetCommentCityGuide?CityGuideId=" + itemId + "&UserId=" + userId, new IGetJsonArray() {
            @Override
            public void Get(ResaultGetJsonArrayVolley resault) {

              if (resault.getResault() == ResaultCode.Success) {
                List<CategoryItemDetailsCommentsModel> models = new ArrayList<>();
                JSONArray array = resault.getJsonArray();

                for (int i = 0; i < array.length(); i++) {
                  CategoryItemDetailsCommentsModel model = new CategoryItemDetailsCommentsModel();

                  try {
                    JSONObject object = array.getJSONObject(i);
                    model.setId(object.getInt("Id"));
                    model.setMessage(object.getString("Text"));
                    model.setDate(object.getString("Date"));
                    model.setPhoneNumber(object.getString("CellPhone"));
                    model.setUserAvatar(object.getString("Avatar"));
                    model.setLikes(object.getInt("Like"));
                    model.setDislikes(object.getInt("DisLike"));
                    model.setLiked(object.getBoolean("UserLiked"));
                    model.setDisliked(object.getBoolean("UserDisLike"));

                    models.add(model);
                  } catch (JSONException e) {
                    emitter.onError(e);
                  }
                }
                emitter.onSuccess(models);

              } else {
                emitter.onError(new IOException(resault.getResault().toString()));
              }
            }
          });

        } catch (Exception e) {
          emitter.onError(e);
        }
      }).start();
    });
  }

  public Single<CategoryResultCommentsViewModel> sendComment(JSONObject object) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          postJsonObjectVolley = new PostJsonObjectVolley(API_URL + "CityGuide/AddCommentCityGuide",
            object, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              JSONObject jsonObject = resault.getObject();
              CategoryResultCommentsViewModel result = new CategoryResultCommentsViewModel();

              try {
                result.setStatus(jsonObject.getBoolean("Status"));
                result.setCode(jsonObject.getInt("Code"));
                result.setTitle(jsonObject.getString("Titel"));
              } catch (JSONException e) {
                e.printStackTrace();
              }
              emitter.onSuccess(result);
            }else {
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
