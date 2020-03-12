package mcq.kasun.acer.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;
import mcq.kasun.acer.firebase.Interface.ItemClickListner;
import mcq.kasun.acer.firebase.Interface.Rankingcallback;
import mcq.kasun.acer.firebase.model.Rankingg;
import mcq.kasun.acer.firebase.model.questionScore;
import mcq.kasun.acer.firebase.viewHolder.RankingViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static mcq.kasun.acer.firebase.utils.FirebaseUtils.getCurrentUser;


public class ranking extends Fragment {

    View myfrag;

    RecyclerView rankinglist;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Rankingg,RankingViewHolder> adapter;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference questionscire,ranktb;

    int sum=0;



    public static ranking newInstance(){

        ranking rankk=new ranking();
        return rankk;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questionscire = database.getReference("Question_Score");
        ranktb = database.getReference("Ranking");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myfrag = inflater.inflate(mcq.kasun.acer.firebase.R.layout.fragment_ranking, container, false);

        rankinglist = (RecyclerView) myfrag.findViewById(mcq.kasun.acer.firebase.R.id.ranklist);
        layoutManager = new LinearLayoutManager(getActivity());
        rankinglist.setHasFixedSize(true);

        //becuz order of child methode of firebase sort with asceding order
        //need to revese our recycle data
        //by layout manager
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankinglist.setLayoutManager(layoutManager);


        //implement the call back


        updatescore(getCurrentUser().getEmail()
                .replace(".",","), new Rankingcallback<Rankingg>() {
            @Override
            public void callback(Rankingg ranks) {
                // update to ranking table
                ranktb.child(ranks.getUsername())
                        .setValue(ranks);
                // showRankig();//after uplode we will show therank tble and score

            }
        });


        loadrank();
        return myfrag;

    }

        private void loadrank() {
            //set adapter
            adapter = new FirebaseRecyclerAdapter<Rankingg, RankingViewHolder>(
                    Rankingg.class,
                    mcq.kasun.acer.firebase.R.layout.layout_ranking,
                    RankingViewHolder.class,
                    ranktb.orderByChild("score")
            )

            {
                @Override
                protected void populateViewHolder(RankingViewHolder viewHolder, final Rankingg model, int position) {


                    viewHolder.txt_name.setText(model.getUsername());
                    viewHolder.txt_score.setText(String.valueOf(model.getScore()));


                    //fixed crassh when click item

                    viewHolder.setItemClickListner(new ItemClickListner() {

                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {



                            if(common.currrenttUser.getUsername() == model.getUsername()) {

                                Intent scoredetail = new Intent(getActivity(), scoreDetails.class);
                                scoredetail.putExtra("viewUser", model.getUsername());
                                startActivity(scoredetail);

                            }else {
                               // Toast.makeText(ranking.this,"6load",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),"select your rank for more details..", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            };

            adapter.notifyDataSetChanged();
            rankinglist.setAdapter(adapter);



          //  return myfrag;

        }





    //interface call back toprocess value


    private void updatescore(final String username, final Rankingcallback<Rankingg> callback) {

        questionscire.orderByChild("user").equalTo(getCurrentUser().getEmail()
                .replace(".",",")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){

                    questionScore Ques  =  data.getValue(questionScore.class);
                    sum+=Integer.parseInt(Ques.getScore());
                }
                //after sum of the ques we need to
                //we need to summ it outside


                Rankingg rank = new Rankingg(username,sum);
                callback.callback(rank);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
