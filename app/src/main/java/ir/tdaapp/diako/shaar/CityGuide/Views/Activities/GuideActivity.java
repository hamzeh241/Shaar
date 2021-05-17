package ir.tdaapp.diako.shaar.CityGuide.Views.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryFavoriteItemsFragmentCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryFragmentCityGuide;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.UserItemsFragmentCityGuide;
import ir.tdaapp.diako.shaar.R;

import android.content.Intent;
import android.os.Bundle;

public class GuideActivity extends AppCompatActivity {

  DBAdapter dbAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide);

    dbAdapter=new DBAdapter(this);

  String intent = getIntent().getStringExtra("cityGyideFav");
  Bundle extras = getIntent().getExtras();
  int fragment = 0;

  if (extras != null){
    fragment = extras.getInt("cityGyideFav");
  }

  if (fragment == 0){
    onAddFragment(new CategoryFragmentCityGuide(),0,0,false,CategoryFragmentCityGuide.TAG);
  }else if (fragment == 1){
    onAddFragment(new CategoryFavoriteItemsFragmentCityGuide(),0,0,false,CategoryFavoriteItemsFragmentCityGuide.TAG);
  }else if (fragment == 2){
    onAddFragment(new UserItemsFragmentCityGuide(),0,0,false,UserItemsFragmentCityGuide.TAG);
  }

  }


  public void onAddFragment(Fragment fragment,
                            @AnimatorRes @AnimRes int animEnter,
                            @AnimatorRes @AnimRes int animExit,
                            boolean backStack, String fragmentTAG) {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    //اگر ورودی های انیمیشن غیر صفر باشند انیمیشن ها را اضافه می کند
    if (animEnter != 0 && animExit != 0) {
      transaction.setCustomAnimations(animEnter, animExit, animEnter, animExit);
    }

    //در اینجا فرگمنت اضافه می شود
    transaction.add(R.id.guideFrame, fragment, fragmentTAG);

    //در اینجا فرگمنت را به پشته اضافه می کند
    if (backStack) {
      transaction.addToBackStack(null);
    }

    //در اینجا فرگمنت نمایش داده می شود
    transaction.commit();
  }

  public DBAdapter getAdapter(){
    return dbAdapter;
  }
}