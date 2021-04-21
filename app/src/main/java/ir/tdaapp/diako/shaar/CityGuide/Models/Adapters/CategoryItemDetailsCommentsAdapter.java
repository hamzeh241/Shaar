package ir.tdaapp.diako.shaar.CityGuide.Models.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemCommentsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemDetailsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.onCategoryItemDetailsCommentsClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemDetailsCommentsAdapter extends RecyclerView.Adapter<CategoryItemDetailsCommentsAdapter.ViewHolder> {

  Context context;
  ArrayList<CategoryItemDetailsCommentsModel> models;
  onCategoryItemDetailsCommentsClick clickListener;
  CategoryItemCommentsFragmentService commentsFragmentService;
  CategoryItemDetailsFragmentService detailsFragmentService;

  public CategoryItemDetailsCommentsAdapter(ArrayList<CategoryItemDetailsCommentsModel> models, CategoryItemCommentsFragmentService commentsFragmentService) {
    this.models = models;
    this.commentsFragmentService = commentsFragmentService;
  }

  public CategoryItemDetailsCommentsAdapter(Context context) {
    this.context = context;
    models = new ArrayList<>();
  }

  public CategoryItemDetailsCommentsAdapter(ArrayList<CategoryItemDetailsCommentsModel> models, CategoryItemDetailsFragmentService detailsFragmentService) {
    this.models = models;
    this.detailsFragmentService = detailsFragmentService;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_item_details_comments, parent, false);

    return new ViewHolder(view);
  }

  public void add(CategoryItemDetailsCommentsModel model) {
    models.add(model);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    CategoryItemDetailsCommentsModel item = models.get(position);

    holder.userAddress.setText(item.getPhoneNumber());
    holder.message.setText(item.getMessage());
    holder.date.setText(item.getDate());
    holder.dislikeCount.setText("" + item.getDislikes());
    holder.likeCount.setText("" + item.getLikes());

    holder.like.setOnClickListener(v -> {
      if (commentsFragmentService != null) {
        commentsFragmentService.onLikeState(position, item, true);
      } else if (detailsFragmentService != null) {
        detailsFragmentService.onCommentLiked(position, item, true);
      }
    });
    holder.dislike.setOnClickListener(v -> {
      if (commentsFragmentService != null) {
        commentsFragmentService.onLikeState(position, item, false);
      } else if (detailsFragmentService != null) {
        detailsFragmentService.onCommentLiked(position, item, false);
      }
    });
  }

  public void increaseLike(int position, boolean liked) {
    CategoryItemDetailsCommentsModel model = models.get(position);
    if (liked) {
      model.setLikes((model.getLikes() + 1));
    } else {
      model.setDislikes((model.getDislikes() + 1));
    }
    notifyItemChanged(position);
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  public void setCommentsFragmentService(CategoryItemCommentsFragmentService commentsFragmentService) {
    this.commentsFragmentService = commentsFragmentService;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    ImageView avatar, like, dislike;
    TextView userAddress, date, message, likeCount, dislikeCount;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      findView(itemView);
    }

    private void findView(View view) {
      avatar = view.findViewById(R.id.imgCommentAvatar);
      userAddress = view.findViewById(R.id.txtCommentUserAddress);
      date = view.findViewById(R.id.txtCommentDate);
      message = view.findViewById(R.id.txtCommentMessage);
      likeCount = view.findViewById(R.id.txtCommentLikeCount);
      dislikeCount = view.findViewById(R.id.txtCommentDislikeCount);
      like = view.findViewById(R.id.imgCommentlike);
      dislike = view.findViewById(R.id.imgCommentDislike);
    }
  }
}
