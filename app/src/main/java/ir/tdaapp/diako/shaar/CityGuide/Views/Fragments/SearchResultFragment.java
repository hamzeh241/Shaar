package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnSearchResultService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.SearchResultFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class SearchResultFragment extends CityGuideBaseFragment implements View.OnClickListener, OnSearchResultService {

    public static final String TAG = "SearchResultFragment";

    private int previousTotal = 0;
    private int page = 0;
    private boolean isLoading = true;
    private final int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    SearchResultFragmentPresenter presenter;

    ProgressBar loading;
    LinearLayoutManager layoutManager;
    CategoryDetailsAdapter adapter;
    String title;
    ImageButton back;

    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result_cityguide, container, false);

        findViews(view);
        implement();

        return view;
    }

    private void findViews(View view) {
        presenter = new SearchResultFragmentPresenter(getContext(), this);
        loading = view.findViewById(R.id.loading_result);
        recyclerView = view.findViewById(R.id.recyclear_result_cityguide);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        Bundle bundle = getArguments();
        title = bundle.getString("title");

        recyclerView = view.findViewById(R.id.recyclear_result_cityguide);
        adapter = new CategoryDetailsAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        back = view.findViewById(R.id.btn_back_result_search);
    }

    private void implement() {


        presenter.start(title, page);

        back.setOnClickListener(this::onClick);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_back_result_search:

                getActivity().onBackPressed();

                break;
        }

    }

    @Override
    public void onPresenterStart() {
        setAdapter();
        setPagination();

    }

    @Override
    public void onPresenterRestart() {
        setAdapter();
    }

    private void setAdapter() {
        adapter = new CategoryDetailsAdapter(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClick(model -> {

            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            CategoryItemDetailsFragmentCityGuide fragment = new CategoryItemDetailsFragmentCityGuide();
            fragment.setArguments(bundle);
            ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryItemDetailsFragmentCityGuide.TAG);
        });

    }

    private void setPagination() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (isLoading) {
                    if (totalItemCount > previousTotal) {
                        isLoading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (adapter.getItemCount() > 40) {
                    if (!isLoading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached
                        page++;
                        presenter.getItemByPage(title, page);
                        // Do something

                        isLoading = true;
                    }
                }
            }
        });
    }


    @Override
    public void onResultRecived(CategoryDetailsModel model) {
        adapter.add(model);
    }

    @Override
    public void onLoading(boolean load) {
        loading.setVisibility(load ? View.VISIBLE : View.GONE);
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
        });
    }

    @Override
    public void onPageFinished(List<CategoryDetailsModel> models) {

    }

    @Override
    public void onfinish() {

    }

}