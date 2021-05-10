package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Cars.Model.Services.AddCarFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.AddItemEntryModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarStructureViewModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.AddCarFragmentPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.AddItemPhotosAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.Validation;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.AddItemPhotosModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs.MessageDialog;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;

public class AddCarFragment extends CarBaseFragment implements View.OnClickListener, AddCarFragmentService {

  public static final String TAG = "AddCarFragment";

  AddCarFragmentPresenter presenter;

  Spinner category, productionYear, brand, model, engineStatus,
    bodyStatus, chassisStatus, insuranceDeadline, gearbox, document, sellType;

  EditText mileage, price, title, address, description, phone;
  ImageButton addPhoto;
  CardView submit;
  RecyclerView photoList;
  ProgressBar loading;
  CheckBox exchange;

  ArrayList<String> images = new ArrayList<>();

  LinearLayoutManager layoutManager;
  AddItemPhotosAdapter adapter;
  CarStructureViewModel viewModel;

  MessageDialog uploading;
  MessageDialog sending;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_add_car, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    presenter = new AddCarFragmentPresenter(getContext(), this);
    adapter = new AddItemPhotosAdapter(getContext());
    viewModel = new CarStructureViewModel();
    uploading = new MessageDialog("در حال آپلود عکس ها...", false);
    sending = new MessageDialog("در حال ثبت و ارسال اطلاعات...", false);
    uploading.setCancelable(false);
    sending.setCancelable(false);
    layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(RecyclerView.HORIZONTAL);

    category = view.findViewById(R.id.spinnerCategory);
    productionYear = view.findViewById(R.id.spinnerFromProductionYear);
    brand = view.findViewById(R.id.spinnerBrand);
    model = view.findViewById(R.id.spinnerModel);
    engineStatus = view.findViewById(R.id.spinnerEngineStatus);
    bodyStatus = view.findViewById(R.id.spinnerBodyStatus);
    chassisStatus = view.findViewById(R.id.spinnerChassisStatus);
    insuranceDeadline = view.findViewById(R.id.spinnerInsurance);
    gearbox = view.findViewById(R.id.spinnerGearbox);
    document = view.findViewById(R.id.spinnerDocuments);
    sellType = view.findViewById(R.id.spinnerSellType);

    mileage = view.findViewById(R.id.edtMileage);
    price = view.findViewById(R.id.edtPrice);
    title = view.findViewById(R.id.edtTitle);
    address = view.findViewById(R.id.edtAddress);
    description = view.findViewById(R.id.edtDescription);
    phone = view.findViewById(R.id.edtPhone);

    addPhoto = view.findViewById(R.id.btnAddCarInsertPhoto);
    submit = view.findViewById(R.id.btnAddCarSendData);
    loading = view.findViewById(R.id.addCarLoading);
    exchange = view.findViewById(R.id.checkboxExchange);

    photoList = view.findViewById(R.id.addCarPhotoList);
  }

  private void implement() {
    presenter.start();

    photoList.setLayoutManager(layoutManager);
    photoList.setAdapter(adapter);
    submit.setOnClickListener(this);
    addPhoto.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnAddCarInsertPhoto:
        presenter.requestStoragePermission(getActivity());
        break;
      case R.id.btnAddCarSendData:
        if (checkValidation()) {
          viewModel.setFunction(Integer.parseInt(mileage.getText().toString()));
          viewModel.setPrice(Integer.parseInt(price.getText().toString()));
          viewModel.setBrandId(((AddItemEntryModel) brand.getSelectedItem()).getId());
          viewModel.setEngineStatusId(((AddItemEntryModel) engineStatus.getSelectedItem()).getId());
          viewModel.setChassisStatusId(((AddItemEntryModel) chassisStatus.getSelectedItem()).getId());
          viewModel.setBodyStatusId(((AddItemEntryModel) bodyStatus.getSelectedItem()).getId());
          viewModel.setInsuranceDeadlineId(((AddItemEntryModel) insuranceDeadline.getSelectedItem()).getId());
          viewModel.setGearboxId(((AddItemEntryModel) gearbox.getSelectedItem()).getId());
          viewModel.setDocumentId(((AddItemEntryModel) document.getSelectedItem()).getId());
          viewModel.setCategoryId(((CarChipsListModel) category.getSelectedItem()).getId());
          viewModel.setSellTypeId(((AddItemEntryModel) sellType.getSelectedItem()).getId());
          viewModel.setExchange(exchange.isChecked());
          viewModel.setTitle(title.getText().toString());
          viewModel.setDescription(description.getText().toString());
          viewModel.setPhone(phone.getText().toString());
          viewModel.setAddress(address.getText().toString());
          viewModel.setProductionYearId(((AddItemEntryModel) productionYear.getSelectedItem()).getId());
          viewModel.setImages(images);
          viewModel.setUserId(new User(getContext()).GetUserId());

          presenter.sendDetails(viewModel);
        }
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
  public void onLoading(boolean state) {
    TransitionManager.beginDelayedTransition((ViewGroup) submit.getRootView());
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
  }

  @Override
  public void onCategoriesReceived(List<CarChipsListModel> model) {
    ArrayAdapter<CarChipsListModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    category.setAdapter(adapter);
  }

  @Override
  public void onProductionYearsReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    productionYear.setAdapter(adapter);
  }

  @Override
  public void onBrandsReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    brand.setAdapter(adapter);
    presenter.getModels(((AddItemEntryModel) brand.getSelectedItem()).getId());
  }

  @Override
  public void onModelsReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    this.model.setAdapter(adapter);
  }

  @Override
  public void onEngineStatusReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    engineStatus.setAdapter(adapter);
  }

  @Override
  public void onChassisStatusReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    chassisStatus.setAdapter(adapter);
  }

  @Override
  public void onBodyStatusReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    bodyStatus.setAdapter(adapter);
  }

  @Override
  public void onInsuranceDeadlineReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    insuranceDeadline.setAdapter(adapter);
  }

  @Override
  public void onGearBoxReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    gearbox.setAdapter(adapter);
  }

  @Override
  public void onDocumentReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    document.setAdapter(adapter);
  }

  @Override
  public void onSellTypeReceived(List<AddItemEntryModel> model) {
    ArrayAdapter<AddItemEntryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
    sellType.setAdapter(adapter);
  }

  @Override
  public void onResultReceived(ResultViewModel model) {
    if (model.getStatus()) {
      Toasty.success(getContext(), model.getTitle()).show();
    } else {
      Toasty.error(getContext(), model.getTitle()).show();
    }
  }

  @Override
  public void onStoragePermissionGranted() {
    presenter.openImageSelector(getActivity());
  }

  @Override
  public void onStoragePermissionDenied() {
    Toasty.error(getContext(), getContext().getString(R.string.storage_permisison_needed)).show();
  }

  @Override
  public void onImagesUploaded(ArrayList<String> arrayList, List<Uri> uris) {
    images = arrayList;
    for (Uri uri : uris) {
      adapter.add(new AddItemPhotosModel(uri));
    }
    viewModel.setImages(images);
    adapter.setListener((model, position) -> {
      arrayList.remove(position);
    });
  }

  @Override
  public void onImageUploading(boolean loading) {
    if (loading) {
      uploading.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
    } else {
      uploading.dismiss();
    }
  }

  @Override
  public void onDataSendingState(boolean loading) {
    if (loading) {
      sending.show(getActivity().getSupportFragmentManager(), MessageDialog.TAG);
    } else {
      sending.dismiss();
      ((CarActivity) getActivity()).onAddFragment(new CarListFragment(), 0, 0, true, CarListFragment.TAG);
    }
  }

  private boolean checkValidation() {
    boolean a = Validation.Required(title, "عنوان باید پر شود");
    boolean b = Validation.Required(mileage, "کارکرد باید پر شود");
    boolean c = Validation.Required(description, "توضیحات باید پر شود");
    boolean d = Validation.Required(phone, "شماره تلفن باید پر شود");
    boolean e = Validation.Required(price, "قیمت باید پر شود");
    boolean f = Validation.Required(address, "آدرس باید پر شود");
    boolean g = checkImagesUploaded();

    return a && b && c && d && e && f && g;
  }

  private boolean checkImagesUploaded() {
    if (images.size() > 0) {
      return true;
    } else {
      Toasty.error(getContext(), getContext().getString(R.string.noPhotoSelected)).show();
      return false;
    }
  }
}
