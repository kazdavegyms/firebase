package mcq.kasun.acer.firebase.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mcq.kasun.acer.firebase.Interface.ItemClickListner;
import mcq.kasun.acer.firebase.R;

/**
 * Created by acer on 6/26/2018.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_name,txt_score;
    private ItemClickListner itemClickListner;


    //10.47

    public RankingViewHolder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.txtname);
        txt_score = (TextView)itemView.findViewById(R.id.txtscore);

        itemView.setOnClickListener(this);
    }



    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
                itemClickListner.onClick(v,getAdapterPosition(),false);
    }
}
