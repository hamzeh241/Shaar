package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObject_And_GetJsonArrayVolley;

public class FruitCartRepository extends FruitsBaseRepository {

    GetJsonArrayVolley volley;

    public Single<List<FruitModel>> getItems(JSONArray array) {
        return Single.create(emitter -> {
            new Thread(() -> {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}
