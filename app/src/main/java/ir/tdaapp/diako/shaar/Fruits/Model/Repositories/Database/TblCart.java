package ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database;

import android.content.Context;
import android.database.Cursor;

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
                result.add(q.getInt(q.getColumnIndex("product_id")));
            } catch (Exception e) {
            }
            q.moveToNext();
        }
        q.close();

        return result;
    }

    public void addItem(FruitModel model) {
        try {
            int id = Common.getId(db, "TblBasket");
            db.executeQuery("insert into TblBasket (id,product_id,count) values (" + id + "," + model.getId() + "," + model.getCount() + ")");
        } catch (Exception e) {
        }
    }

    public void deleteItem(FruitModel model) {
        try {
            db.executeQuery("delete from TblBasket where product_id=" + model.getId());
        } catch (Exception e) {
        }
    }
}
