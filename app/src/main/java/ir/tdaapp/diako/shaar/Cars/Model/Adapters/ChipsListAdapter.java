package ir.tdaapp.diako.shaar.Cars.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.Cars.Model.Services.OnChipListener;
import ir.tdaapp.diako.shaar.Cars.Model.Services.onCarListClickListener;
import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.R;

public class ChipsListAdapter extends RecyclerView.Adapter<ChipsListAdapter.ViewHolder> {

    Context context;
    ArrayList<CarChipsListModel> models;
    onCarListClickListener clickListener;
    OnChipListener chipListener;


    public ChipsListAdapter(Context context) {
        this.context = context;
        this.models = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_details_chips, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarChipsListModel model = models.get(position);

        holder.root.setBackgroundResource(model.isSelected() ? R.drawable.red_chip_background_borderline : R.drawable.red_chip_background);
        holder.textView.setText(model.getTitle());
        holder.root.setOnClickListener(v -> {
            chipListener.OnClick(model, position);


        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setSelected(int position) {
        models.get(position).setSelected(true);
        notifyItemChanged(position);
    }

    public void clearSelected() {
        for (CarChipsListModel model : models) {
            model.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void setClickListener(onCarListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setChipListener(OnChipListener chipListener) {
        this.chipListener = chipListener;
    }

    public void add(CarChipsListModel model) {
        models.add(model);
        notifyItemInserted(models.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ViewGroup root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtCategoryDetailsChip);
            root = itemView.findViewById(R.id.chipRoot);
        }
    }
}
