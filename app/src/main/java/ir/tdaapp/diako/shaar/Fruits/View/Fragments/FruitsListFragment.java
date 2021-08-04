package ir.tdaapp.diako.shaar.Fruits.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.View.Dialogs.FruitDetailsDialog;
import ir.tdaapp.diako.shaar.R;

public class FruitsListFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "FruitsListFragment";

    RecyclerView chipsList, newFruitsList;
    EditText searchBar;
    ImageButton filter, search, back;
    FloatingActionButton cart;

    LinearLayoutManager chipsManager, fruitsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fruits_list, container, false);

        findView(view);
        implement();

        return view;
    }

    private void findView(View itemView) {
        chipsList = itemView.findViewById(R.id.fruitChipsList);
        newFruitsList = itemView.findViewById(R.id.newFruitsList);
        searchBar = itemView.findViewById(R.id.edtCarListSearch);
        filter = itemView.findViewById(R.id.imgCarListFilter);
        search = itemView.findViewById(R.id.btnCarListSearch);
        back = itemView.findViewById(R.id.imgCarListBack);
        cart = itemView.findViewById(R.id.fabCart);

        chipsManager = new LinearLayoutManager(getContext());
        fruitsManager = new LinearLayoutManager(getContext());
    }

    private void implement() {
        chipsManager.setOrientation(RecyclerView.HORIZONTAL);

        cart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabCart:
                FruitModel model = new FruitModel();
                model.setId(2);
                model.setName("موز استرالیای");
                model.setPrice("12000 تومان");
                model.setImageUrl("12000 تومان");

                FruitDetailsDialog detailsDialog = new FruitDetailsDialog(model);
                detailsDialog.show(getActivity().getSupportFragmentManager(), FruitDetailsDialog.TAG);
                break;
        }
    }
}
