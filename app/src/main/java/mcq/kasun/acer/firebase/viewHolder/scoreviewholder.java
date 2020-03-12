package mcq.kasun.acer.firebase.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by acer on 6/28/2018.
 */

public class scoreviewholder extends RecyclerView.ViewHolder {

    public TextView txt_name, txt_score;


    public scoreviewholder(View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(mcq.kasun.acer.firebase.R.id.txtname);
        txt_score = (TextView)itemView.findViewById(mcq.kasun.acer.firebase.R.id.txtscore);
    }
}
