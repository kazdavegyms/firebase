package mcq.kasun.acer.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import mcq.kasun.acer.firebase.model.questionScore;
import mcq.kasun.acer.firebase.viewHolder.scoreviewholder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class scoreDetails extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference Question_score;

    RecyclerView scorelist;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<questionScore, scoreviewholder> adapter;


    String viewUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_score_details);

        database = FirebaseDatabase.getInstance();
        Question_score = database.getReference("Question_Score");

        //view
        scorelist = (RecyclerView)findViewById(mcq.kasun.acer.firebase.R.id.scorelist);
        scorelist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scorelist.setLayoutManager(layoutManager);



        if(getIntent() != null)
            viewUser = getIntent().getStringExtra("viewUser");
        if(!viewUser.isEmpty())
            loadscoredetails(viewUser);
        

    }

    private void loadscoredetails(String viewUser) {
        adapter = new FirebaseRecyclerAdapter<questionScore, scoreviewholder>(
                questionScore.class,
                mcq.kasun.acer.firebase.R.layout.scorelayout,
                scoreviewholder.class,
                Question_score.orderByChild("user").equalTo(viewUser)


        ) {


            @Override
            protected void populateViewHolder(scoreviewholder viewHolder, questionScore model, int position) {

                viewHolder.txt_name.setText(model.getQuestion_score());
                viewHolder.txt_score.setText(model.getScore());


            }
        };



        adapter.notifyDataSetChanged();
        scorelist.setAdapter(adapter);

    }
}
