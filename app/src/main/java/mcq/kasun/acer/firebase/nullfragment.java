package mcq.kasun.acer.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mcq.kasun.acer.firebase.model.cat;
import mcq.kasun.acer.firebase.viewHolder.categoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.firebase.ui.database.FirebaseRecyclerOptions;


public class nullfragment extends Fragment {

    View myfrag;
    RecyclerView listcat;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<cat, categoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;



    public static nullfragment newInstance() {

        nullfragment Cat = new nullfragment();
      
        return Cat;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categories = database.getReference("category");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myfrag = inflater.inflate(mcq.kasun.acer.firebase.R.layout.fragment_category, container, false);

        listcat = (RecyclerView) myfrag.findViewById(mcq.kasun.acer.firebase.R.id.listcat);
        listcat.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listcat.setLayoutManager(layoutManager);


        return myfrag;

        //edit


    }

    public void vet(){
        cat vet=new cat();
        vet.getImage();


    }

}

