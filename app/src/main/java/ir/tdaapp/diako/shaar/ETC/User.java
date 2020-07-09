package ir.tdaapp.diako.shaar.ETC;

import android.content.Context;
import android.database.Cursor;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;

/**
 * Created by Diako on 7/6/2019.
 */

public class User {
    DBAdapter dbAdapter;

    public User(Context context){
        dbAdapter=new DBAdapter(context);
    }

    //در این تابع Id کاربر را به دست می آوریم
    public int GetUserId(){
        Cursor cursor=dbAdapter.ExecuteQ("select * from TblUser");
        int Id=0;
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Id=Integer.parseInt(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbAdapter.close();
        return Id;
    }
}
