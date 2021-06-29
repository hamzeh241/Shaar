package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.AddItemPhotosAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.AddItemFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.Validation;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.AddItemPhotosModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ResultViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.ItemStructureViewModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.AddItemFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs.MessageDialog;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class AddItemFragmentCityGuide extends CityGuideBaseFragment implements View.OnClickListener, AddItemFragmentService {

    public static final String TAG = "AddItemFragment";

    AddItemFragmentPresenter presenter;

    EditText title, cellphone, tel1, tel2, instaId, telegramId, description, address;
    ImageButton addPhoto;
    RecyclerView photoList;
    CardView submit;
    Spinner category, filter;
    ProgressBar loading;

    ArrayList<String> images = new ArrayList<>();

    LinearLayoutManager layoutManager;
    AddItemPhotosAdapter adapter;

    ItemStructureViewModel viewModel;

    MessageDialog uploading;
    MessageDialog sending;

    ImageButton back;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View view) {
        presenter = new AddItemFragmentPresenter(getContext(), this);
        viewModel = new ItemStructureViewModel();
        adapter = new AddItemPhotosAdapter(getContext());
        uploading = new MessageDialog("در حال آپلود عکس ها...", false);
        sending = new MessageDialog("در حال ثبت و ارسال اطلاعات...", false);
        uploading.setCancelable(false);
        sending.setCancelable(false);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        title = view.findViewById(R.id.edtAddItemName);
        cellphone = view.findViewById(R.id.edtAddItemCellPhone);
        tel1 = view.findViewById(R.id.edtAddItemTel1);
        tel2 = view.findViewById(R.id.edtAddItemTel2);
        address = view.findViewById(R.id.edtAddItemAddress);
        instaId = view.findViewById(R.id.edtAddItemInstagramId);
        telegramId = view.findViewById(R.id.edtAddItemTelegramId);
        description = view.findViewById(R.id.edtAddItemDescription);
        category = view.findViewById(R.id.spinnerAddItemCategory);
        filter = view.findViewById(R.id.spinnerAddItemCategoryFilter);
        addPhoto = view.findViewById(R.id.btnAddItemInsertPhoto);
        photoList = view.findViewById(R.id.addItemPhotoList);
        submit = view.findViewById(R.id.btnAddItemSendData);
        loading = view.findViewById(R.id.addItemLoading);
        back = view.findViewById(R.id.imageButtonAddItem);
    }

    private void implement() {
        presenter.start();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.getChipsById(((CategoryModel) category.getItemAtPosition(position)).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        photoList.setLayoutManager(layoutManager);
        photoList.setAdapter(adapter);
        submit.setOnClickListener(this);
        addPhoto.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItemSendData:
                if (checkValidation()) {
                    viewModel.setTitle(title.getText().toString());
                    viewModel.setAddress(address.getText().toString());
                    viewModel.setCellPhone(cellphone.getText().toString());
                    viewModel.setDesciption(description.getText().toString());
                    viewModel.setTelegramId(telegramId.getText().toString());
                    viewModel.setInstagramId(instaId.getText().toString());
                    viewModel.setTel1(tel1.getText().toString());
                    viewModel.setTel2(tel2.getText().toString());
                    viewModel.setUserId(new User(getActivity()).GetUserId());
                    viewModel.setImages(images);
                    viewModel.setFilterId(((CategoryDetailsChipModel) filter.getSelectedItem()).getId());
                    viewModel.setUserId(new User(getContext()).GetUserId());
                    presenter.sendDetails(viewModel);
                }
                break;
            case R.id.btnAddItemInsertPhoto:
                presenter.requestStoragePermission(getActivity());
                break;

            case R.id.imageButtonAddItem:
                getActivity().onBackPressed();
                break;
        }
    }

    @Override
    public void onPresenterStart() {

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

    @Override
    public void onFinish() {

    }

    @Override
    public void onLoading(boolean state) {
        loading.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFilterReceived(List<CategoryDetailsChipModel> model) {
        ArrayAdapter<CategoryDetailsChipModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
        filter.setAdapter(adapter);
    }

    @Override
    public void onCategoryReceived(List<CategoryModel> model) {
        ArrayAdapter<CategoryModel> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_layout, model);
        category.setAdapter(adapter);
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
            ((GuideActivity) getActivity()).onAddFragment(new CategoryFragmentCityGuide(), 0, 0, false, AddItemFragmentCityGuide.TAG);
        }
    }

    private boolean checkValidation() {
        boolean a = Validation.Required(title, "نام کسب و کار باید پر شود");
        boolean b = Validation.Required(cellphone, "شماره باید پر شود");
        boolean c = Validation.Required(description, "توضیحات باید پر شود");
        boolean d = Validation.Required(tel1, "تلفن اول باید پر شود");
        boolean e = Validation.Required(instaId, "آیدی اینستاگرام باید پر شود");
        boolean f = Validation.Required(telegramId, "آیدی تلگرام باید پر شود");
        boolean g = checkImagesUploaded();
        boolean h = Validation.ValidPhoneNumber(cellphone, "شماره باید صحیح وارد شود");

        return a && b && c && d && e && f && g&&h;
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

