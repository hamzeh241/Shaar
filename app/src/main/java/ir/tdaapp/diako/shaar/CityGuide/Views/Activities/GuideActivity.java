package ir.tdaapp.diako.shaar.CityGuide.Views.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryFavoriteItemsFragment;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.CategoryFragment;
import ir.tdaapp.diako.shaar.CityGuide.Views.Fragments.UserItemsFragment;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.R;

import android.os.Bundle;

public class GuideActivity extends AppCompatActivity {

  DBAdapter dbAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guide);

    dbAdapter=new DBAdapter(this);

    onAddFragment(new CategoryFragment(), 0,0,false,CategoryFragment.TAG);
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