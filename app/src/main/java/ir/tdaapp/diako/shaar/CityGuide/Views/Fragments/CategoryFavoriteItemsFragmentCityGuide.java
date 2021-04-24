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
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFavoriteItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryFavoriteItemsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;

public class CategoryFavoriteItemsFragmentCityGuide extends CityGuideBaseFragment implements View.OnClickListener, CategoryFavoriteItemsFragmentService {

  public static final String TAG = "CategoryFavoriteItemsFragment";

  CategoryFavoriteItemsFragmentPresenter presenter;

  CategoryDetailsAdapter adapter;

  RecyclerView list;
  ProgressBar loading;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_favorite_items, container, false);

    findView(view);
    implement();

    presenter.start();

    return view;
  }

  private void findView(View view) {
    presenter = new CategoryFavoriteItemsFragmentPresenter(getContext(), this);
    adapter = new CategoryDetailsAdapter(getContext());

    list = view.findViewById(R.id.favoriteItemsList);
    loading = view.findViewById(R.id.favoritesLoading);
  }

  private void implement() {
    list.setLayoutManager(new LinearLayoutManager(getContext()));

    adapter.setOnItemClick(model -> {
      CategoryItemDetailsFragmentCityGuide fragment = new CategoryItemDetailsFragmentCityGuide();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);
      ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryItemDetailsFragmentCityGuide.TAG);
    });
  }


  public void onClick(View v) {

  }

  @Override
  public void loadingState(boolean state) {
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
    list.setVisibility(state ? View.GONE : View.VISIBLE);
  }

  @Override
  public void onItemReceived(CategoryDetailsModel model) {
    adapter.add(model);
    list.setAdapter(adapter);
  }

  @Override
  public void onPresenterStart() {
  }

  @Override
  public void onError(String message) {

  }

  @Override
  public void onFinish() {

  }
}
