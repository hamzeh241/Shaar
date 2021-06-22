package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ir.tdaapp.diako.shaar.ETC.AppController;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Show_Details_Home;
import ir.tdaapp.diako.shaar.Interface.IBase;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.Model.Published_Home;

import java.util.List;

/**
 * Created by Diako on 7/8/2019.
 */

public class PublishedAdapter extends RecyclerView.Adapter<PublishedAdapter.MyViewHolder> implements IBase {

    Context context;
    List<Published_Home> values;
    Internet internet;
    DBAdapter dbAdapter;

    public PublishedAdapter(Context context, List<Published_Home> values) {
        this.context = context;
        this.values = values;
        internet=new Internet(context);
        dbAdapter=new DBAdapter(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_published_myhome, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.Address.setText(values.get(position).getAddress());
        holder.CountRoom.setText(String.valueOf(values.get(position).getRoom()));
        holder.Price.setText(values.get(position).getPrice());

        Glide.with(context).load(values.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .placeholder(ir.tdaapp.diako.shaar.R.drawable.shaar_image).error(ir.tdaapp.diako.shaar.R.drawable.shaar_image).into(holder.img);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setTitle((Html.fromHtml("<font color='#FF7F27'>هشدار</font>")))
                        .setMessage((Html.fromHtml("<font color='#FF7F27'>آیا مایل به حذف می باشید</font>")))
                        .setPositiveButton((Html.fromHtml("<font color='#FF7F27'>بله</font>")), new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String url = Api + "Item/" + values.get(position).getId();
                                StringRequest
                                        stringRequest
                                        = new StringRequest(
                                        Request.Method.DELETE,
                                        url,
                                        new Response.Listener() {
                                            @Override
                                            public void onResponse(Object response) {
                                                if (response.toString().equalsIgnoreCase("true")) {

                                                    Cursor removeLike=dbAdapter.ExecuteQ("delete from TblSearch where IsFavorit=1 and Id="+values.get(position).getId());
                                                    removeLike.close();
                                                    values.remove(position);
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "عملیات با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    new AlertDialog.Builder(context).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای در عملیات رخ داده است لطفا بعدا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>متوجه شدم</font>")), null).show();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                new AlertDialog.Builder(context).setTitle((Html.fromHtml("<font color='#FF7F27'>خطا</font>"))).setMessage((Html.fromHtml("<font color='#FF7F27'>خطای در سمت سرور رخ داده است لطفا بعدا امتحان کنید</font>"))).setPositiveButton((Html.fromHtml("<font color='#FF7F27'>متوجه شدم</font>")), null).show();
                                            }
                                        });

                                AppController.getInstance().addToRequestQueue(stringRequest);
                            }
                        })
                        .setNegativeButton((Html.fromHtml("<font color='#FF7F27'>خیر</font>")), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internet.HaveNetworkConnection()){
                    Bundle bundle=new Bundle();
                    bundle.putInt("Id",values.get(position).getId());
                    Fragment_Show_Details_Home fragment_show_details_home=new Fragment_Show_Details_Home();
                    fragment_show_details_home.setArguments(bundle);
                    ((MainActivity)context).onAddFragment(fragment_show_details_home,0,0,true,Fragment_Show_Details_Home.TAG);
//                    Stack_Back.MyStack_Back.Push("Fragment_Show_Details_Home",context,bundle);
                }else{
                    Toast.makeText(context, "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img, remove;
        TextView Price, CountRoom, Address;
        CardView item;

        public MyViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.img);
            Price = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.price);
            CountRoom = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.CountRoom);
            Address = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.address);
            item = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.item);
            remove = itemView.findViewById(ir.tdaapp.diako.shaar.R.id.remove);
        }
    }
}
