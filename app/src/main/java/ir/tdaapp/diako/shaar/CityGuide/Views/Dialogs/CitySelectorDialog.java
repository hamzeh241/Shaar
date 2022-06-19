package ir.tdaapp.diako.shaar.CityGuide.Views.Dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CityAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CityModel;
import ir.tdaapp.diako.shaar.R;

public class CitySelectorDialog extends DialogFragment {

  public static final String TAG = "CitySelectorDialog";

  private List<CityModel> models;
  private CityCallback callback;
  private CityAdapter adapter;

  public CitySelectorDialog(List<CityModel> models, CityCallback callback) {
    this.models = models;
    this.callback = callback;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.dialog_city_selector, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT);
    view.findViewById(R.id.imgClose).setOnClickListener((v) -> {
      dismiss();
    });
    adapter = new CityAdapter((ArrayList<CityModel>) models, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        callback.onItemSelected(models.get(position));
        dismiss();
      }

      @Override
      public void onLongClick(View view, int position) {
        callback.onItemSelected(models.get(position));
        dismiss();
      }
    });

    RecyclerView list = view.findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(requireContext()));
    list.setAdapter(adapter);

  }
}

