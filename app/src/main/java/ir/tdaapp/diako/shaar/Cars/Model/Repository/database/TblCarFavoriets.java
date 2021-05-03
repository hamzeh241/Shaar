package ir.tdaapp.diako.shaar.Cars.Model.Repository.database;

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

        Cursor q = db.ExecuteQ(" SELECT * FROM TblCarFavorites");

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

    public void addToFavorite(int itemId) {
        try {
            int id = Common.getId(db, "TblCarFavorites");
            db.ExecuteQ(" insert into TblCarFavorites (Id,ItemId) value (" + id + " , " + itemId + ")");
        } catch (Exception e) {

        }
    }

    public void deleteFavorite(int itemId) {
        try {
            db.ExecuteQ(" DELETE FROM TblCarFavorites WHERE  ItemId=" + itemId);
        } catch (Exception e) {
        }
    }

    public boolean checkState(int itemId) {

        try {

            Cursor q = db.ExecuteQ(" SELECT cunt(ItemId) FROM TblCarFavorites WHERE ItemId =" + itemId);
            if (q.getString(0) != null) {

                return q.getInt(0) > 0;
            } else {
                return false;
            }


        } catch (Exception e) {
            return false;
        }


    }
}
