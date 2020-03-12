package mcq.kasun.acer.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class showing extends AppCompatActivity {

    final static  long INTERVEL = 1000;
    final static  long TIMEOUT  = 700000;
    int ProgressValue =0;

    CountDownTimer mCountDown;
    int index=0,score=0,thisQuestion=0,totalQustion,correctAnswer;

    //firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar pbar;
    // ImageView imgv;
    PhotoView imgv;
    ImageView imgstate;
    Button ba,bb,bc,bd,be,on,back,skip;
    TextView tscore,tqno,tqes,yourans;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_shownn);
        //firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
        imgv = (PhotoView) findViewById(mcq.kasun.acer.firebase.R.id.photo_view);
        imgstate = (ImageView) findViewById(mcq.kasun.acer.firebase.R.id.imjmark);
        yourans = (TextView)findViewById(mcq.kasun.acer.firebase.R.id.youraser);


        //   Picasso.with(getBaseContext())
        //            .load(common.questionlist.get(index).getQuestion())
        //            .into(imgv);



        //view
        tscore = (TextView)findViewById(mcq.kasun.acer.firebase.R.id.tscore);
        tqno=(TextView)findViewById(mcq.kasun.acer.firebase.R.id.numqest);
        tqes=(TextView)findViewById(mcq.kasun.acer.firebase.R.id.questions);


        back=(Button)findViewById(mcq.kasun.acer.firebase.R.id.backbtn);
        skip=(Button)findViewById(mcq.kasun.acer.firebase.R.id.skip);
        totalQustion = common.questionlist.size();
        ShowQuest(++index);

        //imgstate.setImageDrawable(getResources().getDrawable(R.drawable.rightmark));

       // imgstate.setVisibility(View.INVISIBLE);



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShowQuest(++index);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowQuestback(--index);

            }
        });







    }







    private void ShowQuest(int index) {


       tscore.setText("Correct answer: " + common.questionlist.get(index).getCorrectAnswer());


       // String nn = common.wrong_answers.get(index).toString();
       // Toast.makeText(showing.this,nn,Toast.LENGTH_SHORT).show();


        if (index < totalQustion){

            thisQuestion++;
            tqno.setText(String.format("%d/%s",thisQuestion,totalQustion-1));

            int positionn = -1;
            positionn = common.Right.indexOf(thisQuestion);
            if (positionn == -1) {


                yourans.setText("  Your answer Wrong  ");
                imgstate.setImageDrawable(getResources().getDrawable(mcq.kasun.acer.firebase.R.drawable.wrongimage));
                imgstate.setVisibility(View.VISIBLE);
                Toast.makeText(showing.this,"4load",Toast.LENGTH_SHORT).show();


            } else {


                yourans.setText("  Your answer correct  ");
                imgstate.setImageDrawable(getResources().getDrawable(mcq.kasun.acer.firebase.R.drawable.rightmark));
                imgstate.setVisibility(View.VISIBLE);

                // markkk.setImageResource(R.drawable.ic_check_box_black_24dp);
                // tscore.setText(" correct answer");


            }



            if (common.questionlist.get(index).getIsimageQuestion().equals("true")){

                //if fs a image
                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.VISIBLE);
                tqes.setVisibility(View.INVISIBLE);
                Toast.makeText(showing.this,"5load",Toast.LENGTH_SHORT).show();

            }else{

                //  tqes.setText(common.questionlist.get(index).getQuestion());

                // imgv.setVisibility(View.INVISIBLE);
                // tqes.setVisibility(View.VISIBLE);

                Picasso.with(getBaseContext())
                        .load(common.questionlist.get(index).getQuestion())
                        .into(imgv);

                imgv.setVisibility(View.INVISIBLE);
                tqes.setVisibility(View.VISIBLE);

                Toast.makeText(showing.this,"6load",Toast.LENGTH_SHORT).show();

            }




        }
        else {

            //if it is the final question
           // Intent intent = new Intent(this,done.class);
           // startActivity(intent);
           // finish();
            Toast.makeText(showing.this,"7load",Toast.LENGTH_SHORT).show();

        }

    }

    private void ShowQuestback(int indexx) {

        tscore.setText("Correct answer : "+ common.questionlist.get(index).getCorrectAnswer());



        if (thisQuestion == 1) {

            index++;


            Toast.makeText(showing.this, "cant go back from here", Toast.LENGTH_SHORT).show();


        } else {


            if (indexx < totalQustion) {

                thisQuestion--;
                tqno.setText(String.format("%d/%s", thisQuestion, totalQustion - 1));

                int positionn = -1;
                positionn = common.Right.indexOf(thisQuestion);
                if (positionn == -1) {


                    yourans.setText("  Your answer Wrong  ");
                    imgstate.setImageDrawable(getResources().getDrawable(mcq.kasun.acer.firebase.R.drawable.wrongimage));
                    imgstate.setVisibility(View.VISIBLE);
                    Toast.makeText(showing.this,"4load",Toast.LENGTH_SHORT).show();


                } else {


                    yourans.setText("  Your answer correct  ");
                    imgstate.setImageDrawable(getResources().getDrawable(mcq.kasun.acer.firebase.R.drawable.rightmark));
                    imgstate.setVisibility(View.VISIBLE);


                    // markkk.setImageResource(R.drawable.ic_check_box_black_24dp);
                    // tscore.setText(" correct answer");


                }


                // Toast.makeText(playing.this,indexx,Toast.LENGTH_SHORT).show();
                //  String tt,kk;
                //  char kk = answerA;


                if (common.questionlist.get(indexx).getIsimageQuestion().equals("true")) {

                    //if fs a image
                    Picasso.with(getBaseContext())
                            .load(common.questionlist.get(indexx).getQuestion())
                            .into(imgv);

                    imgv.setVisibility(View.VISIBLE);
                    tqes.setVisibility(View.INVISIBLE);
                    // Toast.makeText(playing.this,"5load",Toast.LENGTH_SHORT).show();

                } else {

                    //  tqes.setText(common.questionlist.get(indexx).getQuestion());

                    // imgv.setVisibility(View.INVISIBLE);
                    // tqes.setVisibility(View.VISIBLE);

                    Picasso.with(getBaseContext())
                            .load(common.questionlist.get(indexx).getQuestion())
                            .into(imgv);

                    imgv.setVisibility(View.INVISIBLE);
                    tqes.setVisibility(View.VISIBLE);

                    //   Toast.makeText(playing.this,"6load",Toast.LENGTH_SHORT).show();

                }

                // on.setText(common.questionlist.get(indexx).getAnswerE());

            } else {


                //if it is the final question
                Intent intent = new Intent(this, done.class);
                startActivity(intent);
                finish();
                Toast.makeText(showing.this, "7load", Toast.LENGTH_SHORT).show();

            }


        }

    }



}
