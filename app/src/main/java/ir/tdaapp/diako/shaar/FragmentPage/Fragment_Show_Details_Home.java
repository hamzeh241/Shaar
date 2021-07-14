package ir.tdaapp.diako.shaar.FragmentPage;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.ViewPager_DetailsAdapter;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Font;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.ETC.Policy_Volley;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.ViewModel.VM_ItemFeature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/17/2019.
 */

public class Fragment_Show_Details_Home extends Fragment implements IBase {

    public static final String TAG = "Fragment_Show_Details_Home";

    ViewPager viewPager;
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView img_Right, img_Left;
    ViewPager_DetailsAdapter viewPager_detailsAdapter;

    TextView lbl_Special, lbl_Price, lbl_Address, lbl_Id, lbl_CountRoom, lbl_Area, lbl_Title, lbl_Description, lbl_Mortgage;
    TextView lbl_more_information, lbl_Expert;

    LinearLayout FeaturesLayout;
    Button btn_Call, btn_SMS;
    String PhoneNumber = "", Id = "", DateInsert = "", TourId = "";
    Internet internet;
    List<String> Images;
    ProgressBar progressbar;
    DBAdapter dbAdapter;
    int Area = 0;
    Font font;
    CircleImageView profile_image;
    WebView webView;
    JsonObjectRequest req;
    RequestQueue requestQueue;
    CardView btn_FullDisplay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_details_home, container, false);

//        ((MainActivity) getActivity()).frameLayout1.setVisibility(View.GONE);

        FindItem(view);

        toolbar.setTitle("جزئیات ملک");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);


        setHasOptionsMenu(true);

        onClick();

        GetData();


        font.SetFont("font/b_homa.ttf", lbl_Price);
        font.SetFont("font/b_homa.ttf", lbl_CountRoom);
        font.SetFont("font/b_homa.ttf", lbl_Area);
        font.SetFont("font/b_homa.ttf", lbl_Mortgage);

        return view;
    }

    void FindItem(View view) {
        img_Right = view.findViewById(R.id.Fragment_Show_Details_Right);
        img_Left = view.findViewById(R.id.Fragment_Show_Details_Left);
        viewPager = view.findViewById(R.id.Show_Details_Home_ViewPager);
        toolbar = view.findViewById(R.id.mToolbar);
        lbl_Special = view.findViewById(R.id.Special);
        lbl_Price = view.findViewById(R.id.Price);
        lbl_Address = view.findViewById(R.id.Address);
        lbl_Id = view.findViewById(R.id.Id);
        lbl_CountRoom = view.findViewById(R.id.CountRoom);
        lbl_Area = view.findViewById(R.id.Area);
        lbl_Title = view.findViewById(R.id.Title);
        lbl_Description = view.findViewById(R.id.Description);
        lbl_more_information = view.findViewById(R.id.more_information);
        lbl_Expert = view.findViewById(R.id.Expert);
        FeaturesLayout = view.findViewById(R.id.FeaturesLayout);
        btn_Call = view.findViewById(R.id.btn_Call);
        btn_SMS = view.findViewById(R.id.btn_SMS);
        progressbar = view.findViewById(R.id.progressbar);
        internet = new Internet(getContext());
        Images = new ArrayList<>();
        dbAdapter = new DBAdapter(getContext());
        font = new Font(getContext());
        profile_image = view.findViewById(R.id.profile_image);
        lbl_Mortgage = view.findViewById(R.id.lbl_Mortgage);
        btn_FullDisplay = view.findViewById(R.id.btn_FullDisplay);
        webView = view.findViewById(R.id.webView);
    }

    void onClick() {

        btn_FullDisplay.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Id", TourId);
            Fragment_Login_Email fragment_login_email = new Fragment_Login_Email();
            fragment_login_email.setArguments(bundle);
//            Stack_Back.MyStack_Back.Push("Fragment_Login_Email", getContext(), bundle);
            ((MainActivity) getActivity()).onAddFragment(fragment_login_email, 0, 0, true, Fragment_Login_Email.TAG);
        });

        img_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItem(+1), true);
            }
        });

        img_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItem(-1), true);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Stack_Back.MyStack_Back.Pop(getActivity());
                getActivity().onBackPressed();
            }
        });

        btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + PhoneNumber));
                startActivity(intent);
            }
        });

        btn_SMS.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {

                String Message = getResources().getString(R.string.SMSMessage);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms: " + PhoneNumber));
                sendIntent.putExtra("sms_body", Message);
                startActivity(sendIntent);

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        requestQueue = Volley.newRequestQueue(getContext());

        req.setTag(TAG);

        requestQueue.add(req);

        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //در اینجا تور مجازی ست می شود
    void GetTour() {

        if (!TourId.equalsIgnoreCase("")) {
            webView.setVisibility(View.VISIBLE);
            btn_FullDisplay.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(TourId);
        } else {
            webView.setVisibility(View.GONE);
            btn_FullDisplay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {

        if (IsLike()) {
            MenuItem itemLike = menu.findItem(R.id.toolbar_Like);
            if (itemLike != null)
                itemLike.setIcon(R.drawable.is_like_home);
        }

    }

    //برای نمایش منو در toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //زمانی که کاربر یکی از دکمه های منو را انتخاب می کند
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_Share:

                String Message = getResources().getString(R.string.ShareHome1) + " " + Id + " " + getResources().getString(R.string.ShareHome2);
                Message += "\n\n" + lbl_Title.getText().toString();
                Message += "\n\n" + getResources().getString(R.string.Address) + ": " + lbl_Address.getText().toString();
                Message += "\n\n" + lbl_Description.getText().toString();
                Message += "\n\n" + getResources().getString(R.string.PhoneYourUserPro) + ": " + PhoneNumber;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Message);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.toolbar_Like:


                if (!IsLike()) {
                    Cursor cursor = dbAdapter.executeQuery("insert into TblSearch (Id,Title,Price,Area,Room,DateInsert,Address,IsFavorit,FkListSearch,Age,FkLocation) values (" + Integer.parseInt(Id) + ",'" + lbl_Title.getText().toString() + "',200," + Area + "," + Integer.parseInt(lbl_CountRoom.getText().toString()) + ",'" + DateInsert + "','" + lbl_Address.getText().toString() + "',1,0,0,0)");
                    item.setIcon(R.drawable.is_like_home);
                    cursor.close();
                    dbAdapter.close();
                } else {
                    Cursor cursor = dbAdapter.executeQuery("delete from TblSearch where Id=" + Integer.parseInt(Id));
                    item.setIcon(R.drawable.like_home);
                    cursor.close();
                    dbAdapter.close();
                }
                break;
        }
        return true;
    }

    void GetData() {

        Bundle bundle = getArguments();
        int Type = 0;

        if (bundle != null)
            Type = bundle.getInt("Id");

        if (Type != 0) {

            Id = String.valueOf(Type);
            String url = Api + "Item/" + Type;

            req = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {
                        //در اینجا چک می شود که این آیتم مورد نظر ویژه(جیاواز)است که TextView جیاواز نمایش داده شود
                        boolean Special = Boolean.valueOf(response.getString("Special"));
                        if (Special) {
                            lbl_Special.setVisibility(View.VISIBLE);
                        }

                        int Target = response.getInt("enumTarget");

                        if (Target == 1) {
                            lbl_Price.setText(response.getString("PriceShow") + " تومان");
                        } else if (Target == 0) {
                            lbl_Mortgage.setVisibility(View.VISIBLE);
                            lbl_Mortgage.setText("رهن " + response.getString("MortgageShow") + " تومان");
                            lbl_Price.setText("اجاره " + response.getString("PriceShow") + " تومان");
                        }

                        lbl_Address.setText(response.getString("Address"));

                        lbl_Id.setText("کدآگهی: " + response.getString("Id"));

                        lbl_CountRoom.setText(response.getString("Room"));

                        lbl_Area.setText(response.getString("Area") + " متر مربع");

                        lbl_Title.setText(response.getString("Title"));

                        lbl_Description.setText(response.getString("Description"));


                        if (!response.getString("TblUserProFullName").equalsIgnoreCase("null"))
                            lbl_Expert.setText("کارشناس: " + response.getString("TblUserProFullName"));

                        if (!response.getString("TblUserProTel").equalsIgnoreCase("null"))
                            PhoneNumber = response.getString("TblUserProTel");

                        if (!response.getString("DateInsert").equalsIgnoreCase("null"))
                            DateInsert = response.getString("DateInsert");

                        JSONArray images = response.getJSONArray("TblItemImages");

                        JSONArray array = response.getJSONArray("TblItemFeatuers");

                        if (!response.getString("Area").equalsIgnoreCase("null"))
                            Area = Integer.parseInt(response.getString("Area"));

                        if (!response.getString("TourId").equalsIgnoreCase("null")) {
                            TourId = response.getString("TourId");
                        }

                        GetImages(images);

                        SetFeatures(array);

                        GetTour();

                        String ImageUrl = ApiImage + "ImageSave/" + response.getString("TblUserProImage");

                        Glide.with(getActivity()).asBitmap().load(ImageUrl)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                        try {
                                            profile_image.setImageBitmap(resource);
                                        } catch (Exception e) {
                                        }
                                    }
                                });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    try {
                        new AlertDialog.Builder(getContext())
                                .setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>")))
                                .setMessage((Html.fromHtml("<font color='#FF7F27'>همچین خانه ای یافت نشد لطفا مجددا امتحان کنید </font>")))
                                .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>بارگیری مجدد</font>")), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (internet.HaveNetworkConnection()) {
//                                            Bundle bundle = new Bundle();
//                                            bundle.putInt("Id", Integer.parseInt(Id));
//                                            Stack_Back.MyStack_Back.Push("Fragment_Show_Details_Home2", getContext(), bundle);
                                        } else {
                                            Toast.makeText(getContext(), "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } catch (Exception ex) {

                    }
                }
            });

            AppController.getInstance().addToRequestQueue(Policy_Volley.SetTimeOut(TimeOutVolley, req));

        }
    }

    void SetFeatures(JSONArray jsonArray) {

        List<VM_ItemFeature> Textfeatures = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);

                VM_ItemFeature feature = new VM_ItemFeature();
                feature.setTitle(object.get("Titel").toString());
                feature.setFkFormat(Integer.parseInt(object.get("FkFormat").toString()));
                feature.setValue(object.get("Value").toString());

                //در اینجا چک می شود فرمت داده از نوع بولین است
                if (feature.getFkFormat() == 2 && feature.getValue().equalsIgnoreCase("true")) {

                    //در اینجا Linear layout مربوطه ساخته می شود
                    LinearLayout linearLayout = new LinearLayout(getContext());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 0);

                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setWeightSum(2);
                    linearLayout.setPadding(4, 4, 5, 4);
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.stroke_property));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    //در اینجا ساخت LinearLayout به پایان می رسد

                    //در اینجا TextView مربوط به Title ساخته می شود
                    TextView textView = new TextView(getContext());

                    textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                    textView.setText(feature.getTitle());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    textView.setTypeface(null, Typeface.BOLD);

                    //در اینجا ساخت TextView به پایان می رسد

                    //در اینجا یک RelativeLayout ساخته می شود که ImageView پایینی در آن قرار می گیرد
                    RelativeLayout relativeLayout = new RelativeLayout(getContext());

                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    relativeLayout.setGravity(Gravity.CENTER);

                    //پایان ساخت RelativeLayout

                    //در اینجا بخش مربوط به ImageView ساخته می شود
                    ImageView imageView = new ImageView(getContext());

                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    imageView.setBackground(getResources().getDrawable(R.drawable.checked));

                    //در اینجا ImageView مربوطه ساخته میشود

                    relativeLayout.addView(imageView);
                    linearLayout.addView(textView);
                    linearLayout.addView(relativeLayout);

                    FeaturesLayout.addView(linearLayout);
                }

                //در اینجا چک می شود که فرمت داده از نوع عدد یا اسپینر است
                else if (feature.getFkFormat() == 3 || feature.getFkFormat() == 4) {
                    //در اینجا Linear layout مربوطه ساخته می شود
                    LinearLayout linearLayout = new LinearLayout(getContext());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 10, 0, 0);

                    linearLayout.setLayoutParams(layoutParams);
                    linearLayout.setWeightSum(2);
                    linearLayout.setPadding(4, 4, 5, 4);
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.stroke_property));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    //در اینجا ساخت LinearLayout به پایان می رسد

                    //در اینجا TextView مربوط به Title ساخته می شود
                    TextView textView = new TextView(getContext());

                    textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                    textView.setText(feature.getTitle());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    textView.setTypeface(null, Typeface.BOLD);

                    //در اینجا ساخت TextView به پایان می رسد

                    //در اینجا یک RelativeLayout ساخته می شود که TextView پایینی در آن قرار می گیرد
                    RelativeLayout relativeLayout = new RelativeLayout(getContext());

                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

                    relativeLayout.setGravity(Gravity.CENTER);

                    //پایان ساخت RelativeLayout

                    //در اینجا بخش مربوط به TextView مقدار ساخته می شود

                    TextView lblValue = new TextView(getContext());
                    lblValue.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    lblValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    lblValue.setText(feature.getValue());

                    //در اینجا TextView مفدار مربوطه ساخته میشود

                    relativeLayout.addView(lblValue);
                    linearLayout.addView(textView);
                    linearLayout.addView(relativeLayout);

                    FeaturesLayout.addView(linearLayout);
                }
                //در این شرط اگر فرمت ویژگی از نوع متن در این لیست ذخیره می شود
                else {
                    Textfeatures.add(feature);
                }

            } catch (Exception e) {

            }
        }

        for (int i = 0; i < Textfeatures.size(); i++) {

            //در اینجا Linear layout مربوطه ساخته می شود
            LinearLayout linearLayout = new LinearLayout(getContext());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 10, 0, 0);

            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setPadding(4, 4, 5, 4);
            linearLayout.setBackground(getResources().getDrawable(R.drawable.stroke_property));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //در اینجا ساخت LinearLayout به پایان می رسد

            //در اینجا TextView مربوط به Title ساخته می شود
            TextView textView = new TextView(getContext());

            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText(Textfeatures.get(i).getTitle());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setGravity(Gravity.CENTER);
            //در اینجا ساخت TextView به پایان می رسد

            //در اینجا بخش مربوط به TextView مقدار ساخته می شود
            TextView lblValue = new TextView(getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);

            lblValue.setLayoutParams(new LinearLayout.LayoutParams(params));
            lblValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            lblValue.setText(Textfeatures.get(i).getValue());
            lblValue.setGravity(Gravity.CENTER);

            //در اینجا TextView مفدار مربوطه ساخته میشود

            linearLayout.addView(textView);
            linearLayout.addView(lblValue);

            FeaturesLayout.addView(linearLayout);
        }
    }

    //در اینجا چک می شود که آیتم لایک شده است
    boolean IsLike() {
        Cursor cursor = dbAdapter.executeQuery("select count(Id) from TblSearch where IsFavorit=1 and Id=" + Integer.parseInt(Id));
        int c = Integer.parseInt(cursor.getString(0));
        cursor.close();
        dbAdapter.close();
        return c > 0;
    }

    //در اینجا لیستی از عکس ها که در سرور دریافت می شود را در Images قرار داده می شود
    void GetImages(JSONArray array) {
        if (array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = array.getJSONObject(i);
                    Images.add(ApiImage + "ImageSave/" + object.getString("ImageUrl"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Images.add(ApiImage + "ImageSave/NoImage");
        }
        viewPager_detailsAdapter = new ViewPager_DetailsAdapter(getActivity(), Images, Integer.parseInt(Id));
        viewPager.setAdapter(viewPager_detailsAdapter);
    }

}
