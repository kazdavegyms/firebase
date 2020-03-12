package mcq.kasun.acer.firebase;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {


BottomNavigationView bottomNavigationView;
boolean check = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.hell);



        bottomNavigationView =(BottomNavigationView) findViewById(mcq.kasun.acer.firebase.R.id.navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment =  null;
                Fragment selectedFragmentt =  nullfragment.newInstance();

               switch (item.getItemId())
                {
                    case mcq.kasun.acer.firebase.R.id.action_cat:

/*

                        */

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Home.this, LoginActivity.class));
                     selectedFragment = category.newInstance();
                     check = true;

                    break;

                    case mcq.kasun.acer.firebase.R.id.action_rankin:
                        selectedFragment = ranking.newInstance();
                        check =false;

                        break;

                    case mcq.kasun.acer.firebase.R.id.action_me:
                        selectedFragment = rankingnew.newInstance();
                        check = false;

                        break;
                }


                for (Fragment fragment:getSupportFragmentManager().getFragments()) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }

                if(check==false) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(mcq.kasun.acer.firebase.R.id.kass, selectedFragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.commit();

                }else {
                   setDefaultFragments();
                }



               // Fragment fragment = getSupportFragmentManager().findFragmentByTag("broo");
               // if(fragment != null)
                //    getSupportFragmentManager().beginTransaction().remove(fragment).commit();







                return true;
            }


        }


        );





       setDefaultFragments();
    }

    private void setDefaultFragments() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mcq.kasun.acer.firebase.R.id.kas,category.newInstance(),"broo");
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
