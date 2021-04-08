package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryDetailsChipsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsFragment extends BaseFragment {

  public static final String TAG = "CategoryDetailsFragment";

  ImageButton filter, back;
  RecyclerView chipsList, detailsList;
  EditText searchBar;

  CategoryDetailsAdapter detailsAdapter;
  CategoryDetailsChipsAdapter chipsAdapter;
  ArrayList<CategoryDetailsModel> detailModels;
  ArrayList<CategoryDetailsChipModel> chipModels;
  LinearLayoutManager detailsLayoutManager, chipsLayoutManager;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_details, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    detailModels = new ArrayList<>();
    chipModels = new ArrayList<>();
    filter = view.findViewById(R.id.imgCategoryDetailsFilter);
    back = view.findViewById(R.id.imgCategoryDetailsBack);
    chipsList = view.findViewById(R.id.categoryDetailsChipsList);
    detailsList = view.findViewById(R.id.categoryDetailsList);
    searchBar = view.findViewById(R.id.edtCategoryDetailsSearch);
  }

  private void implement() {
    detailsLayoutManager = new LinearLayoutManager(getContext());
    chipsLayoutManager = new LinearLayoutManager(getContext());
    chipsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
    chipsLayoutManager.setReverseLayout(true);
    detailsList.setLayoutManager(detailsLayoutManager);
    chipsList.setLayoutManager(chipsLayoutManager);

    for (int i = 0; i < 12; i++) {
      CategoryDetailsModel model = new CategoryDetailsModel();
      model.setTitle("یک مغازه تستی");
      model.setAddress("شهرک زاگرس، کوچه ایمان، پلاک 332");
      model.setFavorite(true);
      model.setRateCount(7);
      model.setRating(3.2f);
      detailModels.add(model);
    }

    for (int i = 0; i < 10; i++) {
      CategoryDetailsChipModel model = new CategoryDetailsChipModel();
      model.setTitle("سالن کاشت ناخن");
      chipModels.add(model);
    }

    chipsAdapter = new CategoryDetailsChipsAdapter(chipModels, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(View view, int position) {
        Toast.makeText(getContext(), "LongClicked", Toast.LENGTH_SHORT).show();

      }
    });

    detailsAdapter = new CategoryDetailsAdapter(detailModels, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(View view, int position) {

        Toast.makeText(getContext(), "LongClicked", Toast.LENGTH_SHORT).show();
      }
    });

    detailsList.setAdapter(detailsAdapter);
    chipsList.setAdapter(chipsAdapter);
  }
}
