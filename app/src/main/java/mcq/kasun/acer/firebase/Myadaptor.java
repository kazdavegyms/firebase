package mcq.kasun.acer.firebase;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by acer on 10/23/2018.
 */

public class Myadaptor extends RecyclerView.Adapter<Myadaptor.viewHolder> {

    RecyclerView recyclerView;
    Context context;
    ArrayList<String> items=new ArrayList<>();
    ArrayList<String> urls=new ArrayList<>();

    public void update(String name,String url)
    {
        items.add(name);
        urls.add(url);
        notifyDataSetChanged(); //refesh outomatically
    }

    public Myadaptor(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(mcq.kasun.acer.firebase.R.layout.item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        //initiolize individual elements
        holder.nameoffiler.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
         return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        TextView nameoffiler;

        public viewHolder(View itemView) { //represent individual listitem
            super(itemView);
            nameoffiler = itemView.findViewById(mcq.kasun.acer.firebase.R.id.nameoffile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = recyclerView.getChildLayoutPosition(v);
                    Intent intent =  new Intent();
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(urls.get(position)));
                    context.startActivity(intent);


                }
            });

        }
    }
}
