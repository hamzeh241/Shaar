package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsCommentsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemCommentsFragment extends BaseFragment {

  public static final String TAG = "CategoryItemCommentsFragment";

  RecyclerView list;

  ArrayList<CategoryItemDetailsCommentsModel> comments;
  CategoryItemDetailsCommentsAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_item_comments, container, false);

    findView(view);
    implement();

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  private void findView(View view) {
    list = view.findViewById(R.id.recyclerCategoryItemAllComments);
    comments = new ArrayList<>();
  }

  private void implement() {
    list.setLayoutManager(new LinearLayoutManager(getContext()));

    for (int i = 0; i < 25; i++) {
      CategoryItemDetailsCommentsModel comment = new CategoryItemDetailsCommentsModel();
      comment.setUser("9030264757");
      comment.setDate("2 ماه پیش");
      comment.setMessage("کارشون عالیه");
      comment.setLikes(90);
      comment.setDislikes(13);
      comments.add(comment);
    }

    adapter = new CategoryItemDetailsCommentsAdapter(comments, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {

      }

      @Override
      public void onLongClick(View view, int position) {

      }
    });
    list.setAdapter(adapter);
  }
}
