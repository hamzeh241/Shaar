package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
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

    private RecyclerView carList, chipsList;
    private ImageButton back, filter, search;
    private ProgressBar loading;
    private EditText searchBar;
    private FloatingActionButton fab;
    private LinearLayoutManager chipsManager;
    private LinearLayoutManager carManager;
    private CarListAdapter carAdapter;
    private ChipsListAdapter chipsAdapter;
    int userId;
    SearchModel searchModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View view) {
        presenter = new CarListFragmentPresenter(getContext(), this);

        carAdapter = new CarListAdapter(getContext(), CarListAdapter.CARS_LIST);
        chipsAdapter = new ChipsListAdapter(getContext());

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
        userId = new User(getContext()).GetUserId();
        Log.i(TAG, "findView: " + userId);
    }

    private void implement() {
        presenter.start();
        chipsList.setLayoutManager(chipsManager);
        carList.setLayoutManager(carManager);
        chipsAdapter.setChipListener((model, position) -> {
            carAdapter.clear();
            chipsAdapter.clearSelected();
            chipsAdapter.setSelected(position);
            searchModel.setCategoryId(model.getId());
            presenter.getCars(searchModel, page);
        });

        carAdapter.setClickListener((model, position) -> {
            CarDeatailFragment fragment = new CarDeatailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            fragment.setArguments(bundle);
            ((CarActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CarDeatailFragment.TAG);
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
                        Toasty.info(getContext(), "End is reached").show();
                        presenter.getCars(searchModel, page);
                        // Do something
                        isLoading = true;
                    }
                }
            }
        });
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

                if (userId == 0) {
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
        Log.i(TAG, "onCarReceived: " + model.getTitle());
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
            presenter.start();
            chipsAdapter.clearSelected();
            chipsAdapter.setSelected(0);
        });
    }

    @Override
    public void onFinish() {
    }

    @Override
    public void loadingState(boolean state) {
        loading.setVisibility(state ? View.VISIBLE : View.GONE);
        carList.setVisibility(state ? View.GONE : View.VISIBLE);
        chipsList.setVisibility(state ? View.GONE : View.VISIBLE);
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
        presenter.getCars(searchModel, page);
    }
}
