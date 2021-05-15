package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsCommentsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsPhotosAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.SliderCategoryItemDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database.TblFavorites;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnGlideImageListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.RateDialogService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.ZoomOutPageTransformer;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultRatingViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryItemDetailsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs.MessageDialog;
import ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs.RateDialog;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class CategoryItemDetailsFragmentCityGuide extends CityGuideBaseFragment implements OnGlideImageListener, CategoryItemDetailsFragmentService, RateDialogService, View.OnClickListener {

    public static final String TAG = "CategoryItemDetailsFragment";

    CategoryItemDetailsFragmentPresenter presenter;

    String descriptionString;
    int itemId, userId;

    CoordinatorLayout root;
    ViewPager2 slider;
    TextView title, categoryTitle, rateCount, commentsCount, phoneNumber, address, description, descriptionHeader, instagram, telegram;
    Button phoneCall, sendText, showDescription, addPhoto, showComments, btnAddComments;
    RecyclerView photoList, commentsList;
    ImageButton retry, favorite;
    ImageView back, forward, goBack;
    RatingBar ratingBar;
    ProgressBar loading;
    ViewGroup addRating, addComment, addPhotoLayout;

    CategoryItemDetailsViewModel selectedModel;
    TblFavorites tableFavorites;

    LinearLayoutManager commentsLayoutManager, userPhotosLayoutManager;

    CategoryItemDetailsPhotosAdapter photosAdapter;
    SliderCategoryItemDetailsAdapter sliderPhotosAdapter;
    CategoryItemDetailsCommentsAdapter commentsAdapter;

    LinearLayout instagramLayout,telegramLayout;

    String phoneCallBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_item_details, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View view) {
        presenter = new CategoryItemDetailsFragmentPresenter(getContext(), this, this);
        tableFavorites = new TblFavorites(getContext());
        root = view.findViewById(R.id.categoryItemDetailsRootLayout);
        slider = view.findViewById(R.id.sliderCategoryItemDetails);
        retry = view.findViewById(R.id.imgSliderRefresh);
        favorite = view.findViewById(R.id.imgItemDetailsToggleFavorite);
        back = view.findViewById(R.id.imgSliderCategoryBack);
        forward = view.findViewById(R.id.imgSliderCategoryForward);
        title = view.findViewById(R.id.txtCategoryItemDetailsTitle);
        categoryTitle = view.findViewById(R.id.txtCategoryItemDetailsCategoryTitle);
        rateCount = view.findViewById(R.id.txtCategoryItemRateCount);
        commentsCount = view.findViewById(R.id.txtCategoryItemDetailsCommentCount);
        ratingBar = view.findViewById(R.id.categoryItemDetailsRatingBar);
        phoneNumber = view.findViewById(R.id.txtCategoryItemPhone);
        address = view.findViewById(R.id.txtCategoryItemDetailsAddress);
        description = view.findViewById(R.id.txtCategoryItemDetailsDescription);
        descriptionHeader = view.findViewById(R.id.txtCategoryItemDetailsDescriptionTitle);
        loading = view.findViewById(R.id.categoryItemDetailsLoading);
        addRating = view.findViewById(R.id.categoryItemDetailsAddRating);
        addComment = view.findViewById(R.id.categoryItemDetailsAddComment);
        addPhotoLayout = view.findViewById(R.id.categoryItemDetailsAddPhoto);
        goBack = view.findViewById(R.id.img_btn_back_detail);
        instagram = view.findViewById(R.id.txt_instagram);
        telegram = view.findViewById(R.id.txt_telegram);


        phoneCall = view.findViewById(R.id.btnCategoryItemCall);
        sendText = view.findViewById(R.id.btnCategoryItemMessage);
        showDescription = view.findViewById(R.id.btnCategoryItemDetailsShowDescription);
        addPhoto = view.findViewById(R.id.btnCategoryItemDetailsAddPhotos);
        showComments = view.findViewById(R.id.btnCategoryDetailsShowComments);
        btnAddComments = view.findViewById(R.id.btnCategoryItemDetailsAddComment);

        instagramLayout = view.findViewById(R.id.instagram_layout);
        telegramLayout = view.findViewById(R.id.telegramLayout);


        photoList = view.findViewById(R.id.recyclerCategoryItemPhotos);
        commentsList = view.findViewById(R.id.recyclerItemDetailsComments);

        commentsLayoutManager = new LinearLayoutManager(getContext());
        userPhotosLayoutManager = new LinearLayoutManager(getContext());


    }

    private void implement() {
        itemId = getArguments().getInt("ID");
        userId = new User(getContext()).GetUserId();
        presenter.start(new User(getContext()).GetUserId(), itemId);
        phoneCall.setOnClickListener(this);
        sendText.setOnClickListener(this);
        showDescription.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        showComments.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        addRating.setOnClickListener(this);
        addComment.setOnClickListener(this);
        addPhotoLayout.setOnClickListener(this);
        retry.setOnClickListener(this);
        favorite.setOnClickListener(this);
        btnAddComments.setOnClickListener(this);
        goBack.setOnClickListener(this::onClick);
        instagram.setOnClickListener(this);
        telegram.setOnClickListener(this);
        phoneCall.setOnClickListener(this);
        sendText.setOnClickListener(this);

        userPhotosLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        userPhotosLayoutManager.setReverseLayout(true);

        commentsList.setLayoutManager(commentsLayoutManager);
        photoList.setLayoutManager(userPhotosLayoutManager);

        slider.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        slider.setPageTransformer(new ZoomOutPageTransformer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCategoryDetailsShowComments:
                goToComments();
                break;
            case R.id.categoryItemDetailsAddComment:
                goToComments();
                break;
            case R.id.btnCategoryItemDetailsAddComment:
                goToComments();
                break;
            case R.id.categoryItemDetailsAddPhoto:
                if (new User(getContext()).GetUserId() == 0) {
                    Toasty.error(getContext(), getString(R.string.createAccountFirst)).show();
                } else {
                    presenter.requestStoragePermission(getActivity());
                }
                break;
            case R.id.btnCategoryItemDetailsShowDescription:
                TransitionManager.beginDelayedTransition(root);
                description.setText(descriptionString);
                showDescription.setVisibility(View.GONE);
                break;
            case R.id.imgItemDetailsToggleFavorite:
                if (tableFavorites.checkState(itemId)) {
                    tableFavorites.deleteFavorite(itemId);
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                } else {
                    tableFavorites.addFavorite(itemId);
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                break;
            case R.id.imgSliderCategoryBack:
                try {
                    slider.setCurrentItem(slider.getCurrentItem() - 1, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.imgSliderCategoryForward:
                try {
                    slider.setCurrentItem(slider.getCurrentItem() + 1, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.categoryItemDetailsAddRating:
                RateDialog dialog = new RateDialog(this);
                dialog.show(getActivity().getSupportFragmentManager(), RateDialog.TAG);
                break;
            case R.id.btnCategoryItemDetailsAddPhotos:
                addPhotoLayout.performClick();
                break;

            case R.id.img_btn_back_detail:
                getActivity().onBackPressed();
                break;

            case R.id.txt_instagram:

                if (selectedModel.getInstagramId().isEmpty()){
                    instagramLayout.setVisibility(View.GONE);

                }else {
                    try {
                        Intent instagramIntent = new Intent(Intent.ACTION_VIEW);
                        instagramIntent.setData(Uri.parse("https://www.instagram.com/"+selectedModel.getInstagramId()));
                        startActivity(instagramIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.txt_telegram:

                if (selectedModel.getTelegramId().isEmpty()){
                    telegramLayout.setVisibility(View.GONE);
                }else{
                    try {
                        Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
                        telegramIntent.setData(Uri.parse("https://telegram.me/"+selectedModel.getTelegramId()));
                        startActivity(telegramIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.btnCategoryItemCall:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneCallBtn));
                startActivity(intent);
                break;

            case R.id.btnCategoryItemMessage:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms: " + phoneCallBtn));
                sendIntent.putExtra("sms_body", "");
                startActivity(sendIntent);

        }
    }

    private void goToComments() {
        CategoryItemCommentsFragmentCityGuide fragment = new CategoryItemCommentsFragmentCityGuide();
        Bundle bundle = new Bundle();
        bundle.putInt("ItemId", itemId);
        bundle.putInt("UserId", userId);
        fragment.setArguments(bundle);
        ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryItemCommentsFragmentCityGuide.TAG);
    }

    @Override
    public void onPresenterStart() {

    }

    @Override
    public void loadingState(boolean state) {
        loading.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDetailsReceived(CategoryItemDetailsViewModel model) {
        selectedModel = model;
        title.setText(model.getTitle());
        categoryTitle.setText(model.getCategoryTitle());
        rateCount.setText(model.getRateCount() + " رای");
        if (model.getCommentsModels() != null) {
            commentsCount.setText(model.getCommentsModels().size() + " نظر");
        }
        phoneNumber.setText(model.getCellPhone());
        if (model.getTel1() != null) {
            phoneNumber.append("\n" + model.getTel1());
        }
        if (model.getTel2() != null) {
            phoneNumber.append("\n" + model.getTel2());
        }
        address.setText(model.getAddress());
        descriptionHeader.setText("درباره " + model.getTitle());
        descriptionString = model.getDescription();
        if (descriptionString.length() > 300) {
            showDescription.setVisibility(View.VISIBLE);
            String sub = descriptionString.substring(0, 300);
            description.setText(sub);
        } else {
            showDescription.setVisibility(View.GONE);
            description.setText(descriptionString);
        }
        ratingBar.setRating(model.getStarCount());
        int resId = tableFavorites.checkState(itemId) ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
        favorite.setImageResource(resId);

        sliderPhotosAdapter = new SliderCategoryItemDetailsAdapter(getContext(), model.getSliderModels(), this);

        if (model.getPhotoModels() != null) {
            photosAdapter = new CategoryItemDetailsPhotosAdapter(model.getPhotoModels(), new OnItemClick() {
                @Override
                public void onClick(View view, int position) {
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            });
        }

        commentsAdapter = new CategoryItemDetailsCommentsAdapter(model.getCommentsModels(), this);
        commentsList.setAdapter(commentsAdapter);
        photoList.setAdapter(photosAdapter);
        slider.setAdapter(sliderPhotosAdapter);

        phoneCallBtn = model.getCellPhone();

    }

    @Override
    public void onStoragePermissionGranted() {
        presenter.openImageSelector(getActivity(), itemId);
    }

    @Override
    public void onStoragePermissionDenied() {
        MessageDialog dialog = new MessageDialog(getString(R.string.allow_storage_permission), true);
        dialog.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
    }

    @Override
    public void onImagesUploaded(JSONArray array) {

        PostJsonArrayVolley postJsonArrayVolley = new PostJsonArrayVolley(CityGuideBaseApi.API_URL +
                "CityGuide/AddUserImageCityGuide?UserId=" + userId, array, resault -> {

            if (resault.getResault() == ResaultCode.Success) {
                CategoryResultRatingViewModel resultModel = new CategoryResultRatingViewModel();
                try {
                    JSONObject object = resault.getJsonArray().getJSONObject(0);
                    resultModel.setStatus(object.getBoolean("Status"));
                    resultModel.setMessage(object.getString("Titel"));
                    resultModel.setCode(object.getInt("Code"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toasty.success(getContext(), resultModel.getMessage()).show();
            } else {
                Toasty.error(getContext(), getContext().getString(R.string.imageUploadingFailed)).show();
            }


        });
    }

    @Override
    public void onCommentLiked(int position, CategoryItemDetailsCommentsModel model, boolean liked) {
        JSONObject object = new JSONObject();
        PostJsonObjectVolley volley;
        try {
            object.put("UserId", new User(getContext()).GetUserId());
            object.put("CommentId", model.getId());
            object.put("IsLiked", liked);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        volley = new PostJsonObjectVolley(CityGuideBaseApi.API_URL + "CityGuide/LikeComment", object, resault -> {
            if (resault.getResault() == ResaultCode.Success) {
                JSONObject resultObject = resault.getObject();
                ResultViewModel viewModel = new ResultViewModel();
                try {
                    viewModel.setTitle(resultObject.getString("Titel"));
                    viewModel.setCode(resultObject.getInt("Code"));
                    viewModel.setStatus(resultObject.getBoolean("Status"));
                    if (viewModel.getStatus()) {
                        if (liked) {
                            commentsAdapter.increaseLike(position, liked);
                        } else {
                            commentsAdapter.increaseLike(position, liked);
                        }
                        Toasty.success(getContext(), viewModel.getTitle()).show();
                    } else {
                        Toasty.error(getContext(), viewModel.getTitle()).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onLoadState(boolean successful) {
        retry.setVisibility(successful ? View.GONE : View.VISIBLE);
        slider.setVisibility(successful ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRate(int rate) {
        presenter.rate(userId, itemId, rate);
    }

    @Override
    public void onRateSendingState(boolean state, String message) {
        if (state) {
            Toasty.success(getContext(), message).show();
        } else {
            Toasty.error(getContext(), message).show();
        }
    }
}
