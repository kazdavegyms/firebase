package mcq.kasun.acer.firebase;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class replyyy extends AppCompatActivity {

    final static  long INTERVEL = 1000;
    final static  long TIMEOUT  = 7000;
    int ProgressValue =0;

    CountDownTimer mCountDown;
    int index=0,score=0,thisQuestion=0,totalQustion,correctAnswer;

    //firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar pbar;
    ImageView imgv;
    Button ba,bb,bc,bd,be;
    TextView tscore,tqno,tqes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_replyyy);


        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        imgv = (ImageView) findViewById(mcq.kasun.acer.firebase.R.id.imagess);

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
