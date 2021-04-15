package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Services.IGetJsonArray;
import ir.tdaapp.diako.shaar.Volley.ViewModel.ResaultGetJsonArrayVolley;
import ir.tdaapp.diako.shaar.Volley.Volleys.GetJsonArrayVolley;
import pl.droidsonroids.gif.GifImageView;

public class CategoryFragment extends BaseFragment implements CategoryFragmentService, View.OnClickListener {

  public static final String TAG = "CategoryFragment";

  private RecyclerView recyclerView;

  private GridLayoutManager layoutManager;
  private CategoryAdapter adapter;
  private CategoryFragmentPresenter presenter;
  private GifImageView loading;


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
    layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
    presenter = new CategoryFragmentPresenter(getContext(), this);
  }

  private void implement() {
    presenter.start();
    recyclerView.setVisibility(View.GONE);
    loading.setVisibility(View.VISIBLE);
  }

  @Override
  public void onClick(View v) {

  }

  @Override
  public void onPresenterStart() {
    adapter = new CategoryAdapter(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    adapter.setOnItemClick(model -> {

      CategoryDetailsFragment fragment = new CategoryDetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);

      ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryDetailsFragment.TAG);
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
