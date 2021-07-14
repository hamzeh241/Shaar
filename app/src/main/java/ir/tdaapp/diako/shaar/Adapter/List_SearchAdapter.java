package ir.tdaapp.diako.shaar.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ir.tdaapp.diako.shaar.FragmentPage.Fragment_Child_Search_List;
import ir.tdaapp.diako.shaar.MainActivity;
import ir.tdaapp.diako.shaar.ViewModel.VM_List_Search;

import java.util.List;

/**
 * Created by Diako on 7/30/2019.
 */

public class List_SearchAdapter extends RecyclerView.Adapter<List_SearchAdapter.MyViewHolder> {

    List<VM_List_Search> searches;
    Context context;
    DBAdapter dbAdapter;

    public List_SearchAdapter(Context context,List<VM_List_Search> searches){
        this.context=context;
        this.searches=searches;
        dbAdapter=new DBAdapter(context);
    }

    @Override
    public List_SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(ir.tdaapp.diako.shaar.R.layout.recycler_list_search,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(List_SearchAdapter.MyViewHolder holder, final int position) {
        holder.Title.setText(searches.get(position).getTitle());

        holder.Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor Child=dbAdapter.executeQuery("delete from TblSearch where FkListSearch="+searches.get(position).getId());
                Child.close();

                Cursor Item=dbAdapter.executeQuery("delete from TblListSearch where Id="+searches.get(position).getId());
                Item.close();
                dbAdapter.close();

                searches.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("Id",searches.get(position).getId());
                Fragment_Child_Search_List fragment_child_search_list=new Fragment_Child_Search_List();
                fragment_child_search_list.setArguments(bundle);
//                Stack_Back.MyStack_Back.Push("Fragment_Child_Search_List",context,bundle);
                ((MainActivity)context).onAddFragment(fragment_child_search_list,0,0,true,Fragment_Child_Search_List.TAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Title;
        ImageView Remove;
        CardView Item;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Title);
            Remove=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Remove);
            Item=itemView.findViewById(ir.tdaapp.diako.shaar.R.id.Item);
        }
    }
}
