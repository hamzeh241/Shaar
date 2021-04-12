package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
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
    holder.imageView.setImageResource(R.drawable.a);
  }

  @Override
  public int getItemCount() {
    return photos.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    OnItemClick clickListener;

    ImageView imageView;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      findView(itemView);
    }

    private void findView(View view) {
      imageView = view.findViewById(R.id.imgCategoryItemImage);
    }
  }
}
