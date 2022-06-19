package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CityModel;
import ir.tdaapp.diako.shaar.R;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

  private final ArrayList<CityModel> cities;
  private final OnItemClick clickListener;

  public CityAdapter(ArrayList<CityModel> cities, OnItemClick clickListener) {
    this.cities = cities;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_city_layout, parent, false);

    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.root.setText(cities.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return cities.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnItemClick clickListener;

    TextView root;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      findView(itemView);
      itemView.setOnClickListener(this);
    }

    private void findView(View view) {
      root = view.findViewById(R.id.txtCity);
    }

    @Override
    public void onClick(View v) {
      clickListener.onClick(v, getAdapterPosition());
    }
  }
}
