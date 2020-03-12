package mcq.kasun.acer.firebase;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    ListView listView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_main2);

        // Create an instance of a ListView

        listView=new ListView(this);

        // Add data to the ListView

        String[] items={"Facebook","Google+","Twitter","Digg"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                mcq.kasun.acer.firebase.R.layout.activity_listview, mcq.kasun.acer.firebase.R.id.txtitem,items);

        listView.setAdapter(adapter);

        // Perform action when an item is clicked

        listView.setOnItemClickListener(new
                                                AdapterView.OnItemClickListener() {

                                                    @Override

                                                    public void onItemClick(AdapterView<?> parent, View view, int
                                                            position, long id) {

                                                        ViewGroup vg=(ViewGroup)view;

                                                        TextView txt=(TextView)vg.findViewById(mcq.kasun.acer.firebase.R.id.txtitem);

                                                        Toast.makeText(Main2Activity.this,txt.getText().toString(),Toast.LENGTH_LONG).show();

                                                    }

                                                });



    }

    // This method is called to display a dialog listview when you
    //click the button

    public void showDialogListView(View view){







        AlertDialog.Builder builder=new
                AlertDialog.Builder(Main2Activity.this);

        builder.setCancelable(true);

        builder.setPositiveButton("OK",null);

        builder.setView(listView);

        AlertDialog dialog=builder.create();

        dialog.show();






    }
    }

