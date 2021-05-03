package ir.tdaapp.diako.shaar.Cars.View.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.AddCarFragment;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarDeatailFragment;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarFavoriteItemsFragment;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarListFragment;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryFragmentCityGuide;
import ir.tdaapp.diako.shaar.R;

import android.os.Bundle;

public class CarActivity extends AppCompatActivity {

  DBAdapter dbAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_car);

    dbAdapter=new DBAdapter(this);

    onAddFragment(new CarDeatailFragment(), 0,0,false, CarDeatailFragment.TAG);
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
    transaction.add(R.id.carFrameLayout, fragment, fragmentTAG);

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