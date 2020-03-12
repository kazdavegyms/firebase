package mcq.kasun.acer.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import mcq.kasun.acer.firebase.viewHolder.User;
import mcq.kasun.acer.firebase.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static mcq.kasun.acer.firebase.utils.FirebaseUtils.getCurrentUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userPassword, userEmail,username;
    private Button regButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;
    protected FirebaseUser mFirebaseUser;
    FirebaseDatabase database;
    DatabaseReference user;


    String email, password, usernamee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        firebaseAuth = FirebaseAuth.getInstance();
        userPassword = (EditText) findViewById(R.id.etUserPassword);
        userEmail = (EditText) findViewById(R.id.etUserEmail);
        username = (EditText) findViewById(R.id.username);
        regButton = (Button) findViewById(R.id.btnRegister);
        userLogin = (TextView) findViewById(R.id.tvUserLogin);


        ((EditText)findViewById(R.id.username)).setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
    /* When focus is lost check that the text field
    * has valid values.
    */
                if (!hasFocus) {
                  //  validateInput(v);



                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("user");
                    ref.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                                String num=snapshot.child("user").getValue().toString();

                                if(num.equals(username.getText().toString())){

                                    Toast.makeText(RegistrationActivity.this, "choose another name", Toast.LENGTH_SHORT).show();
                                    username.setText(null);

                                }else{


                                  //  Toast.makeText(RegistrationActivity.this, "good", Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });
                   // Toast.makeText(RegistrationActivity.this, "lost here", Toast.LENGTH_SHORT).show();


                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));


            }
        });



        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("user");
        ref.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String num=snapshot.child("user").getValue().toString();

                    Toast.makeText(RegistrationActivity.this, num, Toast.LENGTH_SHORT).show();


                    //String twos=datas.child("2").getValue().toString();
                    //String threes=datas.child("3").getValue().toString();
                   // String four=datas.child("4").getValue().toString();
                    //so on
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    final String user_email = userEmail.getText().toString().trim();
                    final String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                             if(task.isSuccessful()){



                                //sendEmailVerification();
                                User user = new User();
                                String photoUrl = null;
                                if (user.getPhotoUrl() != null) {
                                    user.setPhotoUrl("none");
                                }

                                user.setEmail(userEmail.getText().toString());
                                user.setUser(username.getText().toString());
                                user.setUid(firebaseAuth.getCurrentUser().getUid());
                                user.setPhotoUrl("none");
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("achiveents").child(firebaseAuth.getCurrentUser().getEmail().replace(".",",")).child("badge").setValue("null");


                                //set the achivement bar null



                                FirebaseUtils.getUserRef(user.getEmail().replace(".", ","))
                                        .setValue(user, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                mFirebaseUser = firebaseAuth.getCurrentUser();
                                                sendVerificationEmail();
                                                finish();
                                            }
                                        });

                                sendUserData();
                               // firebaseAuth.signOut();

                                Toast.makeText(RegistrationActivity.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                                finish();
                               // startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });



    }




    private Boolean validate(){
        Boolean result = false;

        usernamee = username.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();

        if(  password.isEmpty() || email.isEmpty() || usernamee.isEmpty()
        ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = mFirebaseUser;

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }


                });
       // firebaseAuth.signOut();
    }

/*
    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){
                       sendUserData();
                       Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                       firebaseAuth.signOut();
                       finish();
                       startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                   }else{
                       Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }
    }
*/
    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
       DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        /*StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(RegistrationActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
