package mcq.kasun.acer.firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by acer on 11/22/2018.
 */

public class isOnline extends AppCompatActivity {

    private static final int WIFI_ENABLE_REQUEST = 0x1006;

    private BroadcastReceiver mNetworkDetectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkInternetConnection();
        }
    };
    private AlertDialog mInternetDialog;
    private boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isConnected = false;
        registerReceiver(mNetworkDetectReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {

        unregisterReceiver(mNetworkDetectReceiver);
        super.onDestroy();
    }

    private void checkInternetConnection() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();


        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            isConnected =true;
            showNoConnectionSnackBar("Connected", isConnected, 1500);

        } else {
            isConnected= false;
            showNoConnectionSnackBar("No active Internet connection found.", isConnected,6000);
        }
    }

    private  void showNoConnectionSnackBar(String message, boolean isConnected, int duration) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, duration);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));

        if (isConnected){
            sbView.setBackgroundColor(getResources().getColor(mcq.kasun.acer.firebase.R.color.colorPrimaryDark));

        }else{
            sbView.setBackgroundColor(getResources().getColor(mcq.kasun.acer.firebase.R.color.cardview_dark_background));
            snackbar.setAction("Turn On", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent internetOptionsIntent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                    startActivityForResult(internetOptionsIntent, WIFI_ENABLE_REQUEST);
                }
            });
            snackbar.setActionTextColor(getResources().getColor(mcq.kasun.acer.firebase.R.color.colorPrimary));
        }

        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == WIFI_ENABLE_REQUEST) {

        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
