package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemDetailsCommentsAdapter extends RecyclerView.Adapter<CategoryItemDetailsCommentsAdapter.ViewHolder> {

  ArrayList<CategoryItemDetailsCommentsModel> models;
  OnItemClick clickListener;

  public CategoryItemDetailsCommentsAdapter(ArrayList<CategoryItemDetailsCommentsModel> models, OnItemClick clickListener) {
    this.models = models;
    this.clickListener = clickListener;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_item_details_comments, parent, false);

    return new ViewHolder(view, clickListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryItemDetailsCommentsModel item = models.get(position);

    holder.userAddress.setText(item.getUser());
    holder.message.setText(item.getMessage());
    holder.date.setText(item.getDate());
    holder.dislikeCount.setText("" + item.getDislikes());
    holder.likeCount.setText("" + item.getLikes());
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    OnItemClick clickListener;

    ImageView avatar;
    TextView userAddress, date, message, likeCount, dislikeCount;

    public ViewHolder(@NonNull View itemView, OnItemClick clickListener) {
      super(itemView);
      this.clickListener = clickListener;
      findView(itemView);
    }

    private void findView(View view) {
      avatar = view.findViewById(R.id.imgCommentAvatar);
      userAddress = view.findViewById(R.id.txtCommentUserAddress);
      date = view.findViewById(R.id.txtCommentDate);
      message = view.findViewById(R.id.txtCommentMessage);
      likeCount = view.findViewById(R.id.txtCommentLikeCount);
      dislikeCount = view.findViewById(R.id.txtCommentDislikeCount);
    }

    @Override
    public void onClick(View v) {
      clickListener.onClick(v, getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
      clickListener.onLongClick(v, getAdapterPosition());
      return true;
    }
  }
}
