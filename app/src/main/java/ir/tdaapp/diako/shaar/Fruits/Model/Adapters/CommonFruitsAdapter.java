package ir.tdaapp.diako.shaar.Fruits.Model.Adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitsBaseRepository;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.R;

public class CommonFruitsAdapter extends RecyclerView.Adapter<CommonFruitsAdapter.ViewHolder> {

  Context context;
  CommonFruitsListener listener;
  ArrayList<FruitModel> models;

  public CommonFruitsAdapter(Context context, CommonFruitsListener listener) {
    this.context = context;
    this.listener = listener;
    models = new ArrayList<>();
  }

  public interface CommonFruitsListener {
    void onClick(FruitModel model, int pos);

    void onAddToCart(FruitModel model, int pos);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_fruit_list_item_all, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    FruitModel model = models.get(position);

    holder.name.setText(model.getName());
    holder.price.setText((int) model.getPrice() + " تومان");

    holder.addToCart.setOnClickListener(v -> {
      listener.onAddToCart(model, position);
    });

    holder.itemView.setOnClickListener(v -> {
      listener.onClick(model, position);
    });

    String imageUrl = FruitsBaseRepository.API_IMAGE + model.getImageUrl();
    Glide.with(context)
      .load(imageUrl)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.imageView);
  }

  public void add(FruitModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, price;
    ImageView imageView;
    ImageButton addToCart;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.txtFruitName);
      price = itemView.findViewById(R.id.txtFruitPrice);
      imageView = itemView.findViewById(R.id.imgFruit);
      addToCart = itemView.findViewById(R.id.imgAddToCart);
    }
  }
}
