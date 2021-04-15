package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {

  Context context;
  onCategoryItemClick clickListener;
  ArrayList<CategoryDetailsModel> models;

  public CategoryDetailsAdapter(Context context) {
    this.models = new ArrayList<>();
    this.context = context;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details, parent, false);
    return new ViewHolder(view);
  }

  public void add(CategoryDetailsModel categoryDetailsModels) {
    models.add(categoryDetailsModels);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  public void setOnItemClick(onCategoryItemClick clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryDetailsModel model = (CategoryDetailsModel) models.get(position);

    holder.title.setText(model.getTitle());
    holder.address.setText(model.getAddress());
    holder.rateCount.setText(model.getRateCount() + " رای");

    int resId = model.isFavorite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
    holder.favorite.setImageResource(resId);
    holder.favorite.setOnClickListener(v -> {
      clickListener.onFavorite(model);
    });
    holder.rootLayout.setOnClickListener(v -> {
      clickListener.onClick(model);
    });
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView title, address, rateCount;
    ImageView favorite;
    LinearLayout rootLayout;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    private void findView(View itemView) {
      title = itemView.findViewById(R.id.txtCategoryTitle);
      address = itemView.findViewById(R.id.txtCategoryAddress);
      rateCount = itemView.findViewById(R.id.txtCategoryDetailsRateCount);
      favorite = itemView.findViewById(R.id.imgCategoryDetailsFavorite);
      rootLayout = itemView.findViewById(R.id.categoryItemRootLayout);
    }
  }
}
