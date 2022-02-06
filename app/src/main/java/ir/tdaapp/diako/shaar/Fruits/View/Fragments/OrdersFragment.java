package ir.tdaapp.diako.shaar.Fruits.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.Fruits.Model.Adapters.OrdersAdapter;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.OrdersFragmentService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.Fruits.Presenter.OrdersFragmentPresenter;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class OrdersFragment extends BaseFragment implements OrdersFragmentService {

  public static final String TAG = "OrdersFragment";

  ImageButton back;
  RecyclerView list;
  ProgressBar loading;
  View noItem;

  OrdersAdapter adapter;
  LinearLayoutManager manager;

  OrdersFragmentPresenter presenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_orders, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(@NonNull View itemView) {
    presenter = new OrdersFragmentPresenter(requireContext(), this);

    manager = new LinearLayoutManager(requireContext());

    list = itemView.findViewById(R.id.ordersList);
    back = itemView.findViewById(R.id.imgBack);
    loading = itemView.findViewById(R.id.loading);
    noItem = itemView.findViewById(R.id.includeNoItem);
  }

  private void implement() {
    presenter.start(new User(requireContext()).getUserId());

    back.setOnClickListener(v -> requireActivity().onBackPressed());
  }

  @Override
  public void onPresenterStart() {
    adapter = new OrdersAdapter(requireContext(), (model, position) -> {
      OrderDetailsFragment fragment = new OrderDetailsFragment();
      Bundle bundle = new Bundle();
      bundle.putInt("ID", model.getId());
      fragment.setArguments(bundle);

      ((FruitsActivity) requireActivity()).onAddFragment(fragment,
        0, 0, true, OrderDetailsFragment.TAG);
    });

    list.setLayoutManager(manager);
  }

  @Override
  public void onLoading(boolean state) {
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
    list.setVisibility(state ? View.GONE : View.VISIBLE);
  }

  @Override
  public void onOrderReceived(OrderResult result) {
    adapter.add(result);
  }

  @Override
  public void onError(ResaultCode code) {
    String error = "";
    String title = "";
    @DrawableRes int imageRes = R.drawable.ic_warning;

    switch (code) {
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
      .setClickListener(() -> {
        adapter.clear();
        presenter.start(new User(requireContext()).getUserId());
      }));
  }

  @Override
  public void onFinish() {
    list.setAdapter(adapter);
    if (adapter.getItemCount() == 0) {
      list.setVisibility(View.GONE);
      noItem.setVisibility(View.VISIBLE);
    } else {
      list.setVisibility(View.VISIBLE);
      noItem.setVisibility(View.GONE);
    }
  }
}
