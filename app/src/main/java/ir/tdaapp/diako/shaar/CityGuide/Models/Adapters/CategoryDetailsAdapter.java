package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {

  OnItemClick clickListener;
  ArrayList<CategoryDetailsModel> models;

  public CategoryDetailsAdapter(ArrayList<CategoryDetailsModel> models, OnItemClick clickListener) {
    this.clickListener = clickListener;
    this.models = models;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details, parent, false);

    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryDetailsModel model = models.get(position);

    holder.title.setText(model.getTitle());
    holder.address.setText(model.getAddress());
    holder.rateCount.setText(model.getRateCount() + " رای");

    int resId = model.isFavorite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
    holder.favorite.setImageResource(resId);
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    OnItemClick clickListener;

    TextView title, address, rateCount;
    ImageView favorite;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
      findView(itemView);
    }

    private void findView(View itemView) {
      title = itemView.findViewById(R.id.txtCategoryTitle);
      address = itemView.findViewById(R.id.txtCategoryAddress);
      rateCount = itemView.findViewById(R.id.txtCategoryDetailsRateCount);
      favorite = itemView.findViewById(R.id.imgCategoryDetailsFavorite);
    }

    @Override
    public void onClick(View view) {
      clickListener.onClick(view, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View view) {
      clickListener.onLongClick(view, getAdapterPosition());
      return true;
    }
  }
}
