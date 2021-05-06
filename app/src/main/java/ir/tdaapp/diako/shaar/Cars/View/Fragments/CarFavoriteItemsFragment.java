package ir.tdaapp.diako.shaar.Cars.View.Fragments;

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

import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarFavoriteItemfragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarFavoriteItemPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.R;

public class CarFavoriteItemsFragment extends CarBaseFragment implements View.OnClickListener, CarFavoriteItemfragmentService {

    public static final String TAG = "CarFavoriteItemsFragment";
    private CarFavoriteItemPresenter presenter;
    private CarListAdapter carListAdapter;
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private ImageButton brn_back;
    private CarListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_favorite_items, container, false);

        findViews(view);
        implement();
        presenter.start();

        return view;
    }

    private void findViews(View view) {

        presenter = new CarFavoriteItemPresenter(getContext(), this);
        adapter = new CarListAdapter(getContext());
        recyclerView = view.findViewById(R.id.recyclear_favorite_car);
        loading = view.findViewById(R.id.car_favorite_loading);
        carListAdapter = new CarListAdapter(getContext());
        brn_back = view.findViewById(R.id.imageButton_favorite_car);

    }

    private void implement() {

        presenter = new CarFavoriteItemPresenter(getContext(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        brn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageButton_favorite_car:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onItemReceived(CarListModel model) {
        adapter.add(model);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPresenterStart() {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadingState(boolean state) {
        loading.setVisibility(state ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(state ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onFinish() {

    }
}
