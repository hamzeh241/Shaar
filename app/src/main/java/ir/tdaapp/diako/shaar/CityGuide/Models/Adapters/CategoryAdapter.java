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
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

  ArrayList<CategoryModel> models;
  OnItemClick clickListener;

  public CategoryAdapter(ArrayList<CategoryModel> models, OnItemClick clickListener) {
    this.models = models;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category, parent, false);


    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryModel model = models.get(position);

    holder.title.setText(model.getTitle());
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    OnItemClick clickListener;

    TextView title;
    ImageView img;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      itemView.setOnClickListener(this);
      itemView.setOnLongClickListener(this);
      title = itemView.findViewById(R.id.txtCategory);
      img = itemView.findViewById(R.id.imgCategory);
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
