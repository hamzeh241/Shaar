package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;

public class FruitsListRepositoryFruits extends FruitsBaseRepository {

    GetJsonArrayVolley getSuggested, getMostSold, getNewFruits;

    public Single<List<FruitModel>> getSuggested() {
        return Single.create(emitter -> {
            new Thread(() -> {
                try {
                    getSuggested = new GetJsonArrayVolley("", resault -> {
                        if (resault.getResault() == ResaultCode.Success) {

                            JSONArray array = resault.getJsonArray();
                            List<FruitModel> models = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                try {

                                    JSONObject object = array.getJSONObject(i);
                                    FruitModel model = new FruitModel();
                                    model.setName(object.getString(""));
                                    model.setPrice(object.getString(""));
                                    model.setImageUrl(object.getString(""));
                                    model.setId(object.getInt(""));

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

    public Single<List<FruitModel>> getNewFruits() {
        return Single.create(emitter -> {
            new Thread(() -> {
                try {
                    getNewFruits = new GetJsonArrayVolley("", resault -> {
                        if (resault.getResault() == ResaultCode.Success) {

                            JSONArray array = resault.getJsonArray();
                            List<FruitModel> models = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                try {

                                    JSONObject object = array.getJSONObject(i);
                                    FruitModel model = new FruitModel();
                                    model.setName(object.getString(""));
                                    model.setPrice(object.getString(""));
                                    model.setImageUrl(object.getString(""));
                                    model.setId(object.getInt(""));

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

    public Single<List<FruitModel>> getMostSold() {
        return Single.create(emitter -> {
            new Thread(() -> {
                try {
                    getMostSold = new GetJsonArrayVolley("", resault -> {
                        if (resault.getResault() == ResaultCode.Success) {

                            JSONArray array = resault.getJsonArray();
                            List<FruitModel> models = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                try {

                                    JSONObject object = array.getJSONObject(i);
                                    FruitModel model = new FruitModel();
                                    model.setName(object.getString(""));
                                    model.setPrice(object.getString(""));
                                    model.setImageUrl(object.getString(""));
                                    model.setId(object.getInt(""));

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
