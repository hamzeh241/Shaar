package ir.tdaapp.diako.shaar.Cars.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onCarListClickListener;
import ir.tdaapp.diako.shaar.Cars.Model.Utilities.CarBaseApi;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.R;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

  Context context;
  ArrayList<CarListModel> models;
  onCarListClickListener clickListener;

  public CarListAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.recycler_car_list, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CarListModel model = models.get(position);
    holder.name.setText(model.getTitle());
    holder.price.setText(model.getPrice());
    holder.mileage.setText(model.getMileage());
    holder.color.setText(model.getColor());
    holder.productionYear.setText(model.getProductionYear());
    Glide.with(context)
      .load(CarBaseApi.API_IMAGE + model.getImageUrl())
      .placeholder(R.drawable.ic_baseline_sync_24)
      .error(R.drawable.ic_baseline_running_with_errors_24)
      .into(holder.imageView);

    holder.root.setOnClickListener(v -> {
      clickListener.onClick(model, position);
    });
  }

  public void add(CarListModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void setClickListener(onCarListClickListener clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    ViewGroup root;
    TextView name, price, color, productionYear, mileage;
    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    private void findView(View view) {
      name = view.findViewById(R.id.txtCarListName);
      price = view.findViewById(R.id.txtCarListPrice);
      color = view.findViewById(R.id.txtCarListColor);
      productionYear = view.findViewById(R.id.txtCarListProductionYear);
      mileage = view.findViewById(R.id.txtCarListMileage);
      imageView = view.findViewById(R.id.imgCarList);
      root = view.findViewById(R.id.carListRoot);
    }
  }
}
