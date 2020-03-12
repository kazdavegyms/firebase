package mcq.kasun.acer.firebase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mcq.kasun.acer.firebase.common.common;

public class yourranking extends AppCompatActivity {

    Button selct,urr;
    EditText urndt;
    Fragment selectedFragment =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_yourranking);

        selct = findViewById(mcq.kasun.acer.firebase.R.id.selectfile);
        urr = findViewById(mcq.kasun.acer.firebase.R.id.youuu);
        urndt = findViewById(mcq.kasun.acer.firebase.R.id.usrnameedit);

        selct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedFragment = ranking.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(mcq.kasun.acer.firebase.R.id.kas,selectedFragment);
                transaction.commit();

            }
        });


        urr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                common.rankuser = urndt.getText().toString();

                selectedFragment = rankingnew.newInstance();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(mcq.kasun.acer.firebase.R.id.kas,selectedFragment);
                transaction.commit();

            }
        });



    }
}
