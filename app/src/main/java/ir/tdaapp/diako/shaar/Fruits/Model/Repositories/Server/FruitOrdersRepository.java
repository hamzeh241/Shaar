package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderDetailsResult;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class FruitOrdersRepository extends FruitsBaseRepository {

  GetJsonArrayVolley getOrdersVolley, getOrderDetailsVolley;

  public Single<List<OrderResult>> getItems(int userId) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          getOrdersVolley = new GetJsonArrayVolley(API_URL + "api/Fruit/GetOrders?userId=" + userId, resault -> {
            if (resault.getResault() == ResaultCode.Success) {
              JSONArray array = resault.getJsonArray();
              List<OrderResult> results = new ArrayList<>();

              try {
                for (int i = 0; i < array.length(); i++) {
                  OrderResult result = new OrderResult();
                  JSONObject object = array.getJSONObject(i);

                  result.setId(object.getInt("Id"));
                  result.setTotalPrice(object.getDouble("Total"));
                  result.setStatusPayment(object.getString("StatusPayment"));
                  result.setStatusBasket(object.getString("StatusBasket"));

                  results.add(result);
                }
              } catch (JSONException e) {
                emitter.onError(e);
              }

              emitter.onSuccess(results);

            } else emitter.onError(new IOException(resault.getMessage()));
          });

        } catch (Exception e) {
          emitter.onError(e);
        }
      }).start();
    });
  }

  public Single<List<OrderDetailsResult>> getOrderDetails(int orderId) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          getOrderDetailsVolley = new GetJsonArrayVolley(API_URL + "api/Fruit/GetOrderDetails?orderId=" + orderId, resault -> {
            if (resault.getResault() == ResaultCode.Success) {
              JSONArray array = resault.getJsonArray();
              List<OrderDetailsResult> results = new ArrayList<>();

              try {
                for (int i = 0; i < array.length(); i++) {
                  OrderDetailsResult result = new OrderDetailsResult();
                  JSONObject object = array.getJSONObject(i);

                  result.setId(object.getInt("Id"));
                  result.setDateCreate(object.getString("DateCreate"));
                  result.setFruitName(object.getString("FruitName"));
                  result.setUnitPrice(object.getDouble("UnitPrice"));
                  result.setWeight(object.getDouble("Weight"));
                  result.setTypeEnum(object.getBoolean("TypeEnum"));
                  result.setRange(object.getString("Range"));
                  result.setImageUrl(object.getString("Image"));

                  results.add(result);
                }
              } catch (JSONException e) {
                emitter.onError(e);
              }

              emitter.onSuccess(results);

            } else emitter.onError(new IOException(resault.getMessage()));
          });

        } catch (Exception e) {
          emitter.onError(e);
        }
      }).start();
    });
  }
}
