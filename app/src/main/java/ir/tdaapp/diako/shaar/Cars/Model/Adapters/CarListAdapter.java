package ir.tdaapp.diako.shaar.Cars.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.R;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

  Context context;
  ArrayList<CarListModel> models;

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

  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    TextView name, price, color, productionYear, mileage;
    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    private void findView(View view) {
    }
  }
}
