package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.CarFavoriteItemfragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.CarFavoriteItemPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class CarFavoriteItemsFragment extends CarBaseFragment implements View.OnClickListener, CarFavoriteItemfragmentService {

    public static final String TAG = "CarFavoriteItemsFragment";
    private CarFavoriteItemPresenter presenter;
    private RecyclerView recyclerView;
    private ProgressBar loading;
    private ImageButton brn_back;
    private CarListAdapter adapter;
    LinearLayout linearLayoutNotLogIn;
    LinearLayout linearLayoutNoItemMessage;
    int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_favorite_items, container, false);

        findViews(view);
        implement();

        return view;
    }

    private void findViews(View view) {

        presenter = new CarFavoriteItemPresenter(getContext(), this);
        adapter = new CarListAdapter(getContext(), CarListAdapter.CARS_LIST);
        recyclerView = view.findViewById(R.id.recyclear_favorite_car);
        loading = view.findViewById(R.id.car_favorite_loading);
        brn_back = view.findViewById(R.id.imageButton_favorite_car);
        userId = new User(getContext()).GetUserId();
        linearLayoutNoItemMessage = view.findViewById(R.id.no_item_messag_car_fav);
        linearLayoutNotLogIn = view.findViewById(R.id.no_item_to_show_car_fav);


    }

    private void implement() {
        presenter.start();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        brn_back.setOnClickListener(this);

        //اگر کاربر لاگین نکرده باشد پیام خطا نشان داده میشود
        if (userId == 0){
            linearLayoutNotLogIn.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
        }

        adapter.setClickListener((model, position) -> {
            CarDeatailFragment fragment = new CarDeatailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            fragment.setArguments(bundle);
            ((CarActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CarDeatailFragment.TAG);
        });
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
        if (userId != 0){
            adapter.add(model);
            recyclerView.setAdapter(adapter);
            linearLayoutNoItemMessage.setVisibility(View.GONE);
        }


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
        if (adapter.getItemCount() == 0 && userId != 0){
            loading.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            linearLayoutNoItemMessage.setVisibility(View.VISIBLE);
        }


    }
}
