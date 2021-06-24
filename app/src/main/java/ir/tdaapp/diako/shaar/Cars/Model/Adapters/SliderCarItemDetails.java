package ir.tdaapp.diako.shaar.Cars.Model.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarDetailsPhotoModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnGlideImageListener;
import ir.tdaapp.diako.shaar.R;

public class SliderCarItemDetails extends RecyclerView.Adapter<SliderCarItemDetails.ViewHolder> {

  Context context;
  ArrayList<CarDetailsPhotoModel> models;
  OnGlideImageListener glideListener;

  public SliderCarItemDetails(Context context, ArrayList<CarDetailsPhotoModel> models, OnGlideImageListener glideListener) {
    this.context = context;
    this.models = models;
    this.glideListener = glideListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.slider_category_item_details, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CarDetailsPhotoModel item = models.get(position);
    String imageUrl = CarBaseApi.API_IMAGE_CAR + item.getImageName().replace("\"", "");
    Glide.with(context)
      .load(imageUrl)
      .listener(new RequestListener<Drawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
          glideListener.onLoadState(false);
          return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
          glideListener.onLoadState(true);
          return false;
        }
      })
      .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    PhotoView imageView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.imgSliderCategoryItemDetails);
    }
  }
}
