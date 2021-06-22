package ir.tdaapp.diako.shaar;


import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.AnimRes;
import androidx.annotation.AnimatorRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.tdaapp.diako.shaar.ETC.SizeScreen;
import ir.tdaapp.diako.shaar.FragmentPage.Dialog_Search_By_Id;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Splash;

import com.flurry.android.FlurryAgent;
//import com.onesignal.OneSignal;
import java.util.List;

import co.ronash.pushe.Pushe;


public class MainActivity extends AppCompatActivity {

    boolean singleBack = false;

//   public void removeStack(){
//
//       for (Fragment i : fragmentList){
//           if (i instanceof Fragment_Login_Home || i instanceof Fragment_Add_Account || i instanceof Fragment_SMS_Panel ||
//                   i instanceof Succefull_Register){
//               onBackPressed();
//           }
//       }
//       List<Fragment> a = getSupportFragmentManager().getFragments();
//   }


    SizeScreen sizeScreen;
    public FrameLayout frameLayout1, frameLayout2, frameLayout3, frameLayout4;
    public Dialog_Search_By_Id dialog_search_by_id;
    private static MainActivity instance;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;


        Pushe.initialize(this, true);

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "D67NK6SCYPGC2QH3RN7R");

        FindItem();

        //این پروژه دارای دو content frame layout می باشد که margin width آن برابر با عرض صفحه قرار می گیرد تا مخفی شود و frame layout اولی فقط نمایش داده شود تا در صورت نیاز layout دومی نمایش داده شود

//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frameLayout2.getLayoutParams();
//        params.setMargins(0, 0, sizeScreen.GetWidth(), 0);
//        frameLayout2.setLayoutParams(params);

        //در اینجا سایز اسکرین در پشته مقدار دهی می شود
//        Stack_Back.MyStack_Back.sizeScreen = sizeScreen;

//        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Main, new Fragment_Splash()).commit();
        onAddFragment(new Fragment_Splash(), 0, 0, false, Fragment_Splash.TAG);
    }


    public static MainActivity getInstance() {
        return instance;
    }

    void FindItem() {
        sizeScreen = new SizeScreen(this);
//        frameLayout2 = findViewById(R.id.Fragment_Main2);
        frameLayout1 = findViewById(R.id.Fragment_Main);
//        frameLayout3 = findViewById(R.id.Fragment_Main3);
//        frameLayout4 = findViewById(R.id.Fragment_Main4);
        dialog_search_by_id = new Dialog_Search_By_Id();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
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
        transaction.add(R.id.Fragment_Main, fragment, fragmentTAG);

        //در اینجا فرگمنت را به پشته اضافه می کند
        if (backStack) {
            transaction.addToBackStack(null);
        }

        //در اینجا فرگمنت نمایش داده می شود
        transaction.commit();
    }

    public void HiddenLogin() {
        Fragment fragmentHome = getSupportFragmentManager().findFragmentByTag(Fragment_Home.TAG);
        if (fragmentHome != null) {
            ((Fragment_Home) fragmentHome).HidenItemNavigation();
        }
    }



    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        if (fragmentList instanceof Fragment_Home && singleBack){
            if (singleBack){
                super.onBackPressed();
                return;
            }
            this.singleBack = true;

            Toast.makeText(this, "برای خروج دکمه ی بازگشت را دوباره فشار دهید", Toast.LENGTH_SHORT).show();

        }else {
            super.onBackPressed();
        }



    }
}
