package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.ChipsListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarListFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onSearchParametersReceived;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.FilterModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.SearchModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarListFragmentPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.Cars.View.Dialogs.CarSearchFilterDialog;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class CarListFragment extends CarBaseFragment implements View.OnClickListener, CarListFragmentService, onSearchParametersReceived {

  public static final String TAG = "CarListFragment";

  private int previousTotal = 0;
  private int page = 0;
  private boolean isLoading = true;
  private final int visibleThreshold = 5;
  int firstVisibleItem, visibleItemCount, totalItemCount;

  private CarListFragmentPresenter presenter;
  private Handler handler;

  private RelativeLayout root, noItem;
  private RecyclerView carList, chipsList;
  private ImageButton back, filter, search;
  private MKLoader loading;
  private EditText searchBar;
  private FloatingActionButton fab;
  private LinearLayoutManager chipsManager;
  private LinearLayoutManager carManager;
  private CarListAdapter carAdapter;
  private ChipsListAdapter chipsAdapter;
  int userId;
  SearchModel searchModel;
  Fade fade;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_car_list, container, false);

    findView(view);
    implement();
    hideSoftKeyBoard();

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
//    hideKeyboard(getActivity());
  }

  private void findView(View view) {
    presenter = new CarListFragmentPresenter(getContext(), this);
    handler = new Handler();

    carAdapter = new CarListAdapter(getContext(), CarListAdapter.CARS_LIST);
    chipsAdapter = new ChipsListAdapter(getContext());

    root = view.findViewById(R.id.carListRoot);
    noItem = view.findViewById(R.id.no_item);
    carList = view.findViewById(R.id.carList);
    chipsList = view.findViewById(R.id.carChipsList);
    back = view.findViewById(R.id.imgCarListBack);
    filter = view.findViewById(R.id.imgCarListFilter);
    loading = view.findViewById(R.id.carListLoading);
    fab = view.findViewById(R.id.fabAddCar);
    search = view.findViewById(R.id.btnCarListSearch);
    searchBar = view.findViewById(R.id.edtCarListSearch);

    chipsManager = new LinearLayoutManager(getContext());
    carManager = new LinearLayoutManager(getContext());
    chipsManager.setOrientation(RecyclerView.HORIZONTAL);
    chipsManager.setReverseLayout(true);
    carManager.setOrientation(RecyclerView.VERTICAL);

    searchModel = new SearchModel();

    fade = new Fade(Fade.OUT);
    fade.setInterpolator(new LinearOutSlowInInterpolator());
    fade.setDuration(android.R.integer.config_shortAnimTime);
    fade.setStartDelay(android.R.integer.config_shortAnimTime);
  }

  private void implement() {
    presenter.start();
    chipsList.setLayoutManager(chipsManager);
    carList.setLayoutManager(carManager);
    chipsAdapter.setChipListener((model, position) -> {
      if (model.isSelected()) {
        carAdapter.clear();
        chipsAdapter.clearSelected();
        presenter.getCars(null, 0);
      } else {
        carAdapter.clear();
        chipsAdapter.clearSelected();
        chipsAdapter.setSelected(position);
        searchModel.setCategoryId(model.getId());
        presenter.getCars(searchModel, page);
      }
    });

    carAdapter.setClickListener((model, position) -> {
      CarDeatailFragment fragment = new CarDeatailFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);
      ((CarActivity) getActivity()).onAddFragment(fragment, R.anim.fadein,
        R.anim.fadeout, true, CarDeatailFragment.TAG);
    });

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
    back.setOnClickListener(this);
    filter.setOnClickListener(this);
    fab.setOnClickListener(this);
    search.setOnClickListener(this);
    searchBar.setOnFocusChangeListener((v, hasFocus) -> {
    });
  }

  private void setPagination() {
    carList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = carList.getChildCount();
        totalItemCount = carManager.getItemCount();
        firstVisibleItem = carManager.findFirstVisibleItemPosition();

        if (isLoading) {
          if (totalItemCount > previousTotal) {
            isLoading = false;
            previousTotal = totalItemCount;
          }
        }
        if (carAdapter.getItemCount() > 40) {
          if (!isLoading && (totalItemCount - visibleItemCount)
            <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            page++;
            presenter.getCars(searchModel, page);
            // Do something
            isLoading = true;
          }
        }
      }
    });
  }

  private void hideKeyboard(Activity activity) {
    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
    //Find the currently focused view, so we can grab the correct window token from it.
    View view = searchBar;
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
      view = new View(activity);
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }

  public void hideSoftKeyBoard() {
    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }

  private void clearSearchParams() {
    searchModel.setFromPrice(0);
    searchModel.setToPrice(0);
    searchModel.setFromDateId(0);
    searchModel.setToDateId(0);
    searchModel.setCategoryId(0);
    searchModel.setBrandId(0);
    searchModel.setGearboxId(0);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.imgCarListBack:
        getActivity().onBackPressed();
        break;
      case R.id.imgCarListFilter:
        CarSearchFilterDialog dialog = new CarSearchFilterDialog(this);
        dialog.show(getActivity().getSupportFragmentManager(), CarSearchFilterDialog.TAG);
        break;
      case R.id.fabAddCar:
        if (new User(getContext()).GetUserId() == 0) {
          Toasty.info(getContext(), R.string.addAccuont, Toast.LENGTH_SHORT, false).show();
          ((CarActivity) getActivity()).onAddFragment(new Fragment_Login_Home(2), R.anim.fadein, R.anim.fadeout, true, Fragment_Login_Home.TAG);
        } else {
          ((CarActivity) getActivity()).onAddFragment(new AddCarFragment(), R.anim.fadein, R.anim.fadeout, true, AddCarFragment.TAG);
        }
        break;
      case R.id.btnCarListSearch:
        searchModel.setText(searchBar.getText().toString());
        presenter.getCars(searchModel, page);
        break;
    }
  }

  @Override
  public void onCarReceived(CarListModel model) {
    carAdapter.add(model);
  }

  @Override
  public void onCarsReceived(List<CarListModel> model) {
    carAdapter.clear();
  }

  @Override
  public void onChipsReceived(CarChipsListModel model) {
    chipsAdapter.add(model);
  }

  @Override
  public void onPresenterStart() {
    carList.setAdapter(carAdapter);
    chipsList.setAdapter(chipsAdapter);
    setPagination();

//     userId= new User(getContext()).GetUserId();
    userId = new User(getContext()).GetUserId();
    Log.i(TAG, "onPresenterStart: "+ userId);
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
      .setClickListener(() -> {
        carAdapter.clear();
        chipsAdapter.clear();
        presenter.start();
      }));
  }

  @Override
  public void onFinish() {
    if (carAdapter.getItemCount() == 0) {
      noItem.setVisibility(View.VISIBLE);
    } else {
      noItem.setVisibility(View.GONE);
    }
  }

  @Override
  public void loadingState(boolean state) {
//    TransitionManager.beginDelayedTransition(root);
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
    carList.setVisibility(state ? View.GONE : View.VISIBLE);
    chipsList.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
  }

  @Override
  public void onResult(FilterModel object) {
    //searchObject = object;
    filter.setImageResource(R.drawable.ic_baseline_filter_alt_24);
    carAdapter.clear();
    searchModel.setBrandId(object.getBrandId());
    searchModel.setText(searchBar.getText().toString());
    searchModel.setPage(page);
    searchModel.setFromDateId(object.getFromDateId());
    searchModel.setToDateId(object.getToDateId());
    searchModel.setFromPrice(object.getFromPrice());
    searchModel.setToPrice(object.getToPrice());
    searchModel.setGearboxId(object.getGearboxId());
    presenter.getCars(searchModel, page);
  }

  @Override
  public void onCancel() {
    filter.setImageResource(R.drawable.ic_outline_filter_alt_24);
    clearSearchParams();
    carAdapter.clear();
    chipsAdapter.clearSelected();
    presenter.getCars(null, 0);
  }
}
