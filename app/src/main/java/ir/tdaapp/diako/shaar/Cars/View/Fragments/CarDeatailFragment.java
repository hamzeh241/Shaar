package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.SliderCarItemDetails;
import ir.tdaapp.diako.shaar.Cars.Model.Repository.database.TblCarFavoriets;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarDetailFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailsPhotoModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarDetailFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnGlideImageListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.ZoomOutPageTransformer;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;

import static ir.tdaapp.diako.shaar.Interface.IBase.ApiImage;

public class CarDeatailFragment extends CarBaseFragment implements OnGlideImageListener, CarDetailFragmentService, View.OnClickListener {

    public static final String TAG = "CarDeatailFragment";

    int itemId, userId;

    TextView carName, productionYear, mileage, carBodyStatus, carBodyStatus2, brand,
            chasisStatus, insuranceTime, gearBox, ducument, salesType, price, description,
            expertName, txtExchange, address;

    CircleImageView circleImageView;
    ImageButton favorite, refresh, btnBack;
    ImageView back, forward;
    ShimmerFrameLayout loading;
    Button textExpert, callExpert, showMoreDescribe;

    CoordinatorLayout root;
    ViewPager2 viewPager;

    String descriptionText, expertPhone;
    TblCarFavoriets tblCarFavoriets;
    CarDetailFragmentPresenter presenter;

    SliderCarItemDetails viewPagerAdapter;


    CarDetailModel model = new CarDetailModel();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_details_fragment, container, false);

        findView(view);
        implement();

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

        //other TextViews
        description = view.findViewById(R.id.txt_car_description);
        expertName = view.findViewById(R.id.txt_expert_name);

        //ImageViews
        circleImageView = view.findViewById(R.id.circle_imageview_expert);
        favorite = view.findViewById(R.id.img_faverite_detail);
        back = view.findViewById(R.id.img_back_viewPager);
        forward = view.findViewById(R.id.img_forward_viewPager);

        //Buttons
        showMoreDescribe = view.findViewById(R.id.btn_show_more_describe);
        callExpert = view.findViewById(R.id.btn_call_expert);
        textExpert = view.findViewById(R.id.btn_text_expert);
        refresh = view.findViewById(R.id.imgBtn_refresh_car);
        btnBack = view.findViewById(R.id.imageButton_details_car);

        loading = view.findViewById(R.id.viewPager_loading);
        root = view.findViewById(R.id.car_detail_root_layout);
        viewPager = view.findViewById(R.id.viewPager_car_detail);
        tblCarFavoriets = new TblCarFavoriets(getContext());


        CarDetailModel model = new CarDetailModel();
        expertPhone = model.getPhone();


    }

    private void implement() {
        //id to item id
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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_call_expert:
                dialContactPhone(expertPhone + "");
                break;

            case R.id.btn_text_expert:
                sendTextToExpert();
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
        }
    }

    //تماس با کارشناس
    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    //ارسال پیام به کارشناس
    private void sendTextToExpert() {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + model.getPhone()));
        sendIntent.putExtra("sms_body", "this is a test message");
        startActivity(sendIntent);
    }

    @Override
    public void onCarReceived(CarDetailModel model) {
        int resId = tblCarFavoriets.checkState(itemId) ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
        favorite.setImageResource(resId);

        carName.setText(model.getName());
        productionYear.setText(model.getProductionYear());
        mileage.setText(model.getMileage());
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

        if (model.isExchange()) {
            txtExchange.setText(R.string.exchange);
        } else {
            txtExchange.setText(R.string.notExchange);
        }

        //ست کردن عکس کارشناس
        String ImageUrl = CarBaseApi.API_IMAGE_CAR_EXPERT + "ImageSave/" + model.getExpertImage();
        Glide.with(getContext()).load(ImageUrl).into(circleImageView);

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


    }

    @Override
    public void onPresenterStart() {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void loadingState(boolean load) {
        if (load) {
            loading.startShimmer();
            refresh.setVisibility(View.GONE);
        } else {
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
