package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.UsersCarsFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.UsersCarsFragmentPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class UsersCarsListFragment extends CarBaseFragment implements View.OnClickListener, UsersCarsFragmentService {

    public static final String TAG = "UsersCarsList";
    UsersCarsFragmentPresenter presenter;

    LinearLayout linearLayoutNotLogIn;
    LinearLayout linearLayoutNoItemMessage;

    int userId;

    private RecyclerView list;
    private ProgressBar loading;

    CarListAdapter adapter;
    LinearLayoutManager manager;
    ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_cars, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View view) {
        presenter = new UsersCarsFragmentPresenter(getContext(), this);

        linearLayoutNotLogIn = view.findViewById(R.id.no_item_to_show_layout);

        userId = new User(getActivity()).GetUserId();

        list = view.findViewById(R.id.usersCarsList);
        loading = view.findViewById(R.id.userCarsLoading);
        back = view.findViewById(R.id.imgUsersCarsBack);

        adapter = new CarListAdapter(getContext(), CarListAdapter.USERS_LIST);
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutNoItemMessage = view.findViewById(R.id.no_item_message_use_car);
    }

    private void implement() {
        presenter.start(userId);
        hideSoftKeyBoard();
        adapter.setClickListener((model, position) -> {
            CarDeatailFragment fragment = new CarDeatailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            fragment.setArguments(bundle);

            ((CarActivity) getActivity()).onAddFragment(fragment, R.anim.fadein, R.anim.fadeout,
                    true, CarDeatailFragment.TAG);
        });
        back.setOnClickListener(this);



        //اگر کاربر لاگین نکرده باشد پیام خطا نشان داده میشود
        if (userId == 0) {
            loading.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            linearLayoutNotLogIn.setVisibility(View.VISIBLE);
        }
    }
    public void hideSoftKeyBoard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onCarReceived(CarListModel model) {
        adapter.add(model);

    }

    @Override
    public void onPresenterStart() {
        list.setLayoutManager(manager);
        list.setAdapter(adapter);
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
            presenter.start(userId)));
    }

    @Override
    public void onFinish() {
        if (adapter.getItemCount() == 0 && userId != 0) {
            loading.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            linearLayoutNoItemMessage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void loadingState(boolean loading) {
        TransitionManager.beginDelayedTransition((ViewGroup) this.loading.getRootView());
        this.loading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgUsersCarsBack:
                ((CarActivity) getActivity()).onBackPressed();
                break;
        }
    }
}
