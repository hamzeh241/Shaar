package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsCommentsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemCommentsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryItemCommentsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs.MessageDialog;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;
import ir.tdaapp.diako.shaar.Volley.Volleys.PostJsonObjectVolley;

public class CategoryItemCommentsFragmentCityGuide extends CityGuideBaseFragment implements CategoryItemCommentsFragmentService, View.OnClickListener {

  public static final String TAG = "CategoryItemCommentsFragment";

  CategoryItemCommentsFragmentPresenter presenter;

  int userId, itemId;
  RecyclerView list;
  ProgressBar loading;
  ImageButton post;
  EditText commentText;
  ImageButton back;

  MessageDialog dialog;

  CategoryItemDetailsCommentsAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_item_comments, container, false);

    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    findView(view);
    implement();
    return view;
  }

  private void findView(View view) {
    presenter = new CategoryItemCommentsFragmentPresenter(getContext(), this);

    list = view.findViewById(R.id.recyclerCategoryItemAllComments);
    loading = view.findViewById(R.id.progressAllComments);
    post = view.findViewById(R.id.imgPostComment);
    commentText = view.findViewById(R.id.edtCommentText);
    back = view.findViewById(R.id.imageButton);

    dialog = new MessageDialog(getString(R.string.postingComment), false);

    userId = getArguments().getInt("UserId");
    itemId = getArguments().getInt("ItemId");
  }

  private void implement() {
    presenter.start(itemId, userId);

    post.setOnClickListener(this);
    back.setOnClickListener(this::onClick);

    list.setLayoutManager(new LinearLayoutManager(getContext()));
    dialog.setCancelable(false);
  }

  @Override
  public void onPresenterStart() {
    adapter = new CategoryItemDetailsCommentsAdapter(getContext());
    adapter.setCommentsFragmentService(this);
    list.setAdapter(adapter);
  }

  @Override
  public void onCommentsReceived(CategoryItemDetailsCommentsModel model) {
    Log.i("LOG", model.getMessage());
    adapter.add(model);
  }

  @Override
  public void onLoading(boolean state) {
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
    list.setVisibility(state ? View.GONE : View.VISIBLE);
  }

  @Override
  public void onCommentPostFinished(ResultViewModel model) {
    if (model.getStatus()) {
      Toasty.success(getContext(), getString(R.string.commentPostedSuccessfully)).show();
      presenter.start(itemId, userId);
    } else {
      Toasty.error(getContext(), "خطا در ارتباط" + model.getCode()).show();
    }
  }

  @Override
  public void onCommentPostLoading(boolean state) {
    if (state) {
      dialog.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
    } else {
      dialog.dismiss();
    }
  }

  @Override
  public void onLikeState(int position, CategoryItemDetailsCommentsModel model, boolean liked) {
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
              adapter.increaseLike(position, liked);
            }else {
              adapter.increaseLike(position, liked);
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

    switch (resaultCode) {
      case TimeoutError:
        error = getString(R.string.timeout_error);
        title = getString(R.string.timeout_error_title);
        break;
      case NetworkError:
        error = getString(R.string.network_error);
        title = getString(R.string.network_error_title);
        break;
      case ServerError:
        error = getString(R.string.server_error);
        title = getString(R.string.server_error_title);
        break;
      case ParseError:
      case Error:
        title = getString(R.string.unknown_error_title);
        error = getString(R.string.unknown_error);
        break;
    }
    showErrorDialog(title, error, () -> {
      presenter.start(userId,itemId);

    });

  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.imgPostComment:
        if (new User(getContext()).GetUserId() == 0) {
          Toasty.error(getContext(), getString(R.string.createAccountFirst)).show();
        } else {
          if (commentText.getText().toString() != "") {
            presenter.sendComment(commentText.getText().toString(), itemId, userId);
          }
        }
        break;

      case R.id.imageButton:
        getActivity().onBackPressed();
        break;
    }
  }
}
