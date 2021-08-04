package ir.tdaapp.diako.shaar.Fruits.Model.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.CarListModel;
import ir.tdaapp.diako.shaar.Fruits.Model.ViewModels.FruitModel;
import ir.tdaapp.diako.shaar.R;
import ir.tdaapp.li_utility.Codes.Replace;

public class FruitsListAdapter extends RecyclerView.Adapter<FruitsListAdapter.ViewHolder> {

    Context context;
    ArrayList<FruitModel> models;
    OnFruitListClick listener;

    public FruitsListAdapter(Context context, OnFruitListClick listener) {
        this.context = context;
        this.listener = listener;
        models = new ArrayList<>();
    }

    public interface OnFruitListClick {
        void onClick(FruitModel model, int position);

        void onAddToCart(FruitModel model);

        void onCartCleared(FruitModel model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_fruit_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitsListAdapter.ViewHolder holder, int position) {
        FruitModel item = models.get(position);

        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice());

        // TODO: 7/24/2021 imageUrl string should be added
//        String imageUrl = "";
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.ic_baseline_sync_24)
//                .error(R.drawable.ic_baseline_running_with_errors_24)
//                .into(holder.imageView);

        holder.increase.setOnClickListener(v -> {
            increaseCount(holder);
        });

        holder.decrease.setOnClickListener(v -> {
            decreaseCount(holder);
        });

        holder.clear.setOnClickListener(v -> {
            listener.onCartCleared(item);
            holder.cartDetails.setVisibility(View.GONE);
            holder.addToCart.setVisibility(View.VISIBLE);
        });

        holder.addToCart.setOnClickListener(v -> {
            listener.onAddToCart(item);
            holder.addToCart.setVisibility(View.GONE);
            holder.cartDetails.setVisibility(View.VISIBLE);
        });

        holder.itemView.setOnClickListener(v -> listener.onClick(item, position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private void increaseCount(FruitsListAdapter.ViewHolder holder) {
        int count = Integer.parseInt(Replace.Number_fn_To_en(holder.count.getText().toString()));
        holder.count.setText((count + 1));
    }

    private void decreaseCount(FruitsListAdapter.ViewHolder holder) {
        int count = Integer.parseInt(Replace.Number_fn_To_en(holder.count.getText().toString()));
        holder.count.setText((count - 1));
    }

    public void add(FruitModel model) {
        models.add(model);
        notifyItemInserted(models.size());
    }

    public void clear() {
        models.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout addToCart, cartDetails;
        TextView name, price, count;
        ImageButton increase, decrease, clear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            findView(itemView);
        }

        private void findView(View itemView) {
            imageView = itemView.findViewById(R.id.imgFruit);

            addToCart = itemView.findViewById(R.id.addToCart);
            cartDetails = itemView.findViewById(R.id.cartDetails);

            name = itemView.findViewById(R.id.txtName);
            price = itemView.findViewById(R.id.txtPrice);
            count = itemView.findViewById(R.id.txtFruitCount);

            increase = itemView.findViewById(R.id.imgIncreaseCount);
            decrease = itemView.findViewById(R.id.imgDecreaseCount);
            clear = itemView.findViewById(R.id.imgClearAll);
        }
    }
}
