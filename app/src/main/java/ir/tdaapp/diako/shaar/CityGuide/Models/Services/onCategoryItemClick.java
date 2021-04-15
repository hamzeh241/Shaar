package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsChipModel;
import ir.tdaapp.diako.shaar.CityGuide.Models.ViewModels.CategoryDetailsModel;

public interface onCategoryItemClick {

  void onClick(CategoryDetailsModel model);

  void onFavorite(CategoryDetailsModel model);
}
