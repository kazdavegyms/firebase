package mcq.kasun.acer.firebase;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class schoolpaperactivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_schoolpaperactivty);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // selectedFragment = category.newInstance();
        transaction.replace(mcq.kasun.acer.firebase.R.id.kas, schoolpapersfag.newInstance());
        transaction.commit();

    }
}
