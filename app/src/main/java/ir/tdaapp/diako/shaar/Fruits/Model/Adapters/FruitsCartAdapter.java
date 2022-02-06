package ir.tdaapp.diako.shaar.Fruits.Model.Adapters;

import android.content.Context;
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

public class FruitsCartAdapter extends RecyclerView.Adapter<FruitsCartAdapter.ViewHolder> {

  public FruitsCartAdapter(Context context, FruitsCartListener listener) {
    this.context = context;
    this.listener = listener;
    models = new ArrayList<>();
  }

  public ArrayList<FruitModel> getModels() {
    return models;
  }

  public interface FruitsCartListener {
    void onWeightIncrease(FruitModel model, int pos);

    void onWeightDecrease(FruitModel model, int pos);

    void onItemDelete(FruitModel model, int pos);
  }

  Context context;
  ArrayList<FruitModel> models;
  FruitsCartListener listener;

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_fruit_cart_item, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    FruitModel model = models.get(position);

    if (model.isTypeEnum())
      holder.name.setText(model.getName() + " " + model.getRange() + " کیلو");
    else holder.name.setText(model.getName());
    holder.price.setText((int) model.getPrice() + " تومان");
    holder.weight.setText("" + model.getWeight());

    holder.weightName.setText(model.isTypeEnum() ? "عدد" : "کیلو");

    holder.increase.setOnClickListener(v -> {
      holder.weight.setText((Double.parseDouble(holder.weight.getText().toString()) + 0.5d) + "");
      listener.onWeightIncrease(model, position);
    });
    holder.decrease.setOnClickListener(v -> {
      holder.weight.setText((Double.parseDouble(holder.weight.getText().toString()) - 0.5d) + "");
      listener.onWeightDecrease(model, position);
    });
    holder.delete.setOnClickListener(v -> {
      listener.onItemDelete(model, position);
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

    ImageView imageView;
    TextView name, price, weight, weightName;
    ImageView increase, decrease;
    ImageButton delete;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      imageView = itemView.findViewById(R.id.imgFruit);
      delete = itemView.findViewById(R.id.imgDelete);
      name = itemView.findViewById(R.id.txtName);
      price = itemView.findViewById(R.id.txtPrice);
      weightName = itemView.findViewById(R.id.txtWeightName);
      weight = itemView.findViewById(R.id.txtWeight);
      increase = itemView.findViewById(R.id.imgIncrease);
      decrease = itemView.findViewById(R.id.imgDecrease);
    }
  }
}
