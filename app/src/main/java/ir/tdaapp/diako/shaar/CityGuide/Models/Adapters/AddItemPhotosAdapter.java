package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onAddItemPhotosAdapterListener;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.AddItemPhotosModel;
import ir.tdaapp.diako.shaar.R;

public class AddItemPhotosAdapter extends RecyclerView.Adapter<AddItemPhotosAdapter.ViewHolder> {

  Context context;
  ArrayList<AddItemPhotosModel> models;
  onAddItemPhotosAdapterListener listener;

  public AddItemPhotosAdapter(Context context) {
    this.context = context;
    this.models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_add_item_photos, parent, false);

    return new ViewHolder(view);
  }

  public void setListener(onAddItemPhotosAdapterListener listener) {
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    AddItemPhotosModel pictureUri = models.get(position);
    Glide.with(context)
      .load(pictureUri.getUri().getPath())
      .into(holder.imageView);

    holder.remove.setOnClickListener(v -> {
      listener.remove(pictureUri, position);
      remove(position);
    });
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void add(AddItemPhotosModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  private void remove(int position) {
    models.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, models.size());
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ImageButton remove;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      imageView = itemView.findViewById(R.id.imgRecyclerAddItem);
      remove = itemView.findViewById(R.id.btnRecyclerAddItemRemove);
    }
  }
}
