package ir.tdaapp.diako.shaar.FragmentPage;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Adapter.DBAdapter;
import ir.tdaapp.diako.shaar.Adapter.DraftAdapter;
import ir.tdaapp.diako.shaar.Model.Draft_Home;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 7/7/2019.
 */

public class Tab_Draft_Home extends Fragment {
    public static final String TAG = "Tab_Draft_Home";

    RecyclerView Recycler;
    DBAdapter dbAdapter;
    ProgressBar progress;
    LinearLayout img_not_item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(ir.tdaapp.diako.shaar.R.layout.tab_draft_home, container, false);

        FindItem(view);

        SetDataToRecycler(view);
        return view;
    }

    void FindItem(View view) {

        Recycler = view.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler);
        dbAdapter=new DBAdapter(getActivity());
        progress=view.findViewById(ir.tdaapp.diako.shaar.R.id.loadingdata_progress);
        img_not_item=view.findViewById(ir.tdaapp.diako.shaar.R.id.img_not_item);
    }

   // برای دریافت داده
    List<Draft_Home> GetData() {

        List<Draft_Home> homes = new ArrayList<>();

        //در اینجا تمامی خانه های ثبت شده در sqlite دریافت می شوند
        Cursor cursor=dbAdapter.ExecuteQ("select * from TblItem order by Id Desc");


        if (cursor != null && cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Cursor GetFirstImage=dbAdapter.ExecuteQ("select * from TblItemImages where FkItem="+cursor.getString(0));

                Bitmap bmp;
                if (GetFirstImage.moveToFirst()) {
                    File sdCardDirectory = Environment.getExternalStorageDirectory();
                    File f = new File(sdCardDirectory, "Shaar/" + GetFirstImage.getString(1));
                    if (f.exists())
                    bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                    else
                        bmp=((BitmapDrawable)getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.shaar_image)).getBitmap();
                }
                else {
                    bmp=((BitmapDrawable)getResources().getDrawable(ir.tdaapp.diako.shaar.R.drawable.shaar_image)).getBitmap();
                }
                GetFirstImage.close();

                String Address=cursor.getString(10);
                if (Address.length()>15)
                    Address=Address.substring(0,15);
                homes.add(new Draft_Home(Address,Integer.parseInt(cursor.getString(7)),cursor.getString(2),bmp,Integer.parseInt(cursor.getString(0))));
                cursor.moveToNext();
            }

        }
        else{
            img_not_item.setVisibility(View.VISIBLE);
        }
        cursor.close();

        return homes;
    }

    void SetDataToRecycler(View view){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        DraftAdapter draftAdapter=new DraftAdapter(getContext(),GetData(),view);
        Recycler.setLayoutManager(linearLayoutManager);
        Recycler.setAdapter(draftAdapter);
    }
}
