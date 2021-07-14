package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;
import android.database.Cursor;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;

/**
 * Created by Diako on 7/6/2019.
 */

public class User {


    DBAdapter dbAdapter;

    public User(Context context) {
        dbAdapter = new DBAdapter(context);
    }

    public void addUser(UserModel model){
        Cursor cursor = dbAdapter.executeQuery("select * from TblUser");
        if (cursor != null && cursor.moveToFirst()) {
            int a = cursor.getCount();
            if (a > 0) {
                dbAdapter.executeQuery("delete from TblUser");
                dbAdapter.close();
            }
        }
        cursor.close();
        String addUser = "insert into TblUser ('Id','FullName','Email','CellPhone') values ('" + model.getId() +
                "','" + model.getFullName() +
                "','" + model.getEmail() +
                "','" + model.getCellPhone() + "')";
        dbAdapter.executeQuery(addUser);
        dbAdapter.close();
    }

    //در این تابع Id کاربر را به دست می آوریم
    public int getUserId() {
        Cursor cursor = dbAdapter.executeQuery("select * from TblUser");
        int Id = 0;
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Id = Integer.parseInt(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbAdapter.close();
        return Id;
    }

    public UserModel getUserInfo() {
        UserModel model = new UserModel();
        Cursor cursor = dbAdapter.executeQuery("select * from TblUser");

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                model.setId(Integer.parseInt(cursor.getString(0)));
                model.setFullName(cursor.getString(1));
                model.setEmail(cursor.getString(2));
                model.setCellPhone(cursor.getString(3));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbAdapter.close();
        return model;
    }

    public class UserModel {
        private String cellPhone, fullName, email;
        private int id;

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
