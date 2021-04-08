package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;

public class CategoryFragment extends BaseFragment {

  public static final String TAG = "CategoryFragment";

  private RecyclerView recyclerView;

  private GridLayoutManager layoutManager;
  private CategoryAdapter adapter;


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category, container, false);

    findView(view);
    implement();




    return view;
  }

  private void findView(View itemView) {
    recyclerView = itemView.findViewById(R.id.categoryList);
    layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
  }

  private void implement() {
    ArrayList<CategoryModel> models = new ArrayList<>();

    for (int i = 0; i < 25; i++) {
      CategoryModel model = new CategoryModel(1, "salam", "khubi");
      models.add(model);
    }

    adapter = new CategoryAdapter(models, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        ((GuideActivity)getActivity()).onAddFragment(new CategoryDetailsFragment(),0,0,false,CategoryDetailsFragment.TAG);
      }

      @Override
      public void onLongClick(View view, int position) {

      }
    });
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }
}
