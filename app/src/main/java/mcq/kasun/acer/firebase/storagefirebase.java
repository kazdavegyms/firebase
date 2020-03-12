package mcq.kasun.acer.firebase;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class storagefirebase extends AppCompatActivity {

    Button selectfile,uploade,ftt;
     TextView notification;
     Uri pdfuri;

     FirebaseStorage firebaseStorage;
     FirebaseDatabase firebaseDatabase;

     ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_storagefirebase);

        firebaseStorage = FirebaseStorage.getInstance();//return an object of firebase storage
        firebaseDatabase = FirebaseDatabase.getInstance();

        selectfile = findViewById(mcq.kasun.acer.firebase.R.id.selectfile);
        ftt = findViewById(mcq.kasun.acer.firebase.R.id.fetch);

        uploade = findViewById(mcq.kasun.acer.firebase.R.id.button3);
        notification = findViewById(mcq.kasun.acer.firebase.R.id.textView);




        ftt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent homeActivity = new Intent(storagefirebase.this,yourranking.class);
                startActivity(homeActivity);
                finish();
            }
        });

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(storagefirebase.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        selectpdf();

                    } else {
                        ActivityCompat.requestPermissions(storagefirebase.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                    }
                }
            }
        });

        uploade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(pdfuri != null )
                  uploadefile(pdfuri);
              else{

                  Toast.makeText(storagefirebase.this,"Select a file",Toast.LENGTH_SHORT).show();
              }

            }
        });


    }

    private void uploadefile(Uri pdfuri) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("uploading file....");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String filename = System.currentTimeMillis()+".pdf";
        final String filname1 = System.currentTimeMillis()+"";
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child("uploads").child(filename).putFile(pdfuri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String url = taskSnapshot.getDownloadUrl().toString();//return the uri to your uploaded file
                        //store this uri in thr real time database
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child(filname1).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(storagefirebase.this,"file successfull uploaded",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(storagefirebase.this,"on complete file not successfull uploaded",Toast.LENGTH_SHORT).show();


                                }

                            }
                        });
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(storagefirebase.this,"on fail file not successfull uploaded",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

            int currentprogress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
            progressDialog.setProgress(currentprogress);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

         if (requestCode==9 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){

             selectpdf();
         }
         else {
             Toast.makeText(storagefirebase.this,"please provide permittion",Toast.LENGTH_SHORT).show();
         }

    }

    private void selectpdf(){

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);
        //intent.setData(Uri.parse(urls.get(position)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==86 && resultCode== RESULT_OK && data != null){

            pdfuri = data.getData();
            notification.setText("a file is selected.. " + data.getData().getLastPathSegment());
        }else{

            Toast.makeText(storagefirebase.this,"please select a file",Toast.LENGTH_SHORT).show();
        }
    }
}