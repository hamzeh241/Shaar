package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.SliderCarItemDetails;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.Database.TblCarFavoriets;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarDetailFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.AppBarStateChangeListener;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarDetailFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnGlideImageListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.ZoomOutPageTransformer;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.li_utility.Codes.ShowPriceTextView;

public class CarDeatailFragment extends CarBaseFragment implements OnGlideImageListener, CarDetailFragmentService, View.OnClickListener {

    public static final String TAG = "CarDeatailFragment";

    int itemId, userId;

    String number;


    TextView carName, productionYear, mileage, carBodyStatus, carBodyStatus2, brand,
            chasisStatus, insuranceTime, gearBox, ducument, salesType, price, description,
            expertName, txtExchange, address, color;
    TextView toolbarTitle;

    CircleImageView circleImageView;
    ImageButton favorite, refresh, btnBack;
    ImageView back, forward;
    ShimmerFrameLayout loading;
    Button textExpert, callExpert, showMoreDescribe;
    NestedScrollView scrollView;
    AppBarLayout appBarLayout;

    CoordinatorLayout root;
    ViewPager2 viewPager;

    String descriptionText, expertPhone;
    TblCarFavoriets tblCarFavoriets;
    CarDetailFragmentPresenter presenter;

    SliderCarItemDetails viewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_details_fragment, container, false);

        findView(view);
        implement();
        hideKeyboard(getActivity());

        return view;
    }

    private void findView(View view) {

        presenter = new CarDetailFragmentPresenter(getContext(), this);

        //car main info
        carName = view.findViewById(R.id.car_name_detail_fragment);
        productionYear = view.findViewById(R.id.txt_production_yaer);
        mileage = view.findViewById(R.id.txt_milage_detail);
        carBodyStatus = view.findViewById(R.id.txt_car_body_status_content);
        carBodyStatus2 = view.findViewById(R.id.txt_car_body_status2);
        brand = view.findViewById(R.id.txt_car_brand);
        chasisStatus = view.findViewById(R.id.tct_car_chasis_status);
        insuranceTime = view.findViewById(R.id.txt_car_insurance_time);
        gearBox = view.findViewById(R.id.txt_car_gearBox_type);
        ducument = view.findViewById(R.id.txt_car_ducument_type);
        salesType = view.findViewById(R.id.txt_car_sales_type);
        price = view.findViewById(R.id.txt_car_price);
        txtExchange = view.findViewById(R.id.txt_car_exchange);
        address = view.findViewById(R.id.txt_car_address);
        color = view.findViewById(R.id.txt_car_color);

        //other TextViews
        description = view.findViewById(R.id.txt_car_description);
        expertName = view.findViewById(R.id.txt_expert_name);
        toolbarTitle = view.findViewById(R.id.carDetailsToolbarTitle);

        //ImageViews
        circleImageView = view.findViewById(R.id.circle_imageview_expert);
        favorite = view.findViewById(R.id.img_faverite_detail);
        back = view.findViewById(R.id.img_back_viewPager);
        forward = view.findViewById(R.id.img_forward_viewPager);

        //Buottns
        showMoreDescribe = view.findViewById(R.id.btn_show_more_describe);
        callExpert = view.findViewById(R.id.btn_call_expert);
        textExpert = view.findViewById(R.id.btn_text_expert);
        refresh = view.findViewById(R.id.imgBtn_refresh_car);
        btnBack = view.findViewById(R.id.imageButton_details_car);

        loading = view.findViewById(R.id.viewPager_loading);
        scrollView = view.findViewById(R.id.nested_scrollview);
        appBarLayout = view.findViewById(R.id.carDetailsAppBar);
        root = view.findViewById(R.id.car_detail_root_layout);
        viewPager = view.findViewById(R.id.viewPager_car_detail);
        tblCarFavoriets = new TblCarFavoriets(getContext());
    }

    private void implement() {
        itemId = getArguments().getInt("ID");
        presenter.start(itemId);
        callExpert.setOnClickListener(this);
        textExpert.setOnClickListener(this);
        showMoreDescribe.setOnClickListener(this);
        favorite.setOnClickListener(this);
        refresh.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case IDLE:
                        changeToolbarItems(R.color.black);
                        break;
                    case EXPANDED:
                        changeToolbarItems(R.color.black);
                        break;
                    case COLLAPSED:
                        changeToolbarItems(R.color.colorWhite);
                        break;
                }
            }
        });
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void changeToolbarItems(@ColorRes int color) {
        ImageViewCompat.setImageTintList(favorite, ColorStateList.valueOf(getResources().getColor(color)));
        ImageViewCompat.setImageTintList(btnBack, ColorStateList.valueOf(getResources().getColor(color)));
        toolbarTitle.setTextColor(getResources().getColor(color));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //تماس با کارشناس
            case R.id.btn_call_expert:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
                break;
            //ارسال پیام به کارشناس
            case R.id.btn_text_expert:
                String Message = getResources().getString(R.string.SMSMessage);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms: " + number));
                sendIntent.putExtra("sms_body", Message);
                startActivity(sendIntent);
                break;
            case R.id.btn_show_more_describe:
                TransitionManager.beginDelayedTransition(root);
                description.setText(descriptionText);
                showMoreDescribe.setVisibility(View.GONE);
                break;
            case R.id.img_faverite_detail:
                if (tblCarFavoriets.checkState(itemId)) {
                    tblCarFavoriets.deleteFavorite(itemId);
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                } else {
                    tblCarFavoriets.addFavorite(itemId);
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                break;
            case R.id.img_forward_viewPager:
                try {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.img_back_viewPager:
                try {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imageButton_details_car:
                getActivity().onBackPressed();
                break;
            case R.id.imgBtn_refresh_car:
                presenter.start(itemId);
                loading.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onCarReceived(CarDetailModel model) {
        int resId = tblCarFavoriets.checkState(itemId) ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
        favorite.setImageResource(resId);
        carName.setText(model.getName());
        productionYear.setText(model.getProductionYear());
        new ShowPriceTextView(model.getMileage(), mileage);
//      mileage.setText(model.getMileage());
        carBodyStatus.setText(model.getCarBodyStatus());
        carBodyStatus2.setText(model.getCarBodyStatus());
        brand.setText(model.getBrand());
        chasisStatus.setText(model.getChassisStatus());
        insuranceTime.setText(model.getInsuranceTime());
        gearBox.setText(model.getGearBox());
        ducument.setText(model.getDocument());
        salesType.setText(model.getSalesType());
        price.setText(model.getPrice());
        description.setText(model.getDescription());
        expertName.setText(model.getExpertName());
        address.setText(model.getAddress());
        color.setText(model.getColor());

        if (model.isExchange()) {
            txtExchange.setText(R.string.exchange);
        } else {
            txtExchange.setText(R.string.notExchange);
        }
        //ست کردن عکس کارشناس
        Glide.with(this)
                .load(CarBaseApi.API_IMAGE_CAR_EXPERT + model.getExpertImage())
                .into(circleImageView);

        descriptionText = model.getDescription();
        if (descriptionText.length() > 300) {
            showMoreDescribe.setVisibility(View.VISIBLE);
            String subString = descriptionText.substring(0, 300);
            description.setText(subString);
        } else {
            showMoreDescribe.setVisibility(View.GONE);
        }
        viewPagerAdapter = new SliderCarItemDetails(getContext(), model.getPhotos(), this);
        viewPager.setAdapter(viewPagerAdapter);
        number = model.getPhone();
    }

    @Override
    public void onPresenterStart() {

    }

    @Override
    public void onError(ResaultCode resaultCode) {
        String error = "";
        String title = "";
        @DrawableRes int imageRes = R.drawable.ic_warning;

        switch (resaultCode) {
            case TimeoutError:
                error = getString(R.string.timeout_error);
                title = getString(R.string.timeout_error_title);
                imageRes = R.drawable.ic_router_device;
                break;
            case NetworkError:
                error = getString(R.string.network_error);
                title = getString(R.string.network_error_title);
                imageRes = R.drawable.ic_router_device;
                break;
            case ServerError:
                error = getString(R.string.server_error);
                title = getString(R.string.server_error_title);
                imageRes = R.drawable.ic_server_error;
                break;
            case ParseError:
            case Error:
                title = getString(R.string.unknown_error_title);
                error = getString(R.string.unknown_error);
                imageRes = R.drawable.ic_warning;
                break;
        }
        showErrorDialog(new ErrorDialog.Builder(getContext())
                .setErrorTitle(title)
                .setErrorSubtitle(error)
                .setImageUrl(imageRes)
                .setButtonText(R.string.try_again)
                .setClickListener(() ->
                        presenter.start(itemId)));
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void loadingState(boolean load) {
        if (load) {
            loading.startShimmer();
            refresh.setVisibility(View.GONE);
            scrollView.setAlpha(0f);
        } else {
            AlphaAnimation animation1 = new AlphaAnimation(0.2f, 1.0f);
            animation1.setDuration(500);
            scrollView.setAlpha(1f);
            scrollView.startAnimation(animation1);
            loading.stopShimmer();
            loading.setVisibility(View.GONE);
            refresh.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadState(boolean successful) {
        refresh.setVisibility(successful ? View.GONE : View.VISIBLE);
        viewPager.setVisibility(successful ? View.VISIBLE : View.GONE);
    }
}
