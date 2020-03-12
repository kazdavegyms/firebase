package mcq.kasun.acer.firebase;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;
import mcq.kasun.acer.firebase.model.questions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class start extends AppCompatActivity {

     Button btnplay;
     Button btnnorush;
     TextView co;

     FirebaseDatabase database;
     DatabaseReference questionss,ppers;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_start);  // me page eke thyenna oni layout eka


        database = FirebaseDatabase.getInstance();
        questionss = database.getReference("Questions").child(common.pastpapername.toString());
        ppers = database.getReference("paper");


        int Pno = common.PPno;
        int k0 = 01;
        String ppno = Integer.toString(Pno);
        String twoDigit = String.format("%02d", Pno);


        co = (TextView)findViewById(mcq.kasun.acer.firebase.R.id.coom);
        btnplay = (Button)findViewById(mcq.kasun.acer.firebase.R.id.btnply);
        btnnorush = (Button)findViewById(mcq.kasun.acer.firebase.R.id.btnply2);


        LoadQuestions(common.catogeryId, common.pastpapername);
        co.setText(common.categoryname+"_"+ common.pastpapername+"_"+common.papperno+common.PPno);


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetAvailable() == true){
                    Intent intent = new Intent(start.this,playing.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(start.this, "check network connection", Toast.LENGTH_LONG).show();

                }

            }
        });

        btnnorush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(start.this, "Comming soon", Toast.LENGTH_LONG).show();

                // Intent intent = new Intent(start.this,plynnotime.class);
               // int at = common.attepmt;
              //  startActivity(intent);
              //  finish();
            }
        });


    }



    public boolean isInternetAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED;
    }




    private void LoadQuestions(String catogeryId,String kk) {

        //first clear list if have old questions
        if (common.questionlist.size() > 0)
            common.questionlist.clear();


        questionss.orderByChild("CategoryId_pno")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postsnapshot: dataSnapshot.getChildren()){

                            questions qus= postsnapshot.getValue(questions.class);
                            common.questionlist.add(qus);


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







        // random list
        Collections.shuffle(common.questionlist);

    }


}

