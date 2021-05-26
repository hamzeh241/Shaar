package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarChipsListModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryChipClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsChipsAdapter extends RecyclerView.Adapter<CategoryDetailsChipsAdapter.ViewHolder> {

    Context context;
    ArrayList<CategoryDetailsChipModel> models;
    onCategoryChipClick clickListener;


    public CategoryDetailsChipsAdapter(Context context) {
        this.context = context;
        models = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details_chips, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDetailsChipModel item = models.get(position);

        holder.rootLayout.setBackgroundResource(item.isSelected() ? R.drawable.red_chip_background_borderline:R.drawable.red_chip_background);
        holder.title.setText(item.getTitle());
        holder.rootLayout.setOnClickListener(v -> {
            clickListener.onClick(item, position);
        });
    }

    public void setSelected(int position) {
        models.get(position).setSelected(true);
        notifyItemChanged(position);
    }

    public void clearSelected() {
        for (CategoryDetailsChipModel model : models) {
            model.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public CategoryDetailsChipModel getItemAt(int position) {
        return models.get(position);
    }

    public void add(CategoryDetailsChipModel model) {
        models.add(model);
        notifyItemInserted(models.size());
    }

    public void setOnItemClick(onCategoryChipClick clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        LinearLayout rootLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtCategoryDetailsChip);
            rootLayout = itemView.findViewById(R.id.chipRoot);
        }

    }
}
