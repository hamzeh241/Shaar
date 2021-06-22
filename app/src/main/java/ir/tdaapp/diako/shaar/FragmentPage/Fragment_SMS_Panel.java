package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.ReplaceData;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Diako on 6/24/2019.
 */

public class Fragment_SMS_Panel extends Fragment implements IBase {
    public static final String TAG = "Fragment_SMS_Panel";
    String CellPhone;

    int activity = 0;

    public Fragment_SMS_Panel(int activity) {
        this.activity = activity;
    }

    public Fragment_SMS_Panel() {

    }

    //    int activity = 0;
//
//    public Fragment_SMS_Panel(int activity) {
//        this.activity = activity;
//    }
//    public Fragment_SMS_Panel() {
//        this.activity = activity;
//    }

    TextView txt_Discription, txt_Timer;
    Button btn_Done, btn_Resend;
    EditText txt_Code;
    DBAdapter dbAdapter;
    CountDownTimer timer;
    RelativeLayout back;
    ReplaceData replaceData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_sms_panel, container, false);

        FindItem(view);

        //در اینجا از صفحه لاگین اطلاعات مربوطه را دریافت می کند
        Bundle bundle = getArguments();
        CellPhone = bundle.getString("CellPhone");

        txt_Discription.setText("کد تایید به شماره " + CellPhone + " " + "ارسال شده است لطفا آن را وارد نمایید");
        DateTime();
        OnClick();

        return view;
    }

    void FindItem(View view) {
        txt_Discription = view.findViewById(ir.tdaapp.diako.shaar.R.id.Discription);
        btn_Done = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);
        txt_Timer = view.findViewById(ir.tdaapp.diako.shaar.R.id.txtTimer);
        txt_Code = view.findViewById(ir.tdaapp.diako.shaar.R.id.txt_Code);
        btn_Resend = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Resend);
        dbAdapter = new DBAdapter(getActivity());
        back = view.findViewById(ir.tdaapp.diako.shaar.R.id.backall);
        replaceData = new ReplaceData();

    }

    void OnClick() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getContext());
                getActivity().onBackPressed();
            }
        });

        //دکمه ارسال
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txt_Code.getText().toString().equalsIgnoreCase("")) {
                    new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>اخطار</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا کد را وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                } else {

                    //آدرس api که با فرستادن اطلاعات به سمت سرور که نتیجه بولین ارسال می شود
                    String url = Api + "User/" + CellPhone + "?sms=" + replaceData.Number_PersianToEnglish(txt_Code.getText().toString());

                    //در اینجا progressbar لودینگ نمایش داده می شود
                    final ProgressDialog progress = new ProgressDialog(getActivity());
                    progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
                    progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();

                    StringRequest
                            stringRequest
                            = new StringRequest(
                            Request.Method.GET,
                            url,
                            new Response.Listener() {
                                @Override
                                public void onResponse(Object response) {

                                    //در این خط لودیگ مخفی می شود
                                    progress.dismiss();

                                    //در اینجا چک می شود که عملیات در سرور به درستی انجام شده است
                                    if (response.toString().equalsIgnoreCase("true")) {

                                        AddUser();

                                    } else {
                                        new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا کد را درست وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>متوجه شدم</font>")), null).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progress.dismiss();
                                    new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>سرعت اینترنت شما مطلوب نیست لطفا در صورت استفاده از فیلتر شکن آن را خاموش کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                }
                            });

                    AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley, stringRequest));
                }
            }
        });


        //دکمه ارسال مجدد
        btn_Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTime();

                //آدرس api که با فرستادن اطلاعات به سمت سرور که نتیجه بولین ارسال می شود
                String url = "http://api.shaarapp.ir/api/User/" + CellPhone;

                //در اینجا progressbar لودینگ نمایش داده می شود
                final ProgressDialog progress = new ProgressDialog(getActivity());
                progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
                progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                StringRequest
                        stringRequest
                        = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener() {
                            @Override
                            public void onResponse(Object response) {

                                //در این خط لودیگ مخفی می شود
                                progress.dismiss();

                                //در اینجا چک می شود که عملیات در سرور به درستی انجام شده است
                                if (response.toString().equalsIgnoreCase("true")) {
                                    Toast.makeText(getActivity(), "پیامک دوباره ارسال شد", Toast.LENGTH_SHORT).show();
                                } else {
                                    new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای در عملیات رخ داده است</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progress.dismiss();
                                new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>سرعت اینترنت شما مطلوب نیست لطفا در صورت استفاده از فیلتر شکن آن را خاموش کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                            }
                        });

                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });
    }

    void DateTime() {
        btn_Resend.setEnabled(false);
        btn_Resend.setBackgroundColor(getResources().getColor(ir.tdaapp.diako.shaar.R.color.colorGray));

        timer = new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                txt_Timer.setText("ارسال مجدد: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                txt_Timer.setText("ارسال مجدد: " + "0");
                btn_Resend.setEnabled(true);
                btn_Resend.setBackgroundDrawable(getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.change_color_btn));
            }
        }.start();
    }

    void AddUser() {
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setTitle((Html.fromHtml("<font color='#FF7F27'>درحال ارسال</font>")));
        progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        String url = Api + "User/UserInfo?id=" + CellPhone;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Cursor cursor = dbAdapter.ExecuteQ("select * from TblUser");
                            if (cursor != null && cursor.moveToFirst()) {
                                int a = cursor.getCount();
                                if (a > 0) {
                                    dbAdapter.ExecuteQ("delete from TblUser");
                                    dbAdapter.close();
                                }
                            }
                            cursor.close();
                            String AddUser = "insert into TblUser ('Id','FullName','Email','CellPhone') values ('" + response.getString("Id") + "','" + response.getString("FullName") + "','" + response.getString("Email") + "','" + CellPhone + "')";
                            dbAdapter.ExecuteQ(AddUser);
                            dbAdapter.close();
                            timer.cancel();
                            progress.dismiss();

//                            Stack_Back.MyStack_Back.Push("Succefull_Register", getActivity());

                            switch (activity) {

                                case 0:
                                    ((MainActivity) getActivity()).onAddFragment(new Succefull_Register(), 0, 0, true, Succefull_Register.TAG);
                                    break;

                                case 1:
                                    ((GuideActivity) getActivity()).onAddFragment(new Succefull_Register(), 0, 0, true, Succefull_Register.TAG);
                                    break;

                                case 2:
                                    ((CarActivity) getActivity()).onAddFragment(new Succefull_Register(), 0, 0, true, Succefull_Register.TAG);
                                    break;
                            }


                        } catch (JSONException e) {

                            progress.dismiss();
                            new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>مشکلی در عملیات رخ داده است لطفا بعدا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                //todo clear cache
                new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>سرعت اینترنت شما نامطلوب است لطفا دوباره تلاش نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
            }
        });

        AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley, req));
    }
}