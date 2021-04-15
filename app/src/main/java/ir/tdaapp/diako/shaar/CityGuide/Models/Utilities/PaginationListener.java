package ir.tdaapp.diako.shaar.CityGuide.Models.Utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

  public static final int PAGE_START = 0;

  @NonNull
  private LinearLayoutManager layoutManager;

  private static final int PAGE_SIZE = 10;

  public PaginationListener(@NonNull LinearLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  @Override
  public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);
    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
    if (!isLoading()){
      if (!isLastPage()){
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
          && firstVisibleItemPosition >= 0
          && totalItemCount >= PAGE_SIZE) {
          loadMoreItems();
        }
      }else {
        removeLoading();
      }
    }
  }

  protected abstract void loadMoreItems();

  public abstract boolean isLastPage();

  public abstract void removeLoading();

  public abstract boolean isLoading();
}
