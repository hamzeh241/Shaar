package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import ir.tdaapp.diako.shaar.R;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;

/**
 * Created by Diako on 8/16/2019.
 */

public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    List<String> images;

    public ImagePagerAdapter(Context context, List<String> images){
        this.context=context;
        this.images=images;
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

        final ImageViewZoom imageView = new ImageViewZoom(context);
        imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.shaar_image));

        Glide.with(context).load(images.get(position))
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                try {
                    imageView.setImageBitmap(resource);
                } catch (Exception e) {

                }
            }
        });
        container.addView(imageView,0);

        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
