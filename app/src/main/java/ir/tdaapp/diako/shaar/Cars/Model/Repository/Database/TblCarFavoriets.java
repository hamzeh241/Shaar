package ir.tdaapp.diako.shaar.Cars.Model.Repository.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database.Common;

public class TblCarFavoriets {

    Context context;
    DBAdapter db;

    public TblCarFavoriets(Context context) {
        this.context = context;
        db = ((CarActivity) context).getAdapter();
    }



    public List<Integer> getIds() {
        List<Integer> result = new ArrayList<>();

        Cursor q = db.executeQuery("select * from TblCarFavorites");

        for (int i = 0; i < q.getCount(); i++) {
            try {
                result.add(q.getInt(q.getColumnIndex("ItemId")));
            } catch (Exception e) {
            }
            q.moveToNext();
        }
        q.close();

        return result;
    }

    public void addFavorite(int itemId) {
        try {
            int id = Common.getId(db, "TblCarFavorites");
            db.executeQuery("insert into TblCarFavorites (Id,ItemId) values (" + id + "," + itemId + ")");
        } catch (Exception e) {
        }
    }

    public void deleteFavorite(int itemId){
        try {
            db.executeQuery("delete from TblCarFavorites where ItemId="+itemId);
        }catch (Exception e){}
    }

    public boolean checkState(int itemId){
        try {
            Cursor q=db.executeQuery("select count(ItemId) from TblCarFavorites where ItemId="+itemId);
            if (q.getString(0) != null) {
                return q.getInt(0) > 0;
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
}
