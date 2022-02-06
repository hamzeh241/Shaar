package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.ErrorHandling.Error;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ListModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObject_And_GetJsonArrayVolley;

public class FruitsListRepositoryFruits extends FruitsBaseRepository {

  PostJsonObjectVolley getSuggested;
  GetJsonArrayVolley getCategories;

  public Single<List<CarChipsListModel>> getCategories() {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {

          getCategories = new GetJsonArrayVolley(API_URL + "api/Fruit/GetCategories", resault -> {
            if (resault.getResault() == ResaultCode.Success) {

              JSONArray array = resault.getJsonArray();
              List<CarChipsListModel> models = new ArrayList<>();

              for (int i = 0; i < array.length(); i++) {
                try {
                  JSONObject object = array.getJSONObject(i);

                  CarChipsListModel model = new CarChipsListModel();
                  model.setId(object.getInt("Id"));
                  model.setTitle(object.getString("Title"));

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

  public Single<ListModel> getSuggested(JSONObject argObject) {
    return Single.create(emitter -> {
      new Thread(() -> {
        try {
          getSuggested = new PostJsonObjectVolley(API_URL + "api/Fruit/HomeFruites", argObject, resault -> {
            if (resault.getResault() == ResaultCode.Success) {

              JSONObject object = resault.getObject();
              List<FruitModel> mostSoldFruits = new ArrayList<>();
              List<FruitModel> justArrivedFruits = new ArrayList<>();
              List<FruitModel> allFruits = new ArrayList<>();
              List<CarChipsListModel> categories = new ArrayList<>();
              ListModel listModel = new ListModel();
              try {
                JSONArray categoriesArray = object.getJSONArray("Categories");
                for (int i = 0; i < categoriesArray.length(); i++) {

                  JSONObject mostSoldObject = categoriesArray.getJSONObject(i);
                  CarChipsListModel model = new CarChipsListModel();
                  model.setId(mostSoldObject.getInt("Id"));
                  model.setTitle(mostSoldObject.getString("Title"));

                  categories.add(model);
                }
                JSONArray mostSoldArray = object.getJSONArray("Bestselling");
                for (int i = 0; i < mostSoldArray.length(); i++) {

                  JSONObject mostSoldObject = mostSoldArray.getJSONObject(i);
                  FruitModel model = new FruitModel();
                  model.setName(mostSoldObject.getString("Title"));
                  model.setPrice(mostSoldObject.getDouble("UnitPrice"));
                  model.setImageUrl(mostSoldObject.getString("Image"));
                  model.setId(mostSoldObject.getInt("Id"));
                  model.setTypeEnum(mostSoldObject.getBoolean("TypeEnum"));

                  if (model.isTypeEnum())
                    model.setRange(mostSoldObject.getString("Range"));

                  mostSoldFruits.add(model);
                }

                JSONArray allFruitsArray = object.getJSONArray("All");
                for (int i = 0; i < allFruitsArray.length(); i++) {

                  JSONObject allFruitsObject = allFruitsArray.getJSONObject(i);
                  FruitModel model = new FruitModel();
                  model.setName(allFruitsObject.getString("Title"));
                  model.setPrice(allFruitsObject.getDouble("UnitPrice"));
                  model.setImageUrl(allFruitsObject.getString("Image"));
                  model.setId(allFruitsObject.getInt("Id"));

                  model.setTypeEnum(allFruitsObject.getBoolean("TypeEnum"));

                  if (model.isTypeEnum())
                    model.setRange(allFruitsObject.getString("Range"));

                  allFruits.add(model);
                }

                JSONArray justArrivedFruitsArray = object.getJSONArray("JustArrived");

                for (int i = 0; i < justArrivedFruitsArray.length(); i++) {

                  JSONObject justArrivedFruitsObject = justArrivedFruitsArray.getJSONObject(i);
                  FruitModel model = new FruitModel();
                  model.setName(justArrivedFruitsObject.getString("Title"));
                  model.setPrice(justArrivedFruitsObject.getDouble("UnitPrice"));
                  model.setImageUrl(justArrivedFruitsObject.getString("Image"));
                  model.setId(justArrivedFruitsObject.getInt("Id"));

                  model.setTypeEnum(justArrivedFruitsObject.getBoolean("TypeEnum"));
                  if (model.isTypeEnum())
                    model.setRange(justArrivedFruitsObject.getString("Range"));

                  justArrivedFruits.add(model);
                }

                listModel.setAllFruits(allFruits);
                listModel.setJustArrivedFruits(justArrivedFruits);
                listModel.setMostSoldFruits(mostSoldFruits);
                listModel.setCategories(categories);
              } catch (JSONException e) {
                e.printStackTrace();
              }

              emitter.onSuccess(listModel);
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
