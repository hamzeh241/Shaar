package ir.tdaapp.diako.shaar.Fruits.View.Activities;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import java.util.List;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Add_Account;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_SMS_Panel;
import ir.tdaapp.diako.shaar.FragmentPage.Succefull_Register;
import ir.tdaapp.diako.shaar.Fruits.View.Fragments.FruitsListFragment;
import ir.tdaapp.diako.shaar.R;

public class FruitsActivity extends AppCompatActivity {

    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);

        dbAdapter = new DBAdapter(this);

        onAddFragment(new FruitsListFragment(),
                R.anim.fadein, R.anim.fadeout,
                false, FruitsListFragment.TAG);
    }

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
        transaction.add(R.id.fruitsFrame, fragment, fragmentTAG);

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

}