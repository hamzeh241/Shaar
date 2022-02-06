package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database.Common;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;

public class TblCart {

  Context context;
  DBAdapter db;

  public TblCart(Context context) {
    this.context = context;
    db = ((FruitsActivity) context).getAdapter();
  }

  public List<Integer> getIds() {
    List<Integer> result = new ArrayList<>();

    Cursor q = db.executeQuery("select * from TblBasket");

    for (int i = 0; i < q.getCount(); i++) {
      try {
        result.add(q.getInt(q.getColumnIndex("id")));
      } catch (Exception e) {
      }
      q.moveToNext();
    }
    q.close();

    return result;
  }

  public List<FruitModel> getItems() {
    List<FruitModel> result = new ArrayList<>();

    Cursor q = db.executeQuery("select * from TblBasket");

    for (int i = 0; i < q.getCount(); i++) {
      FruitModel model = new FruitModel();
      model.setId(q.getInt(q.getColumnIndex("id")));
      model.setName(q.getString(q.getColumnIndex("title")));
      model.setWeight(q.getDouble(q.getColumnIndex("weight")));
      model.setPrice(q.getDouble(q.getColumnIndex("unit_price")));
      model.setImageUrl(q.getString(q.getColumnIndex("image_url")));
      model.setTypeEnum(q.getInt(q.getColumnIndex("type_enum")) == 0 ? false : true);

      if (model.isTypeEnum())
        model.setRange(q.getString(q.getColumnIndex("range")));
      try {
        result.add(model);
      } catch (Exception ignored) {
        ignored.printStackTrace();
      }
      q.moveToNext();
    }
    q.close();

    return result;
  }

  public boolean addItem(FruitModel model) {
    try {
      Cursor q = db.executeQuery("select * from TblBasket where id=" + model.getId());
      if (q.getCount() == 0) {
        db.executeQuery("insert into [TblBasket] (id,unit_price,weight,title,image_url,type_enum,range) values (" + model.getId() + "," + model.getPrice()
          + "," + model.getWeight() + ",'" + model.getName() + "','" + model.getImageUrl() + "'," + (model.isTypeEnum() ? 1 : 0) + ",'" + (model.getRange() == null ? "" : model.getRange()) + "')");
        return true;
      }else return false;
    } catch (Exception e) {
    }

    return false;
  }

  public void updateWeight(FruitModel model) {
    try {
      db.executeQuery("UPDATE TblBasket SET weight = " + model.getWeight() + " WHERE id = " + model.getId() + "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void deleteItem(FruitModel model) {
    try {
      db.executeQuery("delete from TblBasket where id=" + model.getId());
    } catch (Exception e) {
    }
  }
}
