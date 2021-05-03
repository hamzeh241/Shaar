package ir.tdaapp.diako.shaar.Cars.Model.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailsPhotoModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonObject;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonObjectVolley;

public class CarDetailsApi extends CarBaseApi {

  GetJsonObjectVolley volley;

  public Single<CarDetailModel> getDetails(int id) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          volley = new GetJsonObjectVolley(API_URL + "Car/GetDetailsCar?Id=" + id, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
              JSONObject object = resault.getObject();
              CarDetailModel model = new CarDetailModel();

              try {

                JSONArray array = object.getJSONArray("ImgesCar");
                ArrayList<CarDetailsPhotoModel> photoModels = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                  CarDetailsPhotoModel photoModel = new CarDetailsPhotoModel();
                  photoModel.setImageName(array.getString(i));

                  photoModels.add(photoModel);
                }

                model.setName(object.getString("Title"));
                model.setAddress(object.getString("Address"));
                model.setBrand(object.getString("Brand"));
                model.setCarBodyStatus(object.getString("BodyCondition"));
                model.setChassisStatus(object.getString("ChassisCondition"));
                model.setDescription(object.getString("Description"));
                model.setDocument(object.getString("Document"));
                model.setEngineStatus(object.getString("EngineStatus"));
                model.setExchange(object.getBoolean("Exchange"));
                model.setExpertName(object.getString("UserPro"));
                model.setExpertImage(object.getString("UserProImage"));
                model.setGearBox(object.getString("Gearbox"));
                model.setId(object.getInt("Id"));
                model.setInsuranceTime(object.getString("InsuranceDeadline"));
                model.setMileage("" + object.getInt("Function"));
                model.setPhone(object.getString("Phone"));
                model.setPrice(object.getString("Price"));
                model.setProductionYear(object.getString("YearOfConstruction"));
                model.setSalesType(object.getString("HowToSell"));
                model.setPhotos(photoModels);
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
