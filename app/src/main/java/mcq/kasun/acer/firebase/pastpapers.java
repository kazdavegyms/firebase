package mcq.kasun.acer.firebase;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class pastpapers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mcq.kasun.acer.firebase.R.layout.activity_pastpapers);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // selectedFragment = category.newInstance();
        transaction.replace(mcq.kasun.acer.firebase.R.id.kas, pastpapersfrag.newInstance());
        transaction.commit();

    }
}
