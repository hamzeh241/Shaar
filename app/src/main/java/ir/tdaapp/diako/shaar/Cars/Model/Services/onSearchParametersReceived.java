package ir.tdaapp.diako.shaar.Cars.Model.Services;

import org.json.JSONObject;

import ir.tdaapp.diako.shaar.Cars.Model.ViewModels.FilterModel;

public interface onSearchParametersReceived {

  void onResult(FilterModel object);

  void onCancel();
}
