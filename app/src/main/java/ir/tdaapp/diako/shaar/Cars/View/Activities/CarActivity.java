package ir.tdaapp.diako.shaar.Cars.View.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarFavoriteItemsFragment;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.CarListFragment;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.UsersCarsListFragment;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Add_Account;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_SMS_Panel;
import ir.tdaapp.diako.shaar.FragmentPage.Succefull_Register;
import ir.tdaapp.diako.shaar.R;

import android.os.Bundle;

import java.util.List;

public class CarActivity extends AppCompatActivity {

    DBAdapter dbAdapter;

    public void removeStack() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        FragmentManager manager = getSupportFragmentManager();
        for (Fragment i : fragmentList) {
            if (i instanceof Fragment_Login_Home || i instanceof Fragment_Add_Account || i instanceof Fragment_SMS_Panel ||
                    i instanceof Succefull_Register) {
                manager.popBackStack();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        dbAdapter = new DBAdapter(this);

        String intent = getIntent().getStringExtra("FavCar");

        Bundle extras = getIntent().getExtras();
        int fragment = 0;

        if (extras != null) {
            fragment = extras.getInt("FavCar");
        }

        if (fragment == 0) {
            onAddFragment(new CarListFragment(), 0, 0, false, CarListFragment.TAG);
        } else if (fragment == 1) {
            onAddFragment(new CarFavoriteItemsFragment(), 0, 0, false, CarFavoriteItemsFragment.TAG);
        } else if (fragment == 2) {
            onAddFragment(new UsersCarsListFragment(), 0, 0, false, UsersCarsListFragment.TAG);
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
        transaction.add(R.id.carFrameLayout, fragment, fragmentTAG);

        //در اینجا فرگمنت را به پشته اضافه می کند
        if (backStack) {
            transaction.addToBackStack(null);
        }

        //در اینجا فرگمنت نمایش داده می شود
        transaction.commit();
    }


    public DBAdapter getAdapter() {
        return dbAdapter;
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }
}
