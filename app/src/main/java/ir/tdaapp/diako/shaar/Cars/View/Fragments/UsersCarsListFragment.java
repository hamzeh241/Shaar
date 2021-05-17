package ir.tdaapp.diako.shaar.Cars.View.Fragments;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.Cars.Model.Adapters.CarListAdapter;
import ir.tdaapp.diako.shaar.Cars.Model.Services.UsersCarsFragmentService;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onCarListClickListener;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseFragment;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Cars.Presenter.UsersCarsFragmentPresenter;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;

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

        userId = new User(getContext()).GetUserId();

        list = view.findViewById(R.id.usersCarsList);
        loading = view.findViewById(R.id.userCarsLoading);
        back = view.findViewById(R.id.imgUsersCarsBack);

        adapter = new CarListAdapter(getContext(), CarListAdapter.USERS_LIST);
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        linearLayoutNoItemMessage = view.findViewById(R.id.no_item_message_use_car);
    }

    private void implement() {
        presenter.start(new User(getActivity()).GetUserId());

        adapter.setClickListener((model, position) -> {
            CarDeatailFragment fragment = new CarDeatailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ID", model.getId());
            fragment.setArguments(bundle);

            ((CarActivity) getActivity()).onAddFragment(fragment, R.anim.fadein, R.anim.fadeout,
                    true, CarDeatailFragment.TAG);
        });
        back.setOnClickListener(this);


        if (adapter.getItemCount() == 0){
            loading.setVisibility(View.GONE);
        }

        //اگر کاربر لاگین نکرده باشد پیام خطا نشان داده میشود
        if (userId == 0) {
            loading.setVisibility(View.GONE);
            list.setVisibility(View.GONE);
            linearLayoutNotLogIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCarReceived(CarListModel model) {
        if (userId != 0){
            adapter.add(model);
            linearLayoutNoItemMessage.setVisibility(View.GONE);
        }



    }

    @Override
    public void onPresenterStart() {
        if (userId != 0){
            list.setLayoutManager(manager);
            list.setAdapter(adapter);

        }


    }

    @Override
    public void onError(String s) {

    }

    @Override
    public void onFinish() {

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
