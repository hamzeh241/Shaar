package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarListFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.R;

public class CarListFragment extends CarBaseFragment implements View.OnClickListener, CarListFragmentService {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_car_list, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view){

  }

  private void implement(){

  }

  @Override
  public void onClick(View v) {

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
  public void loadingState(boolean loading) {

  }
}
