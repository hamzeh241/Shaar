package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

 private  CarFavoriteItemPresenter presenter;

 private  CarListAdapter carListAdapter;

 private  RecyclerView recyclerView;

 private  ProgressBar loading;

 private LinearLayoutManager layoutManager;

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


        presenter = new CarFavoriteItemPresenter(getContext(),this);


        recyclerView = view.findViewById(R.id.recyclear_favorite_car);


        loading = view.findViewById(R.id.car_favorite_loading);

        carListAdapter = new CarListAdapter(getContext());

        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        adapter = new CarListAdapter(getContext());


    }

    private void implement() {

        presenter.start();

        recyclerView.setLayoutManager(layoutManager);




    }



    @Override
    public void onClick(View v) {

    }


    @Override
    public void onItemReceived(CarListModel model) {


    }


    @Override
    public void onPresenterStart() {

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void loadingState(boolean state) {
        loading.setVisibility(state ? View.VISIBLE :View.GONE);

    }


    @Override
    public void onError(String message) {

    }

    @Override
    public void onFinish() {

    }
}
