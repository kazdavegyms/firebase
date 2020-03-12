package mcq.kasun.acer.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by acer on 10/23/2018.
 */

public class MyRecyclerViewActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.my_recy);

        recyclerView = findViewById(mcq.kasun.acer.firebase.R.id.recycalviewkaz);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                 String Filename = dataSnapshot.getKey();//return the file name
                 String url = dataSnapshot.getValue(String.class);//return the url for the file name

                ((Myadaptor)recyclerView.getAdapter()).update(Filename,url);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //custom adapor always
        //populate recycle view with items
        recyclerView.setLayoutManager(new LinearLayoutManager(MyRecyclerViewActivity.this));
        Myadaptor mydownloadepaperadapter = new Myadaptor(recyclerView,MyRecyclerViewActivity.this,new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(mydownloadepaperadapter);
    }
}
