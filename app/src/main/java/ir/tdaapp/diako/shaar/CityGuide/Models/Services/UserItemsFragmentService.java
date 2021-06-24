package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;
import ir.tdaapp.diako.shaar.Volley.Enum.ResaultCode;

public interface UserItemsFragmentService {

  void loadingState(boolean state);

  void onItemReceived(CategoryDetailsModel model);

  void onError(ResaultCode resaultCode);

  void onFinished();
}
