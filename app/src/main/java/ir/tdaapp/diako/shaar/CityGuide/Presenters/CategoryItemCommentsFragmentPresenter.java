package ir.tdaapp.diako.shaar.CityGuide.Presenters;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import ir.tdaapp.diako.shaar.CityGuide.Models.Repositories.CategoryItemCommentsApi;
import ir.tdaapp.diako.shaar.CityGuide.Models.Services.CategoryItemCommentsFragmentService;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryItemDetailsCommentsModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryResultCommentsViewModel;

public class CategoryItemCommentsFragmentPresenter {

  Context context;
  CategoryItemCommentsFragmentService service;
  CategoryItemCommentsApi api;
  Disposable getCommentsDisposable, setCommentsDisposable;

  public CategoryItemCommentsFragmentPresenter(Context context, CategoryItemCommentsFragmentService categoryItemCommentsFragmentService) {
    this.context = context;
    this.service = categoryItemCommentsFragmentService;
    api = new CategoryItemCommentsApi();
  }

  public void start(int itemId, int userId) {
    service.onPresenterStart();
    getComments(itemId, userId);
  }

  private void getComments(int itemId, int userId) {
    service.onLoading(true);
    Single<List<CategoryItemDetailsCommentsModel>> data = api.getComments(itemId, userId);

    getCommentsDisposable = data.subscribeWith(new DisposableSingleObserver<List<CategoryItemDetailsCommentsModel>>() {
      @Override
      public void onSuccess(List<CategoryItemDetailsCommentsModel> commentsModels) {
        setComments(commentsModels);
      }

      @Override
      public void onError(Throwable e) {
        service.onError(e.getMessage());
      }
    });
  }

  private void setComments(List<CategoryItemDetailsCommentsModel> commentsModels) {
    Observable<CategoryItemDetailsCommentsModel> data = Observable.fromIterable(commentsModels);
    setCommentsDisposable = data.subscribe(model -> {

        service.onLoading(false);
        service.onCommentsReceived(model);
      },
      throwable -> {

      }, () -> {
        service.onLoading(false);
        service.onFinish();
      });
  }

  public void sendComment(String text, int itemId, int userId) {
    JSONObject object = createObject(text, itemId, userId);
    service.onCommentPostLoading(true);
    Single<CategoryResultCommentsViewModel> data = api.sendComment(object);

    getCommentsDisposable = data.subscribeWith(new DisposableSingleObserver<CategoryResultCommentsViewModel>() {
      @Override
      public void onSuccess(CategoryResultCommentsViewModel model) {
        Log.i("LOG", "" + model.getTitle());
        service.onCommentPostLoading(false);
        service.onCommentPostFinished(model);
      }

      @Override
      public void onError(Throwable e) {
        service.onCommentPostLoading(false);
        service.onError(e.getMessage());
      }
    });
  }

  private JSONObject createObject(String text, int itemId, int userId) {
    JSONObject object = new JSONObject();
    try {
      object.put("Text", text);
      object.put("UserId", userId);
      object.put("CityGuideId", itemId);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return object;
  }

}
