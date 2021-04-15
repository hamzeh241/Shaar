package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseViewHolder;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsLoading;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

  Context context;
  onCategoryItemClick clickListener;
  ArrayList<BaseModel> models;

  private static final int VIEW_TYPE_LOADING = 0;
  private static final int VIEW_TYPE_NORMAL = 1;
  private boolean isLoaderVisible = false;

  public CategoryDetailsAdapter(Context context) {
    this.models = new ArrayList<>();
    this.context = context;
  }

  @NonNull
  @Override
  public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEW_TYPE_NORMAL:
        return new ViewHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_details, parent, false));
      case VIEW_TYPE_LOADING:
        return new ProgressViewHolder(
          LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_item_pagination_loading, parent, false));
      default:
        return null;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (isLoaderVisible) {
      return position == models.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
    } else {
      return VIEW_TYPE_NORMAL;
    }
  }

  public void addLoading() {
    isLoaderVisible = true;
    models.add(new CategoryDetailsLoading());
    notifyItemInserted(models.size());
  }

  public void removeLoading() {
    isLoaderVisible = false;
    int position = models.size() - 1;
    CategoryDetailsLoading item = (CategoryDetailsLoading) models.get(position);
    models.remove(item);
    notifyItemRemoved(position);
  }

  public void add(CategoryDetailsModel model) {
    models.add(model);
    notifyItemInserted(models.size());
  }

  public void clear() {
    models.clear();
    notifyDataSetChanged();
  }

  public void setOnItemClick(onCategoryItemClick clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
    holder.onBind(position);
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends BaseViewHolder {

    TextView title, address, rateCount;
    ImageView favorite;
    LinearLayout rootLayout;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(int position) {
      super.onBind(position);
      CategoryDetailsModel model = (CategoryDetailsModel) models.get(position);

      title.setText(model.getTitle());
      address.setText(model.getAddress());
      rateCount.setText(model.getRateCount() + " رای");

      int resId = model.isFavorite() ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24;
      favorite.setImageResource(resId);
      favorite.setOnClickListener(v -> {
        clickListener.onFavorite(model);
      });
      rootLayout.setOnClickListener(v -> {
        clickListener.onClick(model);
      });
    }

    private void findView(View itemView) {
      title = itemView.findViewById(R.id.txtCategoryTitle);
      address = itemView.findViewById(R.id.txtCategoryAddress);
      rateCount = itemView.findViewById(R.id.txtCategoryDetailsRateCount);
      favorite = itemView.findViewById(R.id.imgCategoryDetailsFavorite);
      rootLayout = itemView.findViewById(R.id.categoryItemRootLayout);
    }
  }

  class ProgressViewHolder extends BaseViewHolder {

    ProgressBar progressBar;

    public ProgressViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    @Override
    protected void clear() {

    }

    @Override
    public void onBind(int position) {
      super.onBind(position);
    }

    private void findView(View itemView) {
      progressBar = itemView.findViewById(R.id.categoryProgressPaging);
    }
  }


}
