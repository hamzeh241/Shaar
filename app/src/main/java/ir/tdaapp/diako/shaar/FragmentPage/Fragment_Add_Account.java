package ir.tdaapp.diako.shaar.FragmentPage;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.CompressImage;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.ETC.ReplaceData;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;
import ir.tdaapp.diako.shaar.ETC.Validation;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.R;

import java.security.cert.Extension;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diako on 5/5/2019.
 */

public class Fragment_Add_Account extends Fragment implements IBase {

    public static final String TAG="Fragment_Add_Account";

    EditText txt_FullName, txt_Email, txt_Mob;
    CompressImage compressImage;
    Button btn_Done;
    RelativeLayout back;
    Validation validation;
    LinearLayout Law_And_Regulations;
    Dialog_Law_And_Regulations dialog_law_and_regulations;
    ReplaceData replaceData;
    StringRequest request;
    RequestQueue requestQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.fragment_add_account, container, false);

        FindItem(view);
        OnClick();

        txt_Mob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b == true) {
                    txt_Mob.setGravity(Gravity.LEFT);
                    txt_Mob.setHint("");
                } else {
                    txt_Mob.setGravity(Gravity.RIGHT);
                    txt_Mob.setHint("شماره موبایل خود را وارد نمایید");
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (request != null) {
            requestQueue = Volley.newRequestQueue(getContext());

            request.setTag(TAG);

            requestQueue.add(request);

            if (requestQueue != null) {
                requestQueue.cancelAll(TAG);
            }
        }
    }

    void FindItem(View view) {
        txt_FullName = view.findViewById(ir.tdaapp.diako.shaar.R.id.AddAccount_txt_FullName);
        compressImage = new CompressImage(128, 128, 75, getActivity());
        btn_Done = view.findViewById(ir.tdaapp.diako.shaar.R.id.btn_Done);
        txt_Email = view.findViewById(ir.tdaapp.diako.shaar.R.id.AddAccount_Email);
        txt_Mob = view.findViewById(ir.tdaapp.diako.shaar.R.id.Fragment_AddAccount_txt_Mob);
        back = view.findViewById(ir.tdaapp.diako.shaar.R.id.back);
        validation = new Validation();
        Law_And_Regulations=view.findViewById(R.id.Law_And_Regulations);
        dialog_law_and_regulations=new Dialog_Law_And_Regulations();
        replaceData=new ReplaceData();
    }

    void OnClick() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stack_Back.MyStack_Back.Pop(getContext());
            }
        });

        Law_And_Regulations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_law_and_regulations.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(),"Dialog_Law_And_Regulations");
            }
        });

        //دکمه ثبت نام
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_Done.setEnabled(false);

                    //در اینجا چک می شود که ادیت تکست ها خالی نباشد
                    if (txt_FullName.getText().toString().equalsIgnoreCase("") ||
                            txt_Mob.getText().toString().equalsIgnoreCase("")) {
                        new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا اطلاعات را کامل وارد نمایید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>متوجه شدم</font>")), null).show();
                        btn_Done.setEnabled(true);
                    } else {

                        //در اینجا progressbar لودینگ نمایش داده می شود
                        final ProgressDialog progress = new ProgressDialog(getActivity());
                        progress.setTitle((Html.fromHtml("<font color='#FF7F27'>در حال ارسال</font>")));
                        progress.setMessage((Html.fromHtml("<font color='#FF7F27'>لطفا منتظر بمانید</font>")));
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();

                        String url2 = Api + "User";

                        request
                                = new StringRequest(
                                Request.Method.POST,
                                url2,
                                new Response.Listener() {
                                    @Override
                                    public void onResponse(Object response) {

                                        btn_Done.setEnabled(true);

                                        //در این خط لودیگ مخفی می شود
                                        progress.dismiss();

                                        String Resault = response.toString().replace("\"", "");
                                        //در اینجا چک می شود که عملیات در سرور به درستی انجام شده است
                                        if (Resault.equalsIgnoreCase("true")) {

                                            // در اینجا اطلاعات کاربر به سمت اس ام اس پنل ارسال می شوند
                                            Bundle bundle = new Bundle();
                                            bundle.putString("CellPhone", txt_Mob.getText().toString());

                                            //در اینجا صفحه اس ام اس پنل نمایش داه می شود
                                            Stack_Back.MyStack_Back.Push("Fragment_SMS_Panel", getContext(), bundle);
                                        } else {
                                            new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>" + Resault + "</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        btn_Done.setEnabled(true);
                                        progress.dismiss();
                                        new AlertDialog.Builder(getActivity()).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>سرعت اینترنت شما مطلوب نیست لطفا در صورت استفاده از فیلتر شکن آن را خاموش کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>باشه</font>")), null).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> json = new HashMap<String, String>();
                                json.put("FullName",replaceData.Number_PersianToEnglish(txt_FullName.getText().toString()));
                                json.put("Email", replaceData.Number_PersianToEnglish(txt_Email.getText().toString()));
                                json.put("CellPhone", replaceData.Number_PersianToEnglish(txt_Mob.getText().toString()));
                                return json;
                            }
                        };

                        AppController.getInstance().addToRequestQueue(Policy_Volley.SetPostRetryAndPolicy(request));
                    }
            }
        });

    }
}
