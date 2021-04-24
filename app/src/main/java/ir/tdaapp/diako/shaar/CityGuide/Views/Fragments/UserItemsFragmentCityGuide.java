package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.UserItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.UserItemsFragmentPresenter;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;

public class UserItemsFragmentCityGuide extends CityGuideBaseFragment implements UserItemsFragmentService {

  public static final String TAG = "UserItemsFragment";

  UserItemsFragmentPresenter presenter;

  RecyclerView list;
  ProgressBar loading;

  LinearLayoutManager layoutManager;
  CategoryDetailsAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_items, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    presenter = new UserItemsFragmentPresenter(getContext(), this);
    layoutManager = new LinearLayoutManager(getContext());
    adapter = new CategoryDetailsAdapter(getContext());

    list = view.findViewById(R.id.userItemList);
    loading = view.findViewById(R.id.userItemLoading);
  }

  private void implement() {
    presenter.start(new User(getContext()).GetUserId());

    list.setLayoutManager(layoutManager);
    list.setAdapter(adapter);
  }

  @Override
  public void loadingState(boolean state) {
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onItemReceived(CategoryDetailsModel model) {
    adapter.add(model);
  }

  @Override
  public void onError(String s) {

  }

  @Override
  public void onFinished() {

  }
}
