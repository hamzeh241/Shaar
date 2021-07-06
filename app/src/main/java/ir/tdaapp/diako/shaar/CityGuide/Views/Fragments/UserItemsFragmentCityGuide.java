package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
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

import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.UserItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.UserItemsFragmentPresenter;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class UserItemsFragmentCityGuide extends CityGuideBaseFragment implements UserItemsFragmentService {

    public static final String TAG = "UserItemsFragment";

    UserItemsFragmentPresenter presenter;

    RecyclerView list;
    ProgressBar loading;
    ImageButton back;

    LinearLayout linearLayoutNoItemMessage;
    LinearLayout linearLayoutNotLogIn;

    int userId;

    LinearLayoutManager layoutManager;
    CategoryDetailsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_items, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View view) {
        presenter = new UserItemsFragmentPresenter(getContext(), this);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new CategoryDetailsAdapter(getContext());
        back = view.findViewById(R.id.imageButton);
        list = view.findViewById(R.id.userItemList);
        loading = view.findViewById(R.id.userItemLoading);
        linearLayoutNotLogIn = view.findViewById(R.id.no_item_to_show_city_guide);
        userId = new User(getContext()).GetUserId();
        linearLayoutNoItemMessage = view.findViewById(R.id.no_item_messag_layout);
    }

    private void implement() {
        presenter.start(new User(getContext()).GetUserId());
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        hideSoftKeyBoard();
        adapter.setOnItemClick(model -> {
            CategoryItemDetailsFragmentCityGuide fragment = new CategoryItemDetailsFragmentCityGuide();
            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            fragment.setArguments(bundle);
            ((GuideActivity) getActivity()).onAddFragment(fragment, 0, 0, true, CategoryItemDetailsFragmentCityGuide.TAG);
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //اگر کاربر لاگین نکرده باشد پیام خطا نشان داده میشود
        if (userId == 0) {
            loading.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            linearLayoutNotLogIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadingState(boolean state) {
        loading.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemReceived(CategoryDetailsModel model) {
        if (userId != 0) {
            adapter.add(model);
            list.setAdapter(adapter);
            linearLayoutNoItemMessage.setVisibility(View.GONE);
        }
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
    public void onFinished() {

        if (adapter.getItemCount() == 0 & userId != 0) {
            loading.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            linearLayoutNoItemMessage.setVisibility(View.VISIBLE);
        }
    }
    public void hideSoftKeyBoard() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
