package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import ir.tdaapp.diako.shaar.ETC.Font;
import ir.tdaapp.diako.shaar.ETC.Internet;
import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Show_Details_Home;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.Model.Like;
import ir.tdaapp.diako.shaar.Model.List_Home;
import ir.tdaapp.diako.shaar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diako on 5/16/2019.
 */

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.MyViewHolder> {

    public List<List_Home>list_homes;
    Context context;
    Internet internet;
    DBAdapter dbAdapter;
    List<Like> likes;
    Font font;

    public ListHomeAdapter(List<List_Home>list_homes,Context context){
        this.list_homes=list_homes;
        this.context=context;
        internet=new Internet(context);
        dbAdapter=new DBAdapter(context);
        font=new Font(context);
        likes=new ArrayList<>();
        GetLikes();
    }

    public void Add(List<List_Home> homes1){
        for (int i=0;i<homes1.size();i++){
            int position=list_homes.size();
            list_homes.add(position,homes1.get(i));
            notifyItemInserted(position);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_list_home,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.Price.setText(list_homes.get(position).getPrice());
        holder.Address.setText(list_homes.get(position).getAddress());
        holder.Discription.setText(list_homes.get(position).getDiscription());
        holder.Time.setText(list_homes.get(position).getTime());
        holder.CountRoom.setText(list_homes.get(position).getCountRoom());
        holder.Area.setText(list_homes.get(position).getArea()+" متر مربع");
        holder.CountImage.setText(list_homes.get(position).getCountImage());

        holder.like.setImageResource(ir.tdaapp.diako.shaar.R.drawable.like);
        //در اینجا تشخیص می دهد که کاربر قبلا این آیتم را لایک کرده است

        if (CheckForIsLike(list_homes.get(position).getId())){
            holder.like.setImageResource(ir.tdaapp.diako.shaar.R.drawable.is_like);
        }


        holder.Special.setVisibility(View.GONE);
        if (list_homes.get(position).isSpecial()){
            holder.Special.setVisibility(View.VISIBLE);
        }

        Glide.with(context).load(list_homes.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.shaar_image).error(R.drawable.shaar_image).into(holder.img);

        holder.Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internet.HaveNetworkConnection()){
                    Bundle bundle=new Bundle();
                    bundle.putInt("Id",list_homes.get(position).getId());
                    Fragment_Show_Details_Home fragment_show_details_home=new Fragment_Show_Details_Home();
                    fragment_show_details_home.setArguments(bundle);
                    ((MainActivity)context).onAddFragment(fragment_show_details_home,0,0,true,Fragment_Show_Details_Home.TAG);
//                    Stack_Back.MyStack_Back.Push("Fragment_Show_Details_Home",context,bundle);
                }else{
                    Toast.makeText(context, "لطفا اتصال خود را به اینترنت چک نمایید", Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //در اینجا چک می شود که کاربر قبلا این آیتم را لایک نکرده باشد و اگر شرط درست باشد آیتم لایک می شود
                if (!CheckForIsLike(list_homes.get(position).getId())){

                    int Special=0;
                    if (list_homes.get(position).isSpecial())
                        Special=1;

                    String Query="insert into TblSearch (Id,Title,Price,Area,Room,Age,Address,IsFavorit,FkLocation,DateInsert,Image,Special) values ("+list_homes.get(position).getId()+",'"+list_homes.get(position).getDiscription()+"','"+list_homes.get(position).getPrice()+"',"+list_homes.get(position).getArea()+","+list_homes.get(position).getCountRoom()+",0,'"+list_homes.get(position).getAddress()+"',1,0,'"+list_homes.get(position).getTime()+"','"+list_homes.get(position).getImage()+"',"+Special+")";
                    Cursor cursor=dbAdapter.executeQuery(Query);
                    holder.like.setImageResource(ir.tdaapp.diako.shaar.R.drawable.is_like);
                    cursor.close();
                    dbAdapter.close();
                    likes.add(new Like(list_homes.get(position).getId()));
                }else{
                    Cursor cursor=dbAdapter.executeQuery("delete from TblSearch where Id="+list_homes.get(position).getId());
                    holder.like.setImageResource(ir.tdaapp.diako.shaar.R.drawable.like);
                    cursor.close();
                    dbAdapter.close();

                    for (int i=0;i<likes.size();i++){
                        if (likes.get(i).getId()==list_homes.get(position).getId()){
                            likes.remove(i);
                        }
                    }
                }
            }
        });
    }


    //در اینجا لیست آیتم های لایک شده در دیتابیس گرفته می شود
    void GetLikes(){
        Cursor cursor=dbAdapter.executeQuery("select Id from TblSearch where IsFavorit=1");

        if (cursor!=null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                likes.add(new Like(Integer.parseInt(cursor.getString(0))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbAdapter.close();
    }

    //در اینجا چک می شود که این آیتم قبلا لایک شده است
    boolean CheckForIsLike(int Id){
        for (int i=0;i<likes.size();i++){
            if (likes.get(i).getId()==Id)
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return list_homes.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Price,Address,Discription,Time,CountRoom,Area,CountImage,Special;
        ImageView img,like;
        CardView Item;

        public MyViewHolder(View itemView) {
            super(itemView);

            Price=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_Price);
            Address=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_Address);
            Discription=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_Discription);
            Time=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_Time);
            CountRoom=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_CountRoom);
            Area=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Recycler_ListHome_lbl_Aria);
            img=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.img);
            CountImage=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.CountImage);
            Special=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Special);
            Item=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Item);
            like=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.like);

            font.SetFont("font/b_homa.ttf",CountRoom);
            font.SetFont("font/b_homa.ttf",Area);

        }

    }
}
