package mcq.kasun.acer.firebase;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mcq.kasun.acer.firebase.common.common;
import mcq.kasun.acer.firebase.model.user;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {


    // to check if we are connected to Network
    boolean isConnected = true;

    // to check if we are monitoring Network
    private boolean monitoringConnectivity = false;


    MaterialEditText editNewUser,editNewPassword,editNewEmail;//for signup
    MaterialEditText User,Password;

    Button btnSignup,btnlogin;
    FirebaseDatabase database;
    DatabaseReference user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_main);

        //firebase
        database = FirebaseDatabase.getInstance();
        user=database.getReference("user");

        User=(MaterialEditText)findViewById(mcq.kasun.acer.firebase.R.id.User);
        Password=(MaterialEditText)findViewById(mcq.kasun.acer.firebase.R.id.password);

        btnlogin=(Button)findViewById(mcq.kasun.acer.firebase.R.id.btn_log_inn);
        btnSignup=(Button)findViewById(mcq.kasun.acer.firebase.R.id.btn_sign_up);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsignupDialoge();
                

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isInternetAvailable() == true){
                    login(User.getText().toString(),Password.getText().toString());


                }else{
                    Toast.makeText(MainActivity.this, "check network connection", Toast.LENGTH_LONG).show();

                }


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


    private void login(final String userr, final String pwd) {


        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userr).exists())
                {

                    if(!userr.isEmpty()){

                        mcq.kasun.acer.firebase.model.user login = dataSnapshot.child(userr).getValue(user.class);
                        if(login.getPassword().equals(pwd))
                        {

                            Intent homeActivity = new Intent(MainActivity.this,Home.class);
                         //   common.currrentUser = login;
                            common.currrenttUser = login;
                            startActivity(homeActivity);
                            finish();
                            //Toast.makeText(MainActivity.this,"got it",Toast.LENGTH_SHORT).show();

                        }

                        else
                            Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_SHORT).show();
                    }

                    else {

                        Toast.makeText(MainActivity.this,"please enter your username",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this,"user is not exist",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showsignupDialoge() {
        AlertDialog.Builder alertdialog= new AlertDialog.Builder(MainActivity.this);
        alertdialog.setTitle("sign up");
        alertdialog.setMessage("please fill up");

        LayoutInflater inflater= this.getLayoutInflater();
        View sign_up_layout = inflater.inflate(mcq.kasun.acer.firebase.R.layout.sign_up_layout,null);

        editNewUser =(MaterialEditText)sign_up_layout.findViewById(mcq.kasun.acer.firebase.R.id.editNewUserNames);
        editNewPassword =(MaterialEditText)sign_up_layout.findViewById(mcq.kasun.acer.firebase.R.id.editnewpassword);
        editNewEmail =(MaterialEditText)sign_up_layout.findViewById(mcq.kasun.acer.firebase.R.id.editemail);

        alertdialog.setView(sign_up_layout);
        alertdialog.setIcon(mcq.kasun.acer.firebase.R.drawable.ic_account_circle_black_24dp);

        alertdialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });


        alertdialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final user User = new user(editNewUser.getText().toString(),editNewPassword.getText().toString(),editNewEmail.getText().toString());

  user.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
          if(dataSnapshot.child(User.getUsername()).exists())
              Toast.makeText(MainActivity.this,"user alredy exist",Toast.LENGTH_SHORT).show();
     else{

         user.child(User.getUsername()).setValue(User);
              Toast.makeText(MainActivity.this,"user register sucess",Toast.LENGTH_SHORT).show();

          }

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
  });
  dialog.dismiss();




            }
        });
        alertdialog.show();




    }
}
