package ir.tdaapp.diako.shaar.CityGuide.Models.Services;

import android.view.View;

public interface OnItemClick {

  void onClick(View view, int position);

  void onLongClick(View view, int position);
}
