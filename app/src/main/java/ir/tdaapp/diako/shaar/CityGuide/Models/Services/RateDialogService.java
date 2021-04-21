package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

public interface RateDialogService {

  void onRate(int rate);

  void onRateSendingState(boolean state, String message);
}
