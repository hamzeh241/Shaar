package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.UserItemsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.CityGuide.Presenters.UserItemsFragmentPresenter;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.R;

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (adapter.getItemCount() == 0 ){
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
    public void onError(String s) {

    }

    @Override
    public void onFinished() {

    }
}
