package ir.tdaapp.diako.shaar.Fruits.View.Fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.ChipsListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.SearchModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.Fruits.Model.Adapters.CommonFruitsAdapter;
import ir.tdaapp.diako.shaar.Fruits.Model.Adapters.FruitsListAdapter;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database.TblCart;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.FruitsListService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Presenter.FruitsListPresenter;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;
import ir.tdaapp.diako.shaar.Fruits.View.Dialogs.FruitDetailsDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class FruitsListFragment extends BaseFragment implements View.OnClickListener, FruitsListService {

  public static final String TAG = "FruitsListFragment";

  private int previousTotal = 0;
  private int page = 0;
  private boolean isLoading = true;
  private final int visibleThreshold = 5;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  RecyclerView chipsList, newFruitsList, bestSellingList, allList;
  EditText searchBar;
  ImageButton filter, search, back;
  FloatingActionButton cart;

  LinearLayoutManager chipsManager, mostSoldManager, newFruitsManager, allFruitsManager;
  FruitsListPresenter presenter;

  ChipsListAdapter chipsAdapter;
  FruitsListAdapter mostSoldAdapter, newFruitsAdapter;
  CommonFruitsAdapter allAdapter;
  ShimmerFrameLayout shimmer;
  ScrollView fruitScrollView;

  SearchModel searchModel;

  TblCart tblCart;

  View includeMostSold, includeNewFruits, includeAllFruits;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_fruits_list, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(@NonNull View itemView) {
    chipsList = itemView.findViewById(R.id.fruitChipsList);
    newFruitsList = itemView.findViewById(R.id.newFruitsList);
    searchBar = itemView.findViewById(R.id.edtCarListSearch);
    filter = itemView.findViewById(R.id.imgCarListFilter);
    search = itemView.findViewById(R.id.btnCarListSearch);
    back = itemView.findViewById(R.id.imgCarListBack);
    cart = itemView.findViewById(R.id.fabCart);
    bestSellingList = itemView.findViewById(R.id.mostSaledList);
    allList = itemView.findViewById(R.id.allFruitsList);
    shimmer = itemView.findViewById(R.id.shimmer);
    fruitScrollView = itemView.findViewById(R.id.fruitListScrollView);

    includeAllFruits = itemView.findViewById(R.id.includeNoItemAllFruits);
    includeNewFruits = itemView.findViewById(R.id.includeNoItemNewFruits);
    includeMostSold = itemView.findViewById(R.id.includeNoItemMostSold);

    chipsManager = new LinearLayoutManager(getContext());
    newFruitsManager = new LinearLayoutManager(getContext());
    mostSoldManager = new LinearLayoutManager(getContext());
    allFruitsManager = new LinearLayoutManager(getContext());

    chipsAdapter = new ChipsListAdapter(requireContext());

    searchModel = new SearchModel();
    tblCart = new TblCart(requireContext());

    presenter = new FruitsListPresenter(getContext(), this);
  }

  private void implement() {
    presenter.start();

    searchBar.setOnKeyListener((v, keyCode, event) -> {
      // If the event is a key-down event on the "enter" button
      if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
        (keyCode == KeyEvent.KEYCODE_ENTER)) {
        // Perform action on key press
        search.performClick();
        return true;
      }
      return false;
    });

    chipsAdapter.setChipListener((model, position) -> {
      if (model.isSelected()) {
        mostSoldAdapter.clear();
        newFruitsAdapter.clear();
        allAdapter.clear();
        chipsAdapter.clearSelected();
        searchModel.setCategoryId(0);
        presenter.getSpecificFruit(searchModel);
      } else {
        mostSoldAdapter.clear();
        newFruitsAdapter.clear();
        allAdapter.clear();
        chipsAdapter.clearSelected();
        searchModel.setCategoryId(model.getId());
        searchModel.setText(searchBar.getText().toString());
        searchModel.setPage(0);
        chipsAdapter.setSelected(position);
        presenter.getSpecificFruit(searchModel);
      }
    });

    cart.setOnClickListener(this);
    search.setOnClickListener(this);
    back.setOnClickListener(this);
  }

  private void setPagination() {
    allList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = allList.getChildCount();
        totalItemCount = allFruitsManager.getItemCount();
        firstVisibleItem = allFruitsManager.findFirstVisibleItemPosition();

        if (isLoading) {
          if (totalItemCount > previousTotal) {
            isLoading = false;
            previousTotal = totalItemCount;
          }
        }
        if (allAdapter.getItemCount() > 40) {
          if (!isLoading && (totalItemCount - visibleItemCount)
            <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            page++;
            searchModel.setPage(page);
            presenter.getSpecificFruit(searchModel);
            // Do something
            isLoading = true;
          }
        }
      }
    });
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fabCart:
        ((FruitsActivity) requireActivity())
          .onAddFragment(new FruitCartFragment(),
            0, 0, true, FruitCartFragment.TAG);
        break;
      case R.id.btnCarListSearch:
        searchModel.setText(searchBar.getText().toString());
        presenter.getSpecificFruit(searchModel);
        break;
      case R.id.imgCarListBack:
        requireActivity().onBackPressed();
        break;
    }
  }

  @Override
  public void onPresenterStart() {
    if (allAdapter != null && mostSoldAdapter != null && newFruitsAdapter != null && chipsAdapter != null) {
      allAdapter.clear();
      mostSoldAdapter.clear();
      newFruitsAdapter.clear();
//      chipsAdapter.clear();
    }

    chipsManager.setOrientation(RecyclerView.HORIZONTAL);
    mostSoldManager.setOrientation(RecyclerView.HORIZONTAL);
    newFruitsManager.setOrientation(RecyclerView.HORIZONTAL);
    chipsManager.setReverseLayout(true);
    mostSoldManager.setReverseLayout(true);
    newFruitsManager.setReverseLayout(true);

    chipsList.setLayoutManager(chipsManager);
    newFruitsList.setLayoutManager(newFruitsManager);
    bestSellingList.setLayoutManager(mostSoldManager);
    allList.setLayoutManager(allFruitsManager);

    if (allAdapter == null)
      allAdapter = new CommonFruitsAdapter(getContext(), new CommonFruitsAdapter.CommonFruitsListener() {
        @Override
        public void onClick(FruitModel model, int pos) {
          FruitDetailsDialog detailsDialog = new FruitDetailsDialog(model, requireContext());
          detailsDialog.show(getActivity().getSupportFragmentManager(), FruitDetailsDialog.TAG);
        }

        @Override
        public void onAddToCart(FruitModel model, int pos) {
          model.setWeight(1.0d);
          if (tblCart.addItem(model))
            Toasty.success(requireContext(), R.string.addToCartSuccessfully).show();
          else {
            model.setWeight(0.0d);
            Toasty.error(requireContext(), "این میوه در سبد خرید موجود است").show();
          }
        }
      });

    if (mostSoldAdapter == null)
      mostSoldAdapter = new FruitsListAdapter(getContext(), new FruitsListAdapter.OnFruitListClick() {
        @Override
        public void onClick(FruitModel model, int position) {
          FruitDetailsDialog detailsDialog = new FruitDetailsDialog(model, requireContext());
          detailsDialog.show(getActivity().getSupportFragmentManager(), FruitDetailsDialog.TAG);
        }

        @Override
        public void onAddToCart(FruitModel model) {
          model.setWeight(1.0d);
          if (tblCart.addItem(model))
            Toasty.success(requireContext(), R.string.addToCartSuccessfully).show();
          else {
            model.setWeight(0.0d);
            Toasty.error(requireContext(), "این میوه در سبد خرید موجود است").show();
          }
        }

        @Override
        public void onCartCleared(FruitModel model) {

        }
      });

    if (newFruitsAdapter == null)
      newFruitsAdapter = new FruitsListAdapter(getContext(), new FruitsListAdapter.OnFruitListClick() {
        @Override
        public void onClick(FruitModel model, int position) {
          FruitDetailsDialog detailsDialog = new FruitDetailsDialog(model, requireContext());
          detailsDialog.show(getActivity().getSupportFragmentManager(), FruitDetailsDialog.TAG);
        }

        @Override
        public void onAddToCart(FruitModel model) {
          model.setWeight(1.0d);
          if (tblCart.addItem(model))
            Toasty.success(requireContext(), R.string.addToCartSuccessfully).show();
          else {
            model.setWeight(0.0d);
            Toasty.error(requireContext(), "این میوه در سبد خرید موجود است").show();
          }
        }

        @Override
        public void onCartCleared(FruitModel model) {

        }
      });
    setPagination();
  }

  @Override
  public void onLoading(boolean state) {
    if (state)
      shimmer.startShimmer();
  }

  @Override
  public void onSuggestedFruitReceived(FruitModel model) {
    allAdapter.add(model);
  }

  @Override
  public void onNewFruitReceived(FruitModel model) {
    newFruitsAdapter.add(model);
  }

  @Override
  public void onMostSoldFruitReceived(FruitModel model) {
    mostSoldAdapter.add(model);
  }

  @Override
  public void onCategoriesReceived(CarChipsListModel model) {
    chipsAdapter.add(model);
  }

  @Override
  public void onError(ResaultCode code) {
    String error = "";
    String title = "";
    @DrawableRes int imageRes = R.drawable.ic_warning;

    switch (code) {
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
      .setClickListener(() -> {
        mostSoldAdapter.clear();
        newFruitsAdapter.clear();
        allAdapter.clear();
        presenter.start();
      }));
  }

  @Override
  public void onFinish() {
    newFruitsList.setAdapter(newFruitsAdapter);
    bestSellingList.setAdapter(mostSoldAdapter);
    allList.setAdapter(allAdapter);
    chipsList.setAdapter(chipsAdapter);

    newFruitsList.setVisibility(newFruitsAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    includeNewFruits.setVisibility(newFruitsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    bestSellingList.setVisibility(mostSoldAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    includeMostSold.setVisibility(mostSoldAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    allList.setVisibility(allAdapter.getItemCount() == 0 ? View.GONE : View.VISIBLE);
    includeAllFruits.setVisibility(allAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    shimmer.stopShimmer();
    fruitScrollView.setVisibility(View.VISIBLE);
    shimmer.setVisibility(View.GONE);
  }
}
