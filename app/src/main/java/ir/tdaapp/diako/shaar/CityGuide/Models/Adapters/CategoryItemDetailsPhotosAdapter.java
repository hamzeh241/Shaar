package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemDetailsPhotosAdapter extends RecyclerView.Adapter<CategoryItemDetailsPhotosAdapter.ViewHolder> {

  private ArrayList<CategoryItemDetailsPhotoModel> photos;
  private OnItemClick clickListener;

  public CategoryItemDetailsPhotosAdapter(ArrayList<CategoryItemDetailsPhotoModel> photos, OnItemClick clickListener) {
    this.photos = photos;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_item_photos, parent, false);

    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryItemDetailsPhotoModel item = photos.get(position);

    Glide.with(holder.imageView.getContext())
      .load(BaseApi.API_IMAGE + item.getImageName())
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return photos.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnItemClick clickListener;

    ImageView imageView;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      findView(itemView);
      itemView.setOnClickListener(this);
    }

    private void findView(View view) {
      imageView = view.findViewById(R.id.imgCategoryItemImage);
    }

    @Override
    public void onClick(View v) {
      clickListener.onClick(v, getAdapterPosition());
    }
  }
}
