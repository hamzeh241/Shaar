package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.CategoryFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class CategoryFragmentCityGuide extends CityGuideBaseFragment implements CategoryFragmentService, View.OnClickListener {

    public static final String TAG = "CategoryFragment";

    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;
    private CategoryAdapter adapter;
    private CategoryFragmentPresenter presenter;
    private ProgressBar loading;
    private ViewGroup searchBar;
    private TextView btnSearch;
    private EditText editText;

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
        btnSearch = itemView.findViewById(R.id.btn_search_cityguidde);
        editText = itemView.findViewById(R.id.editTextTextPersonName2);

    }

    private void implement() {
        presenter.start();
        recyclerView.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        hideSoftKeyBoard();
        searchBar.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        editText.setOnKeyListener((v, keyCode, event) -> {
            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press
                btnSearch.performClick();
                hideKeyboard(getActivity());
                return true;
            }
            return false;
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.categorySearchBar:
//        CategoryDetailsFragmentCityGuide fragment = new CategoryDetailsFragmentCityGuide();
//        Bundle bundle = new Bundle();
//        bundle.putInt("ID", 0);
//        fragment.setArguments(bundle);
//        ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryDetailsFragmentCityGuide.TAG);
                break;

            case R.id.btn_search_cityguidde:
                SearchResultFragment resultFragment = new SearchResultFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("title", editText.getText().toString());
                resultFragment.setArguments(bundle1);
                ((GuideActivity) getActivity()).onAddFragment(resultFragment, 0, 0, true, SearchResultFragment.TAG);
                hideSoftKeyBoard();
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
            ((GuideActivity) getActivity()).onAddFragment(fragment,
                    R.anim.fadein, R.anim.fadeout, true, CategoryDetailsFragmentCityGuide.TAG);
        });
    }

    @Override
    public void onItemsReceived(CategoryModel model) {
        adapter.add(model);
        recyclerView.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
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
    public void onFinish() {

    }

    public void hideSoftKeyBoard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
                .setClickListener(() ->
                        presenter.start()));
    }

}
