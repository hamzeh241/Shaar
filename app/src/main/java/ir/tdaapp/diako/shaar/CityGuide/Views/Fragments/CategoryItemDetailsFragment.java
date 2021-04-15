package ir.tdaapp.diako.shaar.CityGuide.Views.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsCommentsAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Adapters.CategoryItemDetailsPhotosAdapter;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.OnItemClick;
import ir.tdaapp.diako.shaar.CityGuide.Models.Utilities.BaseFragment;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsPhotoModel;
import ir.tdaapp.diako.shaar.CityGuide.Views.Activities.GuideActivity;
import ir.tdaapp.diako.shaar.R;

public class CategoryItemDetailsFragment extends BaseFragment implements View.OnClickListener {

  public static final String TAG = "CategoryItemDetailsFragment";
  String test = "لورم ایپسوم متن ساختگی با تولید سادگی نامفهوم از صنعت چاپ، و با استفاده از طراحان گرافیک است، چاپگرها و متون بلکه روزنامه و مجله در ستون و سطرآنچنان که لازم است، و برای شرایط فعلی تکنولوژی مورد نیاز، و کاربردهای متنوع با هدف بهبود ابزارهای کاربردی می باشد، کتابهای زیادی در شصت و سه درصد گذشته حال و آینده، شناخت فراوان جامعه و متخصصان را می طلبد، تا با نرم افزارها شناخت بیشتری را برای طراحان رایانه ای علی الخصوص طراحان خلاقی، و فرهنگ پیشرو در زبان فارسی ایجاد کرد، در این صورت می توان امید داشت که تمام و دشواری موجود در ارائه راهکارها، و شرایط سخت تایپ به پایان رسد و زمان مورد نیاز شامل حروفچینی دستاوردهای اصلی، و جوابگوی سوالات پیوسته اهل دنیای موجود طراحی اساسا مورد استفاده قرار گیرد.";

  ViewPager2 slider;
  TextView textView;
  Button showAllComments;

  LinearLayoutManager manager;
  RecyclerView photoRecycler, commentRecycler;
  ArrayList<CategoryItemDetailsPhotoModel> photos;
  CategoryItemDetailsPhotosAdapter adapter;

  ArrayList<CategoryItemDetailsCommentsModel> comments;
  CategoryItemDetailsCommentsAdapter commentsAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_category_item_details, container, false);

    findView(view);
    implement();

    return view;
  }

  private void findView(View view) {
    slider = view.findViewById(R.id.sliderCategoryItemDetails);
    textView = view.findViewById(R.id.textView12);
    photoRecycler = view.findViewById(R.id.recyclerCategoryItemPhotos);
    commentRecycler = view.findViewById(R.id.recyclerItemDetailsComments);
    showAllComments = view.findViewById(R.id.btnCategoryDetailsShowComments);
    photos = new ArrayList<>();
    comments = new ArrayList<>();
    manager = new LinearLayoutManager(getContext());
    manager.setOrientation(RecyclerView.HORIZONTAL);
    manager.setReverseLayout(true);
    photoRecycler.setLayoutManager(manager);
    commentRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
  }

  private void implement() {
    if (test.length() > 300) {
      String sub = test.substring(0, 300);
      textView.setText(sub);
    }

    showAllComments.setOnClickListener(this);

    for (int i = 0; i < 25; i++) {
      CategoryItemDetailsPhotoModel model = new CategoryItemDetailsPhotoModel();
      photos.add(model);
      CategoryItemDetailsCommentsModel comment = new CategoryItemDetailsCommentsModel();
      comment.setUser("9030264757");
      comment.setDate("6 ماه پیش");
      comment.setMessage("کارشون عالیه");
      comment.setLikes(3);
      comment.setDislikes(2);
      comments.add(comment);
    }
    adapter = new CategoryItemDetailsPhotosAdapter(photos, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(View view, int position) {

      }
    });

    commentsAdapter = new CategoryItemDetailsCommentsAdapter(comments, new OnItemClick() {
      @Override
      public void onClick(View view, int position) {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onLongClick(View view, int position) {

      }
    });

    photoRecycler.setAdapter(adapter);
    commentRecycler.setAdapter(commentsAdapter);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnCategoryDetailsShowComments:
        ((GuideActivity) getActivity()).onAddFragment(new CategoryItemCommentsFragment(), 0, 0, false, CategoryItemCommentsFragment.TAG);
        break;
    }
  }
}
