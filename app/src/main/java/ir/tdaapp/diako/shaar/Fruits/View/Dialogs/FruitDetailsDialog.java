package ir.tdaapp.diako.shaar.Fruits.View.Dialogs;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import es.dmoral.toasty.Toasty;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Database.TblCart;
import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitsBaseRepository;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.View.Activities.FruitsActivity;
import ir.tdaapp.diako.shaar.R;

public class FruitDetailsDialog extends DialogFragment {

  public static final String TAG = "FruitDetailsDialog";

  TextView name, price;
  LinearLayout addToCart;
  ImageView image;

  FruitModel model;

  TblCart cart;

  public FruitDetailsDialog(FruitModel model, Context context) {
    this.model = model;
    cart = new TblCart(context);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_fruit_details, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View itemView) {
    name = itemView.findViewById(R.id.txtName);
    price = itemView.findViewById(R.id.txtPrice);
    addToCart = itemView.findViewById(R.id.addToCart);
    image = itemView.findViewById(R.id.fruitImage);
  }

  private void implement() {

    name.setText(model.getName());
    price.setText(((int) model.getPrice()) + " تومان");

    String imageUrl = FruitsBaseRepository.API_IMAGE + model.getImageUrl();

    Glide.with(getContext())
      .load(imageUrl)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(image);

    addToCart.setOnClickListener(v -> {
      model.setWeight(1.0d);
      if (cart.addItem(model)) {
        Toasty.success(getContext(), R.string.addToCartSuccessfully).show();
      } else {
        Toasty.error(requireContext(), "این میوه در سبد خرید موجود است").show();
        model.setWeight(0.0d);
      }
      dismiss();
    });
  }

  @Override
  public int getTheme() {
    return R.style.RoundedCornersDialog;
  }
}
