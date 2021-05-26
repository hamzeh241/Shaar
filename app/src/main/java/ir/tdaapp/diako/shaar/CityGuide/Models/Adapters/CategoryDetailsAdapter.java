package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.CityGuideBaseApi;
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
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details, parent, false);
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
    holder.ratingBar.setRating(model.getRating());
    String imageName = model.getImageUrl();
    Glide.with(context)
      .load(CityGuideBaseApi.API_IMAGE + imageName)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.image);

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
    ImageView  image;
    LinearLayout rootLayout;
    RatingBar ratingBar;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    private void findView(View itemView) {
      title = itemView.findViewById(R.id.txtCategoryTitle);
      address = itemView.findViewById(R.id.txtCategoryAddress);
      rateCount = itemView.findViewById(R.id.txtCategoryDetailsRateCount);
      rootLayout = itemView.findViewById(R.id.categoryItemRootLayoutt);
      ratingBar = itemView.findViewById(R.id.categoryDetailsRatingBar);
      image = itemView.findViewById(R.id.imgCategoryDetail);
    }
  }
}
