package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ResultFruitCart;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonObject;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObject_And_GetJsonArrayVolley;

public class FruitCartRepository extends FruitsBaseRepository {

  PostJsonObjectVolley volley;

  public Single<ResultFruitCart> getItems(JSONObject object) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          volley = new PostJsonObjectVolley(API_URL + "api/Fruit/BuyFruit", object, resault -> {
            if (resault.getResault() == ResaultCode.Success) {
              ResultFruitCart resultFruitCart = new ResultFruitCart();
              JSONObject resultObject = resault.getObject();
              List<FruitModel> fruitModels = new ArrayList<>();

              try {

                JSONArray fruitArray = resultObject.getJSONArray("Fruits");
                for (int i = 0; i < fruitArray.length(); i++) {
                  JSONObject fruitObject = fruitArray.getJSONObject(i);
                  FruitModel model = new FruitModel();

                  model.setId(fruitObject.getInt("Id"));
                  model.setName(fruitObject.getString("Title"));
                  model.setResult(fruitObject.getBoolean("Result"));
                  model.setPrice(fruitObject.getDouble("UnitPrice"));
                  model.setMessage(fruitObject.getString("Message"));

                  fruitModels.add(model);
                }

                resultFruitCart.setFruitModels(fruitModels);
                resultFruitCart.setResult(resultObject.getBoolean("Result"));
                resultFruitCart.setTotalResult(resultObject.getBoolean("TotalResult"));
                resultFruitCart.setMessage(resultObject.getString("Message"));
                resultFruitCart.setSumPrice(resultObject.getDouble("SumPrice"));

              } catch (JSONException e) {
                emitter.onError(e);
                e.printStackTrace();
              }

              emitter.onSuccess(resultFruitCart);
            } else {
              emitter.onError(new IOException(resault.getMessage()));
            }
          });

        } catch (Exception e) {
          emitter.onError(e);
          e.printStackTrace();
        }
      }).start();
    });
  }
}
