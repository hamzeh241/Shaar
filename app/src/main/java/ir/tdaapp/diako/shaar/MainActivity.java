package ir.tdaapp.diako.shaar;


import android.content.Context;
import android.database.Cursor;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.ContentFrameLayout;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.ETC.SizeScreen;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.FragmentPage.Dialog_Search_By_Id;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_About_Me;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Home;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Splash;

import com.flurry.android.FlurryAgent;
//import com.onesignal.OneSignal;
import co.ronash.pushe.Pushe;


public class MainActivity extends AppCompatActivity {

    SizeScreen sizeScreen;
    public FrameLayout frameLayout1, frameLayout2, frameLayout3,frameLayout4;
    public Dialog_Search_By_Id dialog_search_by_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pushe.initialize(this, true);

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "D67NK6SCYPGC2QH3RN7R");

        FindItem();

        //این پروژه دارای دو content frame layout می باشد که margin width آن برابر با عرض صفحه قرار می گیرد تا مخفی شود و frame layout اولی فقط نمایش داده شود تا در صورت نیاز layout دومی نمایش داده شود

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) frameLayout2.getLayoutParams();
        params.setMargins(0, 0, sizeScreen.GetWidth(), 0);
        frameLayout2.setLayoutParams(params);

        //در اینجا سایز اسکرین در پشته مقدار دهی می شود
        Stack_Back.MyStack_Back.sizeScreen = sizeScreen;

        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Main, new Fragment_Splash()).commit();
    }

    @Override
    public void onBackPressed() {
        Stack_Back.MyStack_Back.Pop(this);
    }

    void FindItem() {
        sizeScreen = new SizeScreen(this);
        frameLayout2 = findViewById(R.id.Fragment_Main2);
        frameLayout1 = findViewById(R.id.Fragment_Main);
        frameLayout3 = findViewById(R.id.Fragment_Main3);
        frameLayout4 = findViewById(R.id.Fragment_Main4);
        dialog_search_by_id=new Dialog_Search_By_Id();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
