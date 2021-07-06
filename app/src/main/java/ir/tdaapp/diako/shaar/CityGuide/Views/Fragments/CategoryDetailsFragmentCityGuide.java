package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsChipsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryDetailsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;


public class CategoryDetailsFragmentCityGuide extends CityGuideBaseFragment implements CategoryDetailsFragmentService, View.OnClickListener {

    public static final String TAG = "CategoryDetailsFragment";


    private int previousTotal = 0;
    private int page = 0;
    private boolean isLoading = true;
    private final int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    int userId;

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
        userId = new User(getContext()).GetUserId();
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
        hideKeyBoard();
        root.setOnClickListener(this);
        searchBar.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                filter.performClick();
                hideKeyboard(getActivity());
                return true;
            }
            return false;
        });

        fab.setOnClickListener(this);
        filter.setOnClickListener(this);
        back.setOnClickListener(this::onClick);
    }

    public void hideKeyBoard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        chipsAdapter.setOnItemClick((model, position) -> {
            if (model.isSelected()) {
                detailsAdapter.clear();
                chipsAdapter.clearSelected();
                presenter.getItemByFilter(searchBar.getText().toString(), selectedModel.getId(), page);
            } else {
                detailsAdapter.clear();
                chipsAdapter.clearSelected();
                chipsAdapter.setSelected(position);
                selectedModel = model;
                presenter.getItemByFilter(searchBar.getText().toString(), selectedModel.getId(), page);
            }
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
                if (detailsAdapter.getItemCount() > 40) {
                    if (!isLoading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached
                        page++;
                        presenter.getItemByFilter(searchBar.getText().toString(), selectedModel != null ? selectedModel.getId() : 0, page);
                        // Do something

                        isLoading = true;
                    }
                }
            }
        });
    }

    @Override
    public void onItemsReceived(CategoryDetailsModel model) {
        detailsAdapter.add(model);
        Log.i(TAG, "onItemsReceived: " + model.getTitle());
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
                    presenter.start(getArguments().getInt("ID"));
                    try {
                        chipsAdapter.clearSelected();
                        chipsAdapter.setSelected(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.categoryDetailsAddItemFab:
                if (new User(getContext()).GetUserId() == 0) {
                    Toasty.info(getContext(), R.string.addAccuont, Toast.LENGTH_SHORT, false).show();
                    ((GuideActivity) getActivity()).onAddFragment(new Fragment_Login_Home(1), R.anim.fadein, R.anim.fadeout, true, Fragment_Login_Home.TAG);
                } else {
                    ((GuideActivity) getActivity()).onAddFragment(new AddItemFragmentCityGuide(), 0, 0, true, AddItemFragmentCityGuide.TAG);
                }
                break;
            case R.id.imgCategoryDetailsFilter:
                presenter.start(searchBar.getText().toString(), selectedModel.getId(), 0);
                break;
            case R.id.imgCategoryDetailsBack:
                getActivity().onBackPressed();
                break;
        }
    }
}
