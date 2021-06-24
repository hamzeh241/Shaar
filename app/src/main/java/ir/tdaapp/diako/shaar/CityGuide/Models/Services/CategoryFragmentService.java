package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface CategoryFragmentService {

  void onPresenterStart();

  void onItemsReceived(CategoryModel model);

  void onFinish();

  void onError(ResaultCode resaultCode);
}
