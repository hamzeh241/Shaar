package ir.tdaapp.diako.shaar.Cars.View.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ir.tdaapp.diako.shaar.Cars.Model.Services.CarSearchDialogService;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onSearchParametersReceived;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.FilterModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarSearchDialogPresenter;
import ir.tdaapp.diako.shaar.R;

public class CarSearchFilterDialog extends DialogFragment implements View.OnClickListener, CarSearchDialogService {

  public static final String TAG = "CarSearchFilterDialog";

  CarSearchDialogPresenter presenter;


  private final onSearchParametersReceived contract;

  Spinner brand, fromProductionYear, toProductionYear, gearbox;
  EditText fromPrice, toPrice;
  Button submit,cancel;

  public CarSearchFilterDialog(onSearchParametersReceived contract) {
    this.contract = contract;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_car_filter_search, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    presenter = new CarSearchDialogPresenter(getContext(), this);

    brand = view.findViewById(R.id.spinnerBrand);
    fromProductionYear = view.findViewById(R.id.spinnerFromProductionYear);
    toProductionYear = view.findViewById(R.id.spinnerToProductionYear);
    gearbox = view.findViewById(R.id.spinnerGearbox);
    fromPrice = view.findViewById(R.id.edtFromPrice);
    toPrice = view.findViewById(R.id.edtToPrice);
    submit = view.findViewById(R.id.btnSubmitSearch);
    cancel = view.findViewById(R.id.btnDialogDismiss);

  }

  private void implement() {
    presenter.start();

    submit.setOnClickListener(this);

    cancel.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnSubmitSearch:
        FilterModel model = new FilterModel();
        model.setBrandId(((AddItemEntryModel) brand.getSelectedItem()).getId());
        model.setToDateId(((AddItemEntryModel) toProductionYear.getSelectedItem()).getId());
        model.setFromDateId(((AddItemEntryModel) fromProductionYear.getSelectedItem()).getId());
        model.setGearboxId(((AddItemEntryModel) gearbox.getSelectedItem()).getId());
        model.setFromPrice(checkText(fromPrice) ? 0.0 : Double.parseDouble(fromPrice.getText().toString()));
        model.setToPrice(checkText(toPrice) ? 0.0 : Double.parseDouble(toPrice.getText().toString()));
        contract.onResult(model);
        dismiss();
        break;

      case R.id.btnDialogDismiss:
        contract.onCancel();
        dismiss();
        break;
    }


  }

  @Override
  public void onPresenterStart() {


  }

  @Override
  public void onError(String result) {

  }

  @Override
  public void onFinish() {

  }

  @Override
  public void onProductionYearsReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    fromProductionYear.setAdapter(adapter);
    toProductionYear.setAdapter(adapter);
  }

  @Override
  public void onBrandsReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    brand.setAdapter(adapter);
  }

  @Override
  public void onGearBoxReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    gearbox.setAdapter(adapter);
  }

  private boolean checkText(EditText editText) {
    return editText.getText().toString().isEmpty();
  }
}
