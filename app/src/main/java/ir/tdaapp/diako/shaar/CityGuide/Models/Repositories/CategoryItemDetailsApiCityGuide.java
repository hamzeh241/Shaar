package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import io.reactivex.Single;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultRatingViewModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class CategoryItemDetailsApiCityGuide extends CityGuideBaseApi {

  GetJsonObjectVolley getDetailsObjectVolley;
  PostJsonObjectVolley postJsonObjectVolley;
  PostJsonArrayVolley postJsonArrayVolley;

  public Single<CategoryItemDetailsViewModel> getDetails(int userId, int itemId) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          getDetailsObjectVolley = new GetJsonObjectVolley(
            API_URL + "CityGuide/GetDetailCityGuide?UserId=" + userId + "&CityGuideId=" + itemId,
            resault -> {


              if (resault.getResault() == ResaultCode.Success) {
                JSONObject object = resault.getObject();
                CategoryItemDetailsViewModel model = new CategoryItemDetailsViewModel();
                try {
                  model.setTitle(object.getString("Title"));
                  model.setCategoryTitle(object.getString("CategoryTitle"));
                  model.setAddress(object.getString("Address"));
                  model.setStarCount(object.getInt("CountScores"));
                  model.setRateCount(object.getInt("CountRate"));
                  model.setUserScore(object.getInt("ScoreUser"));
                  model.setCellPhone(object.getString("CellPhone"));
                  model.setTel1(object.getString("Tel1"));
                  model.setTel2(object.getString("Tel2"));
                  model.setInstagramId(object.getString("InstagramId"));
                  model.setTelegramId(object.getString("TelegramId"));
                  model.setDescription(object.getString("Description"));

                  JSONArray photosArray = object.getJSONArray("UsersImage");
                  ArrayList<CategoryItemDetailsPhotoModel> photoModels = new ArrayList<>();
                  for (int i = 0; i < photosArray.length(); i++) {
                    CategoryItemDetailsPhotoModel photoModel = new CategoryItemDetailsPhotoModel();
                    photoModel.setImageName(photosArray.getString(i));

                    photoModels.add(photoModel);
                  }

                  JSONArray sliderArray = object.getJSONArray("SliderImages");
                  ArrayList<CategoryItemDetailsPhotoModel> sliderPhotosModels = new ArrayList<>();
                  for (int i = 0; i < sliderArray.length(); i++) {

                    CategoryItemDetailsPhotoModel slide = new CategoryItemDetailsPhotoModel();
                    slide.setImageName(sliderArray.getString(i));

                    sliderPhotosModels.add(slide);
                  }

                  JSONArray commentsArray = object.getJSONArray("Comments");
                  ArrayList<CategoryItemDetailsCommentsModel> commentsModels = new ArrayList<>();
                  for (int i = 0; i < commentsArray.length(); i++) {
                    JSONObject commentsObject = commentsArray.getJSONObject(i);
                    CategoryItemDetailsCommentsModel commentsModel = new CategoryItemDetailsCommentsModel();

                    commentsModel.setId(commentsObject.getInt("Id"));
                    commentsModel.setUserAvatar(commentsObject.getString("Avatar"));
                    commentsModel.setPhoneNumber(commentsObject.getString("CellPhone"));
                    commentsModel.setDate(commentsObject.getString("Date"));
                    commentsModel.setMessage(commentsObject.getString("Text"));
                    commentsModel.setLikes(commentsObject.getInt("Like"));
                    commentsModel.setDislikes(commentsObject.getInt("DisLike"));
                    commentsModel.setLiked(commentsObject.getBoolean("UserLiked"));
                    commentsModel.setDisliked(commentsObject.getBoolean("UserDisLike"));

                    commentsModels.add(commentsModel);
                  }

                  model.setCommentsModels(commentsModels);
                  model.setPhotoModels(photoModels);
                  model.setSliderModels(sliderPhotosModels);

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

  public Single<CategoryResultRatingViewModel> sendUserPhotos(int userId, JSONArray array) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          postJsonArrayVolley = new PostJsonArrayVolley(CityGuideBaseApi.API_URL +
            "CityGuide/AddUserImageCityGuide?UserId=" + userId, array, resault -> {
            if (resault.getResault() == ResaultCode.Success) {
              CategoryResultRatingViewModel resultModel = new CategoryResultRatingViewModel();
              try {
                JSONObject object = resault.getJsonArray().getJSONObject(0);
                resultModel.setStatus(object.getBoolean("Status"));
                resultModel.setMessage(object.getString("Titel"));
                resultModel.setCode(object.getInt("Code"));
              } catch (JSONException e) {
                e.printStackTrace();
                emitter.onError(e);
              }
              emitter.onSuccess(resultModel);
            } else {
              emitter.onError(new IOException(resault.getMessage()));
            }
          });
        } catch (Exception e) {
          emitter.onError(e);
        }
      }).start();
    });
  }

  public Single<CategoryResultRatingViewModel> sendRating(JSONObject object) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          postJsonObjectVolley = new PostJsonObjectVolley(API_URL + "CityGuide/ScoreCityGuide", object, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              JSONObject jsonObject = resault.getObject();
              CategoryResultRatingViewModel model = new CategoryResultRatingViewModel();

              try {
                model.setMessage(jsonObject.getString("Titel"));
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
}
