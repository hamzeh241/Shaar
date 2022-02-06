package ir.tdaapp.diako.shaar.Fruits.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.OrderResult;
import ir.tdaapp.diako.shaar.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

  Context context;
  ArrayList<OrderResult> models;
  OnFruitOrderListener listener;

  public interface OnFruitOrderListener {
    void onClick(OrderResult model, int position);
  }

  public OrdersAdapter(Context context, OnFruitOrderListener listener) {
    this.context = context;
    this.listener = listener;
    models = new ArrayList<>();
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item_fruit_order, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    OrderResult model = models.get(position);

    holder.name.setText(new StringBuilder()
      .append("سفارش شماره ")
      .append(model.getId()).toString());

    holder.price.setText(new StringBuilder()
      .append((int) model.getTotalPrice())
      .append(" تومان").toString());
    holder.status.setText(new StringBuilder()
      .append("وضعیت سفارش: ")
      .append(model.getStatusBasket()).toString());

    holder.itemView.setOnClickListener(v -> listener.onClick(model, position));
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void add(OrderResult model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, price, status;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);

      name = itemView.findViewById(R.id.txtOrderName);
      price = itemView.findViewById(R.id.txtOrderPrice);
      status = itemView.findViewById(R.id.txtOrderStatus);
    }
  }
}
