package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

  Context context;
  ArrayList<CategoryModel> models;
  onCategoryClick clickListener;

  public CategoryAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category, parent, false);

    return new ViewHolder(view);
  }

  public void add(CategoryModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  public void setOnItemClick(onCategoryClick clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryModel model = models.get(position);

    holder.title.setText(model.getTitle());
    holder.rootLayout.setOnClickListener(v ->
      clickListener.onClick(models.get(position)));
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    ImageView img;
    LinearLayout rootLayout;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      title = itemView.findViewById(R.id.txtCategory);
      img = itemView.findViewById(R.id.imgCategory);
      rootLayout = itemView.findViewById(R.id.categoryRoot);
    }
  }
}
