package ir.tdaapp.diako.shaar.Fruits.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.R;

public class FruitCartFragment extends BaseFragment {

    TextView totalItems, totalPrice;
    ImageButton back;
    RecyclerView list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruit_cart, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View itemView) {

    }

    private void implement() {

    }
}
