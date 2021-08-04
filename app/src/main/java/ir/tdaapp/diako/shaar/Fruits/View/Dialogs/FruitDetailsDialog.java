package ir.tdaapp.diako.shaar.Fruits.View.Dialogs;

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

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.R;

public class FruitDetailsDialog extends DialogFragment {

    public static final String TAG = "FruitDetailsDialog";

    TextView name, price;
    LinearLayout addToCart;
    ImageView image;

    int id;
    String strName,
            strPrice, strImage;

    public FruitDetailsDialog(FruitModel model) {
        id = model.getId();
        strName = model.getName();
        strPrice = model.getPrice();
        strImage = model.getImageUrl();
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
        image = itemView.findViewById(R.id.imgFruit);
    }

    private void implement() {

        name.setText(strName);
        price.setText(strPrice);
//        addToCart.setOnClickListener(v -> {
//            dismiss();
//        });
    }

    @Override
    public int getTheme() {
        return R.style.RoundedCornersDialog;
    }
}
