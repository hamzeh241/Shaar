package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.ReplaceData;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.Interface.IBase;

/**
 * Created by Diako on 5/3/2019.
 */

public class Fragment_Login_Home extends Fragment implements IBase {
    EditText txt_phone;
    ImageView close;
    RelativeLayout AddAccount;
    Button GoWithEmail, GoMoshaver, btn_Register;
    DBAdapter dbAdapter;
    Internet internet;
    ReplaceData replaceData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_login_home, container, false);


        FindItem(view);

        txt_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == true) {
                    txt_phone.setGravity(Gravity.LEFT);
                    txt_phone.setHint("");
                } else {
                    txt_phone.setGravity(Gravity.RIGHT);
                    txt_phone.setHint("شماره موبایل خود را وارد نمایید");
                }
            }
        });

        OnClick();

        return view;
    }

    void OnClick() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stack_Back.MyStack_Back.Pop(getActivity());
            }
        });

        AddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stack_Back.MyStack_Back.Push("Fragment_Add_Account", getActivity());
            }
        });

        GoWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stack_Back.MyStack_Back.Push("Fragment_Login_Email", getActivity());
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (internet.HaveNetworkConnection()) {
                    if (!txt_phone.getText().toString().equalsIgnoreCase("")) {
                        final ProgressDialog progress = new ProgressDialog(getActivity());
                        progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
                        progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();

                        String url = Api + "User/CheckHasUser?id=" + replaceData.Number_PersianToEnglish(txt_phone.getText().toString());
                        StringRequest
                                stringRequest
                                = new StringRequest(
                                Request.Method.GET,
                                url,
                                new Response.Listener() {
                                    @Override
                                    public void onResponse(Object response) {
                                        if (response.toString().equalsIgnoreCase("true")) {
                                            progress.dismiss();

                                            Stack_Back.MyStack_Back.Push("Fragment_SMS_Panel", getActivity());

                                            // در اینجا اطلاعات کاربر به سمت اس ام اس پنل ارسال می شوند
                                            Bundle bundle = new Bundle();
                                            bundle.putString("CellPhone", txt_phone.getText().toString());

                                            //در اینجا صفحه اس ام اس پنل نمایش داه می شود
                                            Fragment_SMS_Panel fragment_sms_panel = new Fragment_SMS_Panel();
                                            fragment_sms_panel.setArguments(bundle);
                                            ((AppCompatActivity) getActivity()).getSupportFragmentManager().
                                                    beginTransaction().replace(ir.tdaapp.diako.shaar.R.id.Fragment_Main, fragment_sms_panel).commit();

                                        } else {
                                            progress.dismiss();
                                            new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>همچین کاربری قبلا ثبت نشده است لطفا یک حساب کاربری جدید درست کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        progress.dismiss();
                                        new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای در سرور رخ داده است لطفا مجددا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                    }
                                });
                        AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley,stringRequest));
                    } else {
                        new androidx.appcompat.app.AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا شماره م.بایل خود را وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void FindItem(View view) {
        close = view.findViewById(ir.tdaapp.diako.shaar.R.id.LogIn_Home_close);
        AddAccount = view.findViewById(ir.tdaapp.diako.shaar.R.id.LogIn_Home_NewAccount);
        GoWithEmail = view.findViewById(ir.tdaapp.diako.shaar.R.id.LogIn_Home_GoWithEmail);
        GoMoshaver = view.findViewById(ir.tdaapp.diako.shaar.R.id.LogIn_Home_GoMoshaver);
        btn_Register = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Register);
        txt_phone = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_Home_txt_Mob);
        dbAdapter = new DBAdapter(getActivity());
        internet=new Internet(getContext());
        replaceData=new ReplaceData();
    }
}
