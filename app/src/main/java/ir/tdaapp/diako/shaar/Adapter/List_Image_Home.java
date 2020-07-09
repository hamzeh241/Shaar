package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.Data.DA_Add_Home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 6/12/2019.
 */

public class List_Image_Home extends RecyclerView.Adapter<List_Image_Home.MyViewHolder> {

    Context context;
    List<Bitmap> val;

    public List_Image_Home(Context context) {
        this.context = context;
        val=new ArrayList<>();
    }

    public void Add(Bitmap bit){
        val.add(bit);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_list_add_image,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.img.setImageBitmap(val.get(position));

        holder.img_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.remove(position);
                DA_Add_Home.Images.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    //برای ارسال لیست عکس ها
    public List<Bitmap>GetValues(){
        return val;
    }

    @Override
    public int getItemCount() {
        return val.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        ImageView img_Remove;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.img_add_image);
            img_Remove=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Remove);
        }
    }

}
