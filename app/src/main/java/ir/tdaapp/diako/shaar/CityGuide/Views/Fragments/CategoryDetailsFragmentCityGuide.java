package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsChipsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryDetailsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;


public class CategoryDetailsFragmentCityGuide extends CityGuideBaseFragment implements CategoryDetailsFragmentService, View.OnClickListener {

  public static final String TAG = "CategoryDetailsFragment";

  private int previousTotal = 0;
  private int page = 0;
  private boolean isLoading = true;
  private int visibleThreshold = 5;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  CategoryDetailsFragmentPresenter presenter;

  CategoryDetailsChipModel selectedModel;

  ImageButton filter, back;
  RecyclerView chipsList, detailsList;
  EditText searchBar;
  ProgressBar loading;
  RelativeLayout root;
  FloatingActionButton fab;

  CategoryDetailsAdapter detailsAdapter;
  CategoryDetailsChipsAdapter chipsAdapter;
  ArrayList<CategoryDetailsModel> detailModels;
  ArrayList<CategoryDetailsChipModel> chipModels;
  LinearLayoutManager detailsLayoutManager, chipsLayoutManager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_details, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    presenter = new CategoryDetailsFragmentPresenter(getContext(), this);
    detailsLayoutManager = new LinearLayoutManager(getContext());
    chipsLayoutManager = new LinearLayoutManager(getContext());
    chipsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    chipsLayoutManager.setReverseLayout(true);

    detailModels = new ArrayList<>();
    chipModels = new ArrayList<>();

    root = view.findViewById(R.id.categoryDetailsRootLayout);
    filter = view.findViewById(R.id.imgCategoryDetailsFilter);
    back = view.findViewById(R.id.imgCategoryDetailsBack);
    chipsList = view.findViewById(R.id.categoryDetailsChipsList);
    detailsList = view.findViewById(R.id.categoryDetailsList);
    searchBar = view.findViewById(R.id.edtCategoryDetailsSearch);
    loading = view.findViewById(R.id.loadingCategoryDetails);
    fab = view.findViewById(R.id.categoryDetailsAddItemFab);
  }

  private void implement() {
    detailsList.setLayoutManager(detailsLayoutManager);
    chipsList.setLayoutManager(chipsLayoutManager);
    presenter.start(getArguments().getInt("ID"));

    root.setOnClickListener(this);
    searchBar.setOnKeyListener((v, keyCode, event) -> {
      // If the event is a key-down event on the "enter" button
      if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
        (keyCode == KeyEvent.KEYCODE_ENTER)) {
        // Perform action on key press
        filter.performClick();
        return true;
      }
      return false;
    });

    fab.setOnClickListener(this);
    filter.setOnClickListener(this);
    back.setOnClickListener(this::onClick);
  }

  @Override
  public void onPresenterStart() {
    initializeChipsAdapter();
    initializeDetailsAdapter();
    setPagination();
  }

  @Override
  public void onPresenterRestart() {
    initializeDetailsAdapter();
  }

  private void initializeChipsAdapter() {
    chipsAdapter = new CategoryDetailsChipsAdapter(getContext());
    chipsList.setAdapter(chipsAdapter);

    chipsAdapter.setOnItemClick(model -> {
      detailsAdapter.clear();
      selectedModel = model;
      presenter.getItemByFilter(searchBar.getText().toString(), model.getId(), page);
    });
  }

  private void initializeDetailsAdapter() {
    detailsAdapter = new CategoryDetailsAdapter(getContext());
    detailsList.setAdapter(detailsAdapter);
    detailsAdapter.setOnItemClick(model -> {
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      CategoryItemDetailsFragmentCityGuide fragment = new CategoryItemDetailsFragmentCityGuide();
      fragment.setArguments(bundle);
      ((GuideActivity) getActivity()).onAddFragment(fragment,
        0, 0, true, CategoryItemDetailsFragmentCityGuide.TAG);
    });
  }

  private void setPagination() {
    detailsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = detailsList.getChildCount();
        totalItemCount = detailsLayoutManager.getItemCount();
        firstVisibleItem = detailsLayoutManager.findFirstVisibleItemPosition();

        if (isLoading) {
          if (totalItemCount > previousTotal) {
            isLoading = false;
            previousTotal = totalItemCount;
          }
        }
        if (!isLoading && (totalItemCount - visibleItemCount)
          <= (firstVisibleItem + visibleThreshold)) {
          // End has been reached
          page++;
          presenter.getItemByFilter(searchBar.getText().toString(), selectedModel != null ? selectedModel.getId() : 1, page);
          // Do something

          isLoading = true;
        }
      }
    });
  }

  @Override
  public void onItemsReceived(CategoryDetailsModel model) {
    detailsAdapter.add(model);
  }

  @Override
  public void onChipsReceived(CategoryDetailsChipModel model) {
    chipsAdapter.add(model);
  }

  @Override
  public void loadingState(boolean load) {
    loading.setVisibility(load ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onPageFinished(List<CategoryDetailsModel> categoryDetailsModels) {
//    if (currentPage != PAGE_START) detailsAdapter.removeLoading();
//    detailsAdapter.addAll(detailModels);
//    // check weather is last page or not
//    if (currentPage < totalPage) {
//      detailsAdapter.addLoading();
//    } else {
//      isLastPage = true;
//    }
//    isLoading = false;
  }

  @Override
  public void onFinish() {
    if (chipsAdapter.getItemCount() == 0) {
      chipsList.setVisibility(View.GONE);
    } else {
      chipsList.setVisibility(View.VISIBLE);
      selectedModel = chipsAdapter.getItemAt(0);
    }
    detailsList.setVisibility(View.VISIBLE);
  }

  @Override
  public void onError(String result) {

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.categoryDetailsAddItemFab:
        ((GuideActivity) getActivity()).onAddFragment(new AddItemFragmentCityGuide(), 0, 0, true, AddItemFragmentCityGuide.TAG);
        break;
      case R.id.imgCategoryDetailsFilter:
        presenter.start(searchBar.getText().toString(), selectedModel.getId(), 0);
        Log.i("LOGLOG", searchBar.getText().toString() + " - " + selectedModel.getId() + " - " + page);
        break;

      case R.id.imgCategoryDetailsBack:
        getActivity().onBackPressed();
        break;
    }
  }
}
