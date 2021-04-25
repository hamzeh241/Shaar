package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarListFragmentService;
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

    carList = view.findViewById(R.id.carList);
    chipsList = view.findViewById(R.id.carChipsList);
    back = view.findViewById(R.id.imgCarListBack);
    filter = view.findViewById(R.id.imgCarListFilter);
    loading = view.findViewById(R.id.carListLoading);
  }

  private void implement() {
    //presenter.start();

    back.setOnClickListener(this);
    filter.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.imgCarListBack:
        ((CarActivity) getActivity()).onBackPressed();
        break;
      case R.id.imgCarListFilter:
        break;
    }
  }

  @Override
  public void onCarReceived(CarListModel model) {

  }

  @Override
  public void onChipsReceived(CarChipsListModel model) {

  }

  @Override
  public void onPresenterStart() {

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
