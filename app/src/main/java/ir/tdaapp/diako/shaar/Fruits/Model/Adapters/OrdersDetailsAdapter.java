package ir.tdaapp.diako.shaar.Fruits.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.tdaapp.diako.shaar.Fruits.Model.Repositories.Server.FruitsBaseRepository;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderDetailsResult;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.R;

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.ViewHolder> {

  Context context;
  ArrayList<OrderDetailsResult> models;

  public OrdersDetailsAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_fruit_order_details, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    OrderDetailsResult model = models.get(position);

    holder.name.setText(new StringBuilder(model.getFruitName())
      .append(" ")
      .append(model.isTypeEnum() ? model.getRange() + " کیلو" : "").toString());
    holder.weight.setText(new StringBuilder(String.valueOf(model.getWeight())).append(model.isTypeEnum() ? " عدد" : " کیلو").toString());
    holder.price.setText(new StringBuilder(String.valueOf(model.getUnitPrice())).append(" تومان"));

    String imageUrl = FruitsBaseRepository.API_IMAGE + model.getImageUrl();
    Glide.with(context)
      .load(imageUrl)
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void add(OrderDetailsResult model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, price, weight;
    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.txtFruitName);
      price = itemView.findViewById(R.id.txtUnitPrice);
      weight = itemView.findViewById(R.id.txtWeight);
      imageView = itemView.findViewById(R.id.imgFruit);
    }
  }
}
