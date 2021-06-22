package ir.tdaapp.diako.shaar.Cars.Model.Repository.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;

public class TblAddCar {

    Context context;
    DBAdapter db;

    public TblAddCar(Context context) {
        this.context = context;
        db = ((CarActivity) context).getAdapter();
    }

    public List<AddItemEntryModel> getBrands() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" select * from TblBrand where ParentId IS NULL");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getModels(int parentId) {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" select * from TblBrand where ParentId=" + parentId);

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getEngineStatus() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblEngineStatus");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getChassisStatus() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblChassisCondition");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getBodyConditions() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblBodyCondition");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getInsuranceDeadlines() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblInsuranceDeadline");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getGearboxes() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblGearbox");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getDocuments() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblDocument");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getSellType() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblHowToSell");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getConstructionYears() {
        List<AddItemEntryModel> results = new ArrayList<>();

        Cursor q = db.ExecuteQ(" Select * from TblYearOfConstruction");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                results.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("TitleShamsi")), q.getString(q.getColumnIndex("TitleMiladi"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return results;
    }

    public List<AddItemEntryModel> getColorCar() {
        List<AddItemEntryModel> result = new ArrayList<>();

        Cursor q = db.ExecuteQ("Select * from TblColorCar");

        for (int i = 0; i < q.getCount(); i++) {

            try {
                result.add(new AddItemEntryModel(q.getInt(q.getColumnIndex("Id")),
                        q.getString(q.getColumnIndex("Title"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            q.moveToNext();
        }
        q.close();
        return result;
    }

}
