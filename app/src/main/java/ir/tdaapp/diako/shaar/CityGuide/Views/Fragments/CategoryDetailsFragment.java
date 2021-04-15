package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsChipsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryChipClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.PaginationListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryDetailsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;
import pl.droidsonroids.gif.GifImageView;

import static ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.PaginationListener.PAGE_START;

public class CategoryDetailsFragment extends BaseFragment implements CategoryDetailsFragmentService {

  public static final String TAG = "CategoryDetailsFragment";

  private int currentPage = PAGE_START;
  private boolean isLastPage = false;
  private int totalPage = 10;
  private boolean isLoading = false;
  int itemCount = 0;

  CategoryDetailsFragmentPresenter presenter;

  CategoryDetailsChipModel selectedModel;

  ImageButton filter, back;
  RecyclerView chipsList, detailsList;
  EditText searchBar;
  GifImageView loading;

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

    filter = view.findViewById(R.id.imgCategoryDetailsFilter);
    back = view.findViewById(R.id.imgCategoryDetailsBack);
    chipsList = view.findViewById(R.id.categoryDetailsChipsList);
    detailsList = view.findViewById(R.id.categoryDetailsList);
    searchBar = view.findViewById(R.id.edtCategoryDetailsSearch);
    loading=view.findViewById(R.id.loadingCategoryDetails);
  }

  private void implement() {
    detailsList.setLayoutManager(detailsLayoutManager);
    chipsList.setLayoutManager(chipsLayoutManager);
    presenter.start(getArguments().getInt("ID"));
  }

  @Override
  public void onPresenterStart() {
    chipsAdapter = new CategoryDetailsChipsAdapter(getContext());
    chipsList.setAdapter(chipsAdapter);
    detailsAdapter = new CategoryDetailsAdapter(getContext());
    detailsList.setAdapter(detailsAdapter);

    setPagination();

    chipsAdapter.setOnItemClick(model -> {
      detailsAdapter.clear();
      selectedModel = model;
      presenter.getItemByFilter(model.getId(), 0);
    });
    detailsAdapter.setOnItemClick(new onCategoryItemClick() {
      @Override
      public void onClick(CategoryDetailsModel model) {

      }

      @Override
      public void onFavorite(CategoryDetailsModel model) {

      }
    });
  }

  private void setPagination() {
    detailsList.addOnScrollListener(new PaginationListener(detailsLayoutManager) {
      @Override
      protected void loadMoreItems() {
        isLoading = true;
        currentPage++;
        detailsAdapter.addLoading();
        presenter.getItemByFilter(3, currentPage);
      }

      @Override
      public boolean isLastPage() {
        detailsAdapter.removeLoading();
        return isLastPage;
      }

      @Override
      public void removeLoading() {

      }

      @Override
      public boolean isLoading() {
        return isLoading;
      }
    });
  }

  @Override
  public void onItemsReceived(CategoryDetailsModel model) {
    if (currentPage != PAGE_START) detailsAdapter.removeLoading();
    detailsAdapter.add(model);
    // check weather is last page or not
    if (currentPage < totalPage) {
      detailsAdapter.addLoading();
    } else {
      isLastPage = true;
    }
    isLoading = false;
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
  public void onLoadingPaging(boolean load) {

  }

  @Override
  public void onFinish() {
    chipsList.setVisibility(View.VISIBLE);
    detailsList.setVisibility(View.VISIBLE);
  }

  @Override
  public void onError(String result) {

  }
}
