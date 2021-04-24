package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;

public class CategoryFragmentCityGuide extends CityGuideBaseFragment implements CategoryFragmentService, View.OnClickListener {

  public static final String TAG = "CategoryFragment";

  private RecyclerView recyclerView;

  private GridLayoutManager layoutManager;
  private CategoryAdapter adapter;
  private CategoryFragmentPresenter presenter;
  private ProgressBar loading;
  private ViewGroup searchBar;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View itemView) {
    recyclerView = itemView.findViewById(R.id.categoryList);
    loading = itemView.findViewById(R.id.loadingCategory);
    searchBar = itemView.findViewById(R.id.categorySearchBar);
    layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
    presenter = new CategoryFragmentPresenter(getContext(), this);
  }

  private void implement() {
    presenter.start();
    recyclerView.setVisibility(View.GONE);
    loading.setVisibility(View.VISIBLE);

    searchBar.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.categorySearchBar:
        CategoryDetailsFragmentCityGuide fragment = new CategoryDetailsFragmentCityGuide();
        Bundle bundle = new Bundle();
        bundle.putInt("ID", 0);
        fragment.setArguments(bundle);

        ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryDetailsFragmentCityGuide.TAG);

        break;
    }
  }

  @Override
  public void onPresenterStart() {
    adapter = new CategoryAdapter(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    adapter.setOnItemClick(model -> {

      CategoryDetailsFragmentCityGuide fragment = new CategoryDetailsFragmentCityGuide();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);

      ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryDetailsFragmentCityGuide.TAG);
    });
  }

  @Override
  public void onItemsReceived(CategoryModel model) {
    adapter.add(model);
    recyclerView.setVisibility(View.VISIBLE);
    loading.setVisibility(View.GONE);
  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onError(String result) {

  }

}
