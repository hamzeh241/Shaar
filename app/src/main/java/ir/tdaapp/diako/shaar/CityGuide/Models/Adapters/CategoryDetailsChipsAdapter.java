package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsChipsAdapter extends RecyclerView.Adapter<CategoryDetailsChipsAdapter.ViewHolder> {

  ArrayList<CategoryDetailsChipModel> model;
  OnItemClick clickListener;

  public CategoryDetailsChipsAdapter(ArrayList<CategoryDetailsChipModel> model, OnItemClick clickListener) {
    this.model = model;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details_chips, parent, false);

    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryDetailsChipModel item = model.get(position);

    holder.title.setText(item.getTitle());
  }

  @Override
  public int getItemCount() {
    return model.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    OnItemClick clickListener;

    TextView title;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
      title = itemView.findViewById(R.id.txtCategoryDetailsChip);
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
