package ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;

public class TblCityId {

  private final Context context;

  public static final String SHARED_PREFS_NAME = "SharPrefs";
  public static final String SHARED_PREFS_CITY_ID_KEY = "CityId";

  public TblCityId(Context context) {
    this.context = context;
  }

  public void add(int cityId) {
    SharedPreferences.Editor preferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE).edit();
    preferences.putInt(SHARED_PREFS_CITY_ID_KEY,cityId);
    preferences.apply();
  }

  public int getCityId() {
    SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    return preferences.getInt(SHARED_PREFS_CITY_ID_KEY,0);
  }
}
