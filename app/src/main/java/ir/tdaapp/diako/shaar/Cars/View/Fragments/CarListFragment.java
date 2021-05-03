package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Adapters.ChipsListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarListFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onCarListClickListener;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarListFragmentPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.R;

public class CarListFragment extends CarBaseFragment implements View.OnClickListener, CarListFragmentService {

  public static final String TAG = "CarListFragment";

  private CarListFragmentPresenter presenter;

  private RecyclerView carList, chipsList;
  private ImageButton back, filter;
  private ProgressBar loading;

  private LinearLayoutManager chipsManager;

  private CarListAdapter carAdapter;
  private ChipsListAdapter chipsAdapter;

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

    carAdapter = new CarListAdapter(getContext());
    chipsAdapter = new ChipsListAdapter(getContext());

    carList = view.findViewById(R.id.carList);
    chipsList = view.findViewById(R.id.carChipsList);
    back = view.findViewById(R.id.imgCarListBack);
    filter = view.findViewById(R.id.imgCarListFilter);
    loading = view.findViewById(R.id.carListLoading);

    chipsManager = new LinearLayoutManager(getContext());
    chipsManager.setOrientation(RecyclerView.HORIZONTAL);
  }

  private void implement() {
    presenter.start();

    chipsList.setLayoutManager(chipsManager);
    carList.setLayoutManager(new LinearLayoutManager(getContext()));
    carAdapter.setClickListener((model, position) -> {
      CarDeatailFragment fragment = new CarDeatailFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);

      ((CarActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CarDeatailFragment.TAG);
    });

    back.setOnClickListener(this);
    filter.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.imgCarListBack:
        getActivity().onBackPressed();
        break;
      case R.id.imgCarListFilter:
        break;
    }
  }

  @Override
  public void onCarReceived(CarListModel model) {
    carAdapter.add(model);
  }

  @Override
  public void onChipsReceived(CarChipsListModel model) {
    chipsAdapter.add(model);
  }

  @Override
  public void onPresenterStart() {
    carList.setAdapter(carAdapter);
    chipsList.setAdapter(chipsAdapter);
  }

  @Override
  public void onError(String s) {

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
}
