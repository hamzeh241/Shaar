package ir.tdaapp.diako.shaar.Fruits.View.Fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Cars.View.Activities.CarActivity;
import ir.tdaapp.diako.shaar.Cars.View.Fragments.AddCarFragment;
import ir.tdaapp.diako.shaar.ETC.User;
import ir.tdaapp.diako.shaar.ErrorHandling.ErrorDialog;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Login_Home;
import ir.tdaapp.diako.shaar.Fruits.Model.Adapters.FruitsCartAdapter;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database.TblCart;
import ir.tdaapp.diako.shaar.Fruits.Model.Services.FruitCartService;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.ResultFruitCart;
import ir.tdaapp.diako.shaar.Fruits.Presenter.FruitCartPresenter;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;
import ir.tdaapp.diako.shaar.Fruits.View.Dialogs.FruitCartDetailsDialog;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public class FruitCartFragment extends BaseFragment implements FruitCartService, View.OnClickListener {

  public static final String TAG = "FruitCartFragment";

  double dtotalPrice;

  TextView totalItems, totalPrice, txtSubmitBuy;
  ImageButton back, clear, orders, imgSubmitBuy;
  EditText address;
  RecyclerView list;
  LinearLayout submitBuy, cartHeader;
  ProgressBar loading;
  View noItem;

  GridLayoutManager manager;
  FruitsCartAdapter adapter;

  FruitCartPresenter presenter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_fruit_cart, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View itemView) {
    presenter = new FruitCartPresenter(requireContext(), this);
    manager = new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false);

    list = itemView.findViewById(R.id.fruitCartList);
    back = itemView.findViewById(R.id.imgBack);
    totalItems = itemView.findViewById(R.id.txtItemsInCart);
    totalPrice = itemView.findViewById(R.id.txtTotalCartPrice);
    loading = itemView.findViewById(R.id.loading);
    cartHeader = itemView.findViewById(R.id.cartHeader);
    submitBuy = itemView.findViewById(R.id.submitCart);
    clear = itemView.findViewById(R.id.imgClearList);
    orders = itemView.findViewById(R.id.imgOrderList);
    noItem = itemView.findViewById(R.id.includeNoItem);
    txtSubmitBuy = itemView.findViewById(R.id.txtSubmitBuy);
    imgSubmitBuy = itemView.findViewById(R.id.imgSubmitBuy);
    address = itemView.findViewById(R.id.edtCartAddress);
  }

  private void implement() {
    presenter.start();

    clear.setOnClickListener(this);
    orders.setOnClickListener(this);
    submitBuy.setOnClickListener(this);
    back.setOnClickListener(this);
    imgSubmitBuy.setOnClickListener(this);
  }

  @Override
  public void onPresenterStart() {
    dtotalPrice = 0.0d;
    list.setLayoutManager(manager);
    adapter = new FruitsCartAdapter(requireContext(), new FruitsCartAdapter.FruitsCartListener() {
      @Override
      public void onWeightIncrease(FruitModel model, int pos) {
        if (model.isTypeEnum()) {
          model.setWeight(model.getWeight() + 1);
          presenter.changeWeight(model);
//          presenter.start();
        } else {
          model.setWeight(model.getWeight() + 0.5);
          presenter.changeWeight(model);
//          presenter.start();
        }
      }

      @Override
      public void onWeightDecrease(FruitModel model, int pos) {
        if (model.isTypeEnum()) {
          if (model.getWeight() == 1.0d) {
            presenter.deleteItem(model);
            presenter.start();
          } else {
            model.setWeight(model.getWeight() - 1);
            presenter.changeWeight(model);
//            presenter.start();
          }
        } else {
          if (model.getWeight() == 0.5) {
            presenter.deleteItem(model);
            presenter.start();
          } else {
            model.setWeight(model.getWeight() - 0.5);
            presenter.changeWeight(model);
//            presenter.start();
          }
        }
      }

      @Override
      public void onItemDelete(FruitModel model, int pos) {
        presenter.deleteItem(model);
        presenter.start();
      }
    });
  }

  @Override
  public void onLoading(boolean state) {
    loading.setVisibility(state ? View.VISIBLE : View.GONE);
    list.setVisibility(state ? View.GONE : View.VISIBLE);
    submitBuy.setVisibility(state ? View.GONE : View.VISIBLE);
    cartHeader.setVisibility(state ? View.GONE : View.VISIBLE);
  }

  @Override
  public void onIdsReceived(List<Integer> ids) {

  }

  @Override
  public void onItemReceived(FruitModel model) {
    adapter.add(model);
  }

  @Override
  public void onSendResultReceived(ResultFruitCart result) {
    if (result.isTotalResult()) {
      presenter.clearBasket();
      presenter.start();
      Toasty.success(requireContext(), "سفارش شما با موفقیت در سیستم ثبت شد").show();

      txtSubmitBuy.setVisibility(View.VISIBLE);
      address.setVisibility(View.GONE);
      imgSubmitBuy.setVisibility(View.GONE);
    } else {
      List<FruitModel> models = result.getFruitModels();
      StringBuilder message = new StringBuilder();
      for (FruitModel model : models) {
        if (!model.isResult())
          message.append(model.getMessage()).append("\n");
      }

      Toasty.error(requireContext(), message.toString(), Toasty.LENGTH_LONG).show();
    }
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
      .setButtonText("برگشت")
      .setClickListener(() -> {
        requireActivity().onBackPressed();
      }));
  }

  @Override
  public void onFinish() {
    double tempTotalPrice = 0d;
    for (FruitModel model : adapter.getModels()) {
      tempTotalPrice += (model.getPrice() * model.getWeight());
    }

    dtotalPrice = tempTotalPrice;
    list.setAdapter(adapter);
    totalItems.setText(adapter.getItemCount() + "");
    totalPrice.setText((int) dtotalPrice + " تومان");

    if (adapter.getItemCount() == 0) {
      noItem.setVisibility(View.VISIBLE);
      list.setVisibility(View.GONE);
      submitBuy.setVisibility(View.GONE);
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.submitCart:
        if (new User(getContext()).getUserId() == 0) {
          Toasty.info(getContext(), R.string.addAccuont, Toast.LENGTH_SHORT, false).show();
          ((FruitsActivity) requireActivity()).onAddFragment(new Fragment_Login_Home(3), R.anim.fadein, R.anim.fadeout, true, Fragment_Login_Home.TAG);
        } else {
          FruitCartDetailsDialog dialog = new FruitCartDetailsDialog((address1, phone, description) -> {
            presenter.sendItems(new User(requireContext()).getUserId(), adapter.getModels(), address1, phone, description);
          });

          dialog.show(requireActivity().getSupportFragmentManager(),FruitCartDetailsDialog.TAG);
        }
        break;

      case R.id.imgOrderList:
        if (new User(getContext()).getUserId() == 0) {
          Toasty.info(requireContext(), R.string.addAccuont, Toast.LENGTH_SHORT, false).show();
          ((FruitsActivity) requireActivity()).onAddFragment(new Fragment_Login_Home(3)
            , R.anim.fadein, R.anim.fadeout, true, Fragment_Login_Home.TAG);
        } else
          ((FruitsActivity) requireActivity()).onAddFragment(new OrdersFragment(),
            0, 0, true, OrdersFragment.TAG);
        break;
      case R.id.imgClearList:
        if (adapter.getItemCount() > 0) {
          AlertDialog alertDialog = new AlertDialog.Builder(requireContext())
            .setTitle("آیا مطمئنید؟")
            .setMessage("می خواهید تمام کالا های داخل سبد را حذف کنید؟")
            .setPositiveButton("بله", (dialog, which) -> {
              presenter.clearBasket();
              presenter.start();
            })
            .setNegativeButton("خیر", (dialog, which) -> dialog.dismiss())
            .create();

          alertDialog.show();
        } else Toasty.error(requireContext(), "کالایی در سبد موجود نیست").show();

        break;
      case R.id.imgBack:
        requireActivity().onBackPressed();
        break;
    }
  }
}
