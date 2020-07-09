package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.Enum.ForcedUpdate;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.R;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Diako on 7/25/2019.
 */

public class Fragment_Splash extends Fragment implements IBase {

    public static final String TAG="Fragment_Splash";
    ImageView logo_Shaar, shaar, CityInYourHand;
    TextView lbl_Text;
    ProgressBar ProgressBar;
    JsonObjectRequest request;
    ForcedUpdate forcedUpdate = ForcedUpdate.ItIsNotNecessary;
    DBAdapter dbAdapter;
    Internet internet;
    Handler handler_logo_Shaar,handler_Shaar,handler_CityInYourHand,handler_lbl_Text,handler;
    RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_splash, container, false);

        FindItem(view);

        ShowItem();

        return view;
    }


//    @Override
//    public void onPause() {
//        super.onPause();
//
//        requestQueue= Volley.newRequestQueue(getContext());
//
//        request.setTag(TAG);
//
//        requestQueue.add(request);
//
//        if (requestQueue != null) {
//            requestQueue.cancelAll(TAG);
//        }
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    void FindItem(View view) {
        logo_Shaar = view.findViewById(R.id.logo_Shaar);
        shaar = view.findViewById(R.id.shaar);
        CityInYourHand = view.findViewById(R.id.CityInYourHand);
        lbl_Text = view.findViewById(R.id.lbl_Text);
        ProgressBar = view.findViewById(R.id.ProgressBar);
        dbAdapter = new DBAdapter(getActivity());
        internet = new Internet(getContext());
    }

    void ShowItem() {
        try {

            handler_logo_Shaar = new Handler();
            handler_logo_Shaar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation aniFade = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_splash);
                    logo_Shaar.startAnimation(aniFade);
                    logo_Shaar.setVisibility(View.VISIBLE);
                }
            }, 300);

            handler_Shaar = new Handler();
            handler_Shaar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation scale_in = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                    shaar.startAnimation(scale_in);
                    shaar.setVisibility(View.VISIBLE);


                }
            }, 600);

            handler_CityInYourHand = new Handler();
            handler_CityInYourHand.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation slide_in_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
                    CityInYourHand.startAnimation(slide_in_left);
                    CityInYourHand.setVisibility(View.VISIBLE);
                }
            }, 800);

            handler_lbl_Text = new Handler();
            handler_lbl_Text.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation fadein = AnimationUtils.loadAnimation(getActivity(), R.anim.fadein);
                    lbl_Text.startAnimation(fadein);
                    lbl_Text.setVisibility(View.VISIBLE);
                }
            }, 1200);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ProgressBar.setVisibility(View.VISIBLE);
                    GetVersions();
                }
            }, 1400);

        } catch (Exception e) {

        }
    }

    //در اینجا ورژن های دیتابیس و برنامه از سرور گرفته می شوند
    void GetVersions() {


        //در اینجا آی دی کاربر خوانده می شود
        int IdUser = 0;
        try {
            Cursor User = dbAdapter.ExecuteQ("SELECT Id FROM TblUser LIMIT 1");
            if (User.moveToFirst()) {
                IdUser = Integer.valueOf(User.getString(0));
            }
        } catch (Exception e) {
        }

        String Url = Api + "Item/UpdateApp2?id=" + IdUser;
        request = new JsonObjectRequest(Request.Method.GET, Url, null, response -> {

            try {

                //در اینجا ورژن برنامه و دیتابیس در سرور گرفته می شود
                JSONObject VersionApp = response.getJSONObject("VersionApp");
                JSONArray VersionSqls = response.getJSONArray("VersionSql");

                //در اینجا از سمت سرور دریافت می شود که برنامه نیاز به پاک کردن دیتابیس دارد
                final boolean ClearCatch = VersionApp.getBoolean("ClearCatche");


                Cursor Setting = dbAdapter.ExecuteQ("SELECT Version,Exter1 FROM TblSetting LIMIT 1");

                //در اینجا ورژن ها در گوشی کاربر خوانده می شود
                float MyVersionApp = Float.valueOf(Setting.getString(0));
                float MyVersionSql = Float.valueOf(Setting.getString(1));

                //در اینجا ورژن برنامه گرفته می شود
                float VersionApplication = Float.valueOf(VersionApp.getString("Version"));

                //در اینجا چک می کند که ورژن اپلیکیشن دریافت شده از سرور بزرگتر از ورژن اپلیکیشن کاربر است
                if (VersionApplication > MyVersionApp) {

                    //در اینجا قسمت صحیح ورژن اپلیکیشن کاربر گرفته می شود
                    int Sahih_VersionAppUser = (int) MyVersionApp;

                    //در اینجا قسمت صحیح ورژن اپلکیشن ارسال شده در سمت سرور گرفته می شود
                    int Sahih_VersionAppServer = (int) VersionApplication;

                    //در اینجا اگر عدد صحیح ورژن ارسال شده در سمت سرور بزرگتر از عدد صحیح ورژن برنامه کاربر باشد آپدیت اجباری ست می شود
                    if (Sahih_VersionAppServer > Sahih_VersionAppUser) {
                        forcedUpdate = ForcedUpdate.Forced;
                    }
                    //اگر شرط بالا درست نباشد آپدیت برنامه اختیاری می شود
                    else {
                        forcedUpdate = ForcedUpdate.Optional;
                    }
                }
                //در اینجا اگر شرط بالا درست نباشد لازم نبودن آپدیت برنامه ست می شود
                else {
                    forcedUpdate = ForcedUpdate.ItIsNotNecessary;
                }


                //در اینجا چک می شود که آپدیت لازم است یا خیراگر لازم باشد شرط زیر اجرا می شود
                if (forcedUpdate != ForcedUpdate.ItIsNotNecessary) {

                    final int TypeToUpdate = VersionApp.getInt("Code");
                    final String Url2 = VersionApp.getString("Redirect");

                    //اگر آپدیت اجباری باشد کد زیر اجرا می شود
                    if (forcedUpdate == ForcedUpdate.Forced) {

                        ProgressBar.setVisibility(View.INVISIBLE);

                        new AlertDialog.Builder(getActivity())
                                .setTitle((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Message) + "</font>")))
                                .setMessage((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.TextNewVersionApp) + "</font>")))
                                .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Ok) + "</font>")), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (ClearCatch) {
                                            clearApplicationData();
                                        }
                                        GoToUpdate(TypeToUpdate, Url2);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setCancelable(false)
                                .show();

                    }
                    //اگر آپدیت اختیاری باشد کد زیر اجرا می شود
                    else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Message) + "</font>")))
                                .setMessage((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.TextNewVersionApp2) + "</font>")))
                                .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Ok) + "</font>")), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (ClearCatch) {
                                            clearApplicationData();
                                        }

                                        ProgressBar.setVisibility(View.INVISIBLE);

                                        GoToUpdate(TypeToUpdate, Url2);

                                        Stack_Back.MyStack_Back.Push("Fragment_Home", getContext());
                                    }
                                })
                                .setNegativeButton(R.string.NotNo, (dialogInterface, i) -> SetVersionSql(VersionSqls, MyVersionSql))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setCancelable(false)
                                .show();
                    }
                }
                //اگر آپدیت لازم نباشد در کد زیر کویری های دیتابیس را ست می کند
                else {
                    SetVersionSql(VersionSqls, MyVersionSql);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            ProgressBar.setVisibility(View.INVISIBLE);

            boolean HasInternet = internet.HaveNetworkConnection();

            if (!HasInternet) {

                new AlertDialog.Builder(getActivity())
                        .setTitle((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Error) + "</font>")))
                        .setMessage((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.CheckYourInternet) + "</font>")))
                        .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Reload) + "</font>")), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logo_Shaar.setVisibility(View.GONE);
                                shaar.setVisibility(View.GONE);
                                CityInYourHand.setVisibility(View.GONE);
                                lbl_Text.setVisibility(View.GONE);
                                ShowItem();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Error) + "</font>")))
                        .setMessage((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.YourInternetIsVerySlow) + "</font>")))
                        .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>" + getResources().getString(R.string.Reload) + "</font>")), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                logo_Shaar.setVisibility(View.GONE);
                                shaar.setVisibility(View.GONE);
                                CityInYourHand.setVisibility(View.GONE);
                                lbl_Text.setVisibility(View.GONE);
                                ShowItem();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley, request));
    }

    //برای پاک کردن حافظه دیتابیس
    public void clearApplicationData() {
        File cacheDirectory = getActivity().getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }

    // با فراخوانی این متد از طریق وبسایت یا گوگل پلی یا بازار برنامه آپدیت می شود
    void GoToUpdate(int type, String Url) {

        switch (type) {
            //اگر عدد یک باشد از طریق گوگل پلی برنامه آپدیت می شود
            case 1:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri.Builder uriBuilder = Uri.parse(Url)
                        .buildUpon()
                        .appendQueryParameter("id", "ir.tdaapp.diako.shaar")
                        .appendQueryParameter("launch", "false");

                intent.setData(uriBuilder.build());
                intent.setPackage("com.android.vending");
                startActivity(intent);
                break;

            //اگر عدد 2 باشد از طریق وب سایت برنامه آپدیت می شود
            case 2:
                Uri marketUri = Uri.parse(Url);
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                startActivity(marketIntent);
                break;

            //اگر عدد 3 باشد از طریق بازار برنامه آپدیت می شود
            case 3:
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                intent2.setData(Uri.parse(Url));
                intent2.setPackage("com.farsitel.bazaar");
                startActivity(intent2);
                break;
        }
    }

    void SetVersionSql(JSONArray VersionSqls, float MyVersionSql) {
        //در اینجا اگر آپدیت اجباری نباشد شرط زیر اجرا می شود و ادامه برنامه اجرا می شود و اگر شرط درست نباشد کاربر در صفحه اسپلش باقی می ماند
        if (forcedUpdate != ForcedUpdate.Forced) {

            //در اینجا کویری های دیتابیس که از سرور ارسال شده اند ست می شوند
            float version = 0;
            for (int i = 0; i < VersionSqls.length(); i++) {
                JSONObject object = VersionSqls.optJSONObject(i);

                try {
                    version = Float.valueOf(object.getString("Version"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //در اینجا اگر ورژن اسکیوال ارسال شده از سمت سرور از ورژن فعلی کاربر بزرگتر باشد کویری ارسال شده در دیتابیس ست می شود
                if (version > MyVersionSql) {
                    try {
                        dbAdapter.ExecuteQ(object.getString("Query"));
                    } catch (Exception e) {

                    }
                }
            }

            //در اینجا ورژن اسکیوال در دیتابیس ویرایش می شود
            if (version > MyVersionSql) {
                dbAdapter.ExecuteQ("update TblSetting set Exter1=" + version);
            }

            ProgressBar.setVisibility(View.INVISIBLE);

            Stack_Back.MyStack_Back.Push("Fragment_Home", getContext());
        }
    }
}
