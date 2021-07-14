package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database;

import android.database.Cursor;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;

public class Common {
  //در اینجا نام یک جدول می گیرد و آیدی بعدی آن را پاس می دهد
  public static int getId(DBAdapter db, String tableName) {

    Cursor GetId = db.executeQuery("select MAX(Id) from "+tableName);
    int Id;
    if (GetId.getString(0) != null)
      Id = Integer.parseInt(GetId.getString(0)) + 1;
    else
      Id = 1;

    return Id;
  }

}
