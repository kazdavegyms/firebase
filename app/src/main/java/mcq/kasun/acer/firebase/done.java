package mcq.kasun.acer.firebase;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mcq.kasun.acer.firebase.Interface.Rankingcallback;
import mcq.kasun.acer.firebase.common.common;
import mcq.kasun.acer.firebase.model.Rankingg;
import mcq.kasun.acer.firebase.model.questionScore;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static mcq.kasun.acer.firebase.utils.FirebaseUtils.getCurrentUser;

public class done extends AppCompatActivity {

    Button tryagain,finish,answers;
    TextView txtresultscore,txtresques,wrongans;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference qscore;
    ListView listView=null;
    AlertDialog mydialoge;
    int sum=0;
    DatabaseReference questionscire,ranktb,user;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_done);

        database = FirebaseDatabase.getInstance();
        qscore = database.getReference("Question_Score");
        questionscire = database.getReference("Question_Score");




        DatabaseReference userr = database.getReference().child("user").child(getCurrentUser().getEmail().replace(".", ",")).child("user");


        txtresultscore =(TextView)findViewById(mcq.kasun.acer.firebase.R.id.txttotalquestion);
       //wrongans =(TextView)findViewById(R.id.wronganswer);
        txtresques = (TextView)findViewById(mcq.kasun.acer.firebase.R.id.totalscorer);
        tryagain = (Button)findViewById(mcq.kasun.acer.firebase.R.id.trybtn);
        finish = (Button)findViewById(mcq.kasun.acer.firebase.R.id.finish);
        answers = (Button)findViewById(mcq.kasun.acer.firebase.R.id.finish);
        progressBar =(ProgressBar)findViewById(mcq.kasun.acer.firebase.R.id.donepbar);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                common.Right.clear();
                common.intArrayy = new int[70];


                Intent intent = new Intent(done.this,Home.class);
                startActivity(intent);
                finish();


            }
        });


        userr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String urll = dataSnapshot.getValue(String.class);
                final String url = String.valueOf(urll);

                final DatabaseReference ranktb;
                ranktb = database.getReference("Ranking");



                Toast.makeText(done.this, url, Toast.LENGTH_SHORT).show();


                updatescore(url, new Rankingcallback<Rankingg>() {
                    @Override
                    public void callback(Rankingg ranks) {
                        // update to ranking table
                        ranktb.child(ranks.getUsername())
                                .setValue(ranks);
                        // showRankig();//after uplode we will show therank tble and score

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });







        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialogListView();

                Intent intent = new Intent(done.this,showing.class);
                startActivity(intent);
                finish();


            }
        });

        listView = new ListView(this);

        final ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(this,
                mcq.kasun.acer.firebase.R.layout.activity_listview, mcq.kasun.acer.firebase.R.id.txtitem,common.wrong_answers);



        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new
                                                AdapterView.OnItemClickListener() {

                                                    @Override

                                                    public void onItemClick(AdapterView<?> parent, View view, int
                                                            position, long id) {

                                                        ViewGroup vg=(ViewGroup)view;

                                                        TextView txt=(TextView)vg.findViewById(mcq.kasun.acer.firebase.R.id.txtitem);

                                                        Toast.makeText(done.this,txt.getText().toString(),Toast.LENGTH_LONG).show();
                                                        String number = txt.getText().toString();
                                                        //  int result = Integer.parseInt(number);
                                                        // ShowQuest(result);

                                                    }

                                                });



        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            int score = extra.getInt("SCORE");
            int TotalQ = extra.getInt("TOTAL");
            int Cans = extra.getInt("CORRECT");


         //   dataSend.putInt("SCORE",score );
         //   dataSend.putInt("TOTAL",totalQustion );
         //   dataSend.putInt("COREECT",correctAnswer );



            txtresultscore.setText(String.format("SCORE : %d",common.Right.size()));
            txtresques.setText(String.format("PAASED : %d / %d",common.Right.size(),TotalQ-1));
            progressBar.setMax(TotalQ);
            progressBar.setProgress(Cans);


            int Pno = common.PPno;
            int k0 = 01;
            String ppno = Integer.toString(common.attepmt);
            String twoDigit = String.format("%02d", Pno);

            qscore.child(String.format("%s_%s_%s_%s", getCurrentUser().getEmail()
                            .replace(".",","),
                                                common.categoryname,common.PPno,common.pastpapername))
                        .setValue(new questionScore(String.format("%s_%s",
                                                    common.categoryname,common.pastpapername),
                                getCurrentUser().getEmail()
                                        .replace(".",","),
                                                    String.valueOf(common.Right.size()),
                                                    common.catogeryId,
                                                    common.categoryname,
                                                    ppno

                        ));



        }

    }


    public void showDialogListView(){


        if(mydialoge==null) {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(done.this);

            builder.setCancelable(true);

            builder.setPositiveButton("OK", null);

            builder.setTitle("wrong question list");

            builder.setView(listView);

            mydialoge = builder.create();

            mydialoge.show();
        }
        else {
            mydialoge.show();
        }


    }

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


    public void browser1(View view) {

        Intent intent = new Intent(done.this,DownloadWebview.class);
        startActivity(intent);
        finish();
       // Intent browserIntent =new Intent(Intent.ACTION_VIEW,Uri.parse("https://comfortyou986137434.wordpress.com/"));
       // startActivity(browserIntent);
    }
}
