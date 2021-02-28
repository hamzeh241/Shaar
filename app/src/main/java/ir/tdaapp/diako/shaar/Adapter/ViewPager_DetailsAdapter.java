package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import ir.tdaapp.diako.shaar.ETC.Stack_Back;

/**
 * Created by Diako on 5/18/2019.
 */

public class ViewPager_DetailsAdapter extends PagerAdapter {
    Context context;
    List<String>images;
    int Id;
    public ViewPager_DetailsAdapter(Context context, List<String> images,int Id){
        this.context=context;
        this.images=images;
        this.Id=Id;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(images.get(position))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .placeholder(ir.tdaapp.diako.shaar.R.drawable.shaar_image).error(ir.tdaapp.diako.shaar.R.drawable.shaar_image).into(imageView);
        container.addView(imageView,0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("Id",Id);
                Stack_Back.MyStack_Back.Push("Fragment_Show_Images",context,bundle);
            }
        });

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
