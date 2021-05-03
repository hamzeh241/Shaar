package ir.tdaapp.diako.shaar.Cars.Model.Repository.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonArrayVolley;

public class CarFavoriteitemsApi extends CarBaseApi {

    PostJsonArrayVolley postJsonArrayVolley;


    public Single<List<CarListModel>> sendRequest(List<Integer> ids) {

        return Single.create(emitter -> {
            new Thread(() -> {

                try {
                    JSONArray array = new JSONArray();
                    for (int i : ids) {
                        array.put(i);
                    }
                    postJsonArrayVolley = new PostJsonArrayVolley(API_URL + "Car/GetFavoritesCar", array, resault -> {

                        if (resault.getResault() == ResaultCode.Success) {

                            JSONArray jsonArray = resault.getJsonArray();
                            List<CarListModel> models = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                try {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    CarListModel model = new CarListModel();

                                    model.setId(jsonObject.getInt("Id"));
                                    model.setTitle(jsonObject.getString("Title"));
                                    model.setPrice(jsonObject.getString("Price"));
                                    model.setProductionYear(jsonObject.getString("ShamsiDate"));
                                    model.setGearbox(jsonObject.getString("Gearbox"));
                                    model.setMileage(jsonObject.getString("Function"));
                                    model.setImageUrl(jsonObject.getString("ImageUrl"));
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