package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsCommentsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsPhotosAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.SliderCategoryItemDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.Database.TblFavorites;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnGlideImageListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.RateDialogService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.AppBarStateChangeListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.FileManger;
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
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
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
  TextView title, categoryTitle, rateCount, commentsCount, phoneNumber, address, description, descriptionHeader, instagram, telegram, txtNumber1, txtNumber2, txtNumber3, toolbarTitle;
  Button phoneCall, sendText, showDescription, addPhoto, showComments, btnAddComments;
  RecyclerView photoList, commentsList;
  ImageButton retry, favorite, btnShowMore;
  ImageView back, forward, goBack, imgCall;
  RatingBar ratingBar;
  ProgressBar loading;
  ViewGroup addRating, addComment, addPhotoLayout;

  CategoryItemDetailsViewModel selectedModel;
  TblFavorites tableFavorites;

  LinearLayoutManager commentsLayoutManager, userPhotosLayoutManager;
  MessageDialog uploading;

  CategoryItemDetailsPhotosAdapter photosAdapter;
  SliderCategoryItemDetailsAdapter sliderPhotosAdapter;
  CategoryItemDetailsCommentsAdapter commentsAdapter;

  LinearLayout instagramLayout, telegramLayout, noPhotos, noComments;
  AppBarLayout appBarLayout;

  String phoneCallBtn, phoneCall2, phoneCall3;

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
    imgCall = view.findViewById(R.id.img_call_detail_cityguide);
    toolbarTitle = view.findViewById(R.id.toolbar_text_view);
    noPhotos = view.findViewById(R.id.no_photos);
    noComments = view.findViewById(R.id.no_comments);

    txtNumber1 = view.findViewById(R.id.txtNumberCityGyide);
    txtNumber2 = view.findViewById(R.id.txtNumberCityGyide2);
    txtNumber3 = view.findViewById(R.id.txtNumberCityGyide3);

    phoneCall = view.findViewById(R.id.btnCategoryItemCall);
    sendText = view.findViewById(R.id.btnCategoryItemMessage);
    showDescription = view.findViewById(R.id.btnCategoryItemDetailsShowDescription);
    addPhoto = view.findViewById(R.id.btnCategoryItemDetailsAddPhotos);
    showComments = view.findViewById(R.id.btnCategoryDetailsShowComments);
    btnAddComments = view.findViewById(R.id.btnCategoryItemDetailsAddComment);
    instagramLayout = view.findViewById(R.id.instagram_layout);
    photoList = view.findViewById(R.id.recyclerCategoryItemPhotos);
    commentsList = view.findViewById(R.id.recyclerItemDetailsComments);
    commentsLayoutManager = new LinearLayoutManager(getContext());
    userPhotosLayoutManager = new LinearLayoutManager(getContext());
    appBarLayout = view.findViewById(R.id.appBarLayout);

    uploading = new MessageDialog(getString(R.string.uploading_photos), false);

    itemId = getArguments().getInt("ID");
    userId = new User(getContext()).GetUserId();
  }

  private void implement() {
    presenter.start(new User(getContext()).GetUserId(), itemId);

    hideSoftKeyBoard();

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
    goBack.setOnClickListener(this);
    instagram.setOnClickListener(this);
    telegram.setOnClickListener(this);
    phoneCall.setOnClickListener(this);
    sendText.setOnClickListener(this);
    txtNumber1.setOnClickListener(this);
    txtNumber2.setOnClickListener(this);
    txtNumber3.setOnClickListener(this);
    imgCall.setOnClickListener(this);

    userPhotosLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    userPhotosLayoutManager.setReverseLayout(true);
    commentsList.setLayoutManager(commentsLayoutManager);
    photoList.setLayoutManager(userPhotosLayoutManager);
    slider.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    slider.setPageTransformer(new ZoomOutPageTransformer());

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

  public void hideSoftKeyBoard() {
    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }

  private void changeToolbarItems(@ColorRes int color) {
    ImageViewCompat.setImageTintList(favorite, ColorStateList.valueOf(getResources().getColor(color)));
    ImageViewCompat.setImageTintList(goBack, ColorStateList.valueOf(getResources().getColor(color)));
    toolbarTitle.setTextColor(getResources().getColor(color));
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

        if (selectedModel.getInstagramId().isEmpty()) {
          instagramLayout.setVisibility(View.GONE);
        } else {
          try {
            Intent instagramIntent = new Intent(Intent.ACTION_VIEW);
            instagramIntent.setData(Uri.parse("https://www.instagram.com/" + selectedModel.getInstagramId()));
            startActivity(instagramIntent);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
        break;

      case R.id.txt_telegram:

        if (selectedModel.getTelegramId().isEmpty()) {
          telegramLayout.setVisibility(View.GONE);
        } else {
          try {
            Intent telegramIntent = new Intent(Intent.ACTION_VIEW);
            telegramIntent.setData(Uri.parse("https://telegram.me/" + selectedModel.getTelegramId()));
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
        break;

      case R.id.imgSliderRefresh:
        presenter.start(userId, itemId);
        break;

      case R.id.txtNumberCityGyide:
        Intent intentNum1 = new Intent(Intent.ACTION_DIAL);
        intentNum1.setData(Uri.parse("tel:" + phoneCallBtn));
        startActivity(intentNum1);
        break;

      case R.id.txtNumberCityGyide2:
        Intent intentNum2 = new Intent(Intent.ACTION_DIAL);
        intentNum2.setData(Uri.parse("tel:" + phoneCall2));
        startActivity(intentNum2);
        break;

      case R.id.txtNumberCityGyide3:
        Intent intentNum3 = new Intent(Intent.ACTION_DIAL);
        intentNum3.setData(Uri.parse("tel:" + phoneCall3));
        intentNum3.setData(Uri.parse("tel:" + phoneCall3));
        startActivity(intentNum3);
        break;

      case R.id.img_call_detail_cityguide:
        Intent intentNum4 = new Intent(Intent.ACTION_DIAL);
        intentNum4.setData(Uri.parse("tel:" + phoneCallBtn));
        startActivity(intentNum4);
        break;


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

    if (model.getCommentsModels().size() == 0)
      noComments.setVisibility(View.VISIBLE);

    if (model.getPhotoModels().size() == 0)
      noPhotos.setVisibility(View.VISIBLE);

    phoneCallBtn = model.getCellPhone();
    phoneCall2 = model.getTel1();
    phoneCall3 = model.getTel2();

    txtNumber1.setText(phoneCallBtn);
    txtNumber2.setText(phoneCall2);
    txtNumber3.setText(phoneCall3);


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


    retry.setVisibility(View.GONE);

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
  public void onImagesUploaded(ArrayList<FileManger.FileResponse> responses, JSONArray array) {
    presenter.sendUserPhotos(userId, array);
  }

  @Override
  public void onImagesUploading(boolean state) {
    if (state)
      uploading.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
    else uploading.dismiss();
  }

  @Override
  public void onUserPhotosUploaded(CategoryResultRatingViewModel model) {
    if (model.getStatus())
      Toasty.success(getContext(), model.getMessage()).show();
    else Toasty.error(getContext(), model.getMessage()).show();
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
        presenter.start(userId, itemId)));
  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onLoadState(boolean successful) {

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
