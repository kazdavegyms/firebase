package mcq.kasun.acer.firebase;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;
import mcq.kasun.acer.firebase.model.cat;
import mcq.kasun.acer.firebase.model.questionScore;
import mcq.kasun.acer.firebase.viewHolder.categoryViewHolder;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Choose extends AppCompatActivity {

    Button pastpapers, favirte, schools,answer, feedback;
    FirebaseDatabase database;
    DatabaseReference questionss,questionssc;
    private PopupWindow window;
    List<String> dates = new ArrayList<>();

    //int Pno;

    RecyclerView listcat;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<cat, categoryViewHolder> adapter;
    Fragment selectedFragment =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_choose);

        database = FirebaseDatabase.getInstance();
        questionss = database.getReference("Questions");


        pastpapers = (Button) findViewById(mcq.kasun.acer.firebase.R.id.pastpapers);
        favirte = (Button) findViewById(mcq.kasun.acer.firebase.R.id.faviriotpapers);
        schools = (Button) findViewById(mcq.kasun.acer.firebase.R.id.schoolpapers);
        answer = (Button) findViewById(mcq.kasun.acer.firebase.R.id.answers);

      /*  int Pno = 02;
        final String ppno = Integer.toString(Pno);
        LoadQuestions(ppno);

*/

      answer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


            //  Intent intent = new Intent(Choose.this, barchart.class);
             // startActivity(intent);
              //finish();



              database = FirebaseDatabase.getInstance();
              questionssc = database.getReference("Question_Score");

              questionssc.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(DataSnapshot dataSnapshot) {



                          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                              questionScore user = snapshot.getValue(questionScore.class);


                              //String nn = user.getUser();
                              //dates.add(nn);
                              if (user.getUser().equals("kasun")) {

                                  String nn = user.getScore();
                                  int res = Integer.parseInt(nn);
                                   common.ques_score.add(res);
                                   common.papername.add(user.getQuestion_score());

                                  Toast.makeText(Choose.this, nn, Toast.LENGTH_LONG).show();

                              }
                          }

                      Intent intent = new Intent(Choose.this, barchart.class);
                       startActivity(intent);
                      finish();


                     // for (int x=0; x<dates.size(); x++) {}



                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });



          }
      });


        schools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Choose.this, schoolpaperactivty.class);
                startActivity(intent);
                finish();
            }
        });


        pastpapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Intent intent = new Intent(Choose.this, pastpapers.class);
                    startActivity(intent);


                    int kk = 1;
                    common.PPno = kk;


                    finish();

            }
        });

        favirte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Choose.this, "Comming soon", Toast.LENGTH_LONG).show();



                // Intent intent = new Intent(Choose.this, homepappers.class);
               // startActivity(intent);
               // int kkk = 3;
               // common.PPno = kkk;
               // finish();
            }
        });



    }

}

