package devandroid.salazar.sendsms;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher, as an instance variable.



    //Recebe o retorno do usario se ele aceitou ou não.
    private ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                if (!needsPermissions(permissions.keySet().toArray(new String[0]))) {
                    lerContatos();
                } else {
                    Toast.makeText(this, "permissão negada", Toast.LENGTH_LONG).show();
                }
            });

    boolean needsPermissions(String[] permissions) {
        boolean isGranted = true;
        for (String permission : permissions) {
            isGranted &= ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return !isGranted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button_salvar).setOnClickListener(this::salvar);


        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(new MyBroadcastReceiver(), filter);

        String[] permissions = {Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (!needsPermissions(permissions)){
            // You can use the API that requires the permission.
            lerContatos();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(permissions);
        }
    }

    //
    //acessa o SharedPreferences para salvar o SSID
    private void salvar(View v){
        getSharedPreferences("myAppSendSMS", Context.MODE_PRIVATE).edit().putString("SSID", ((EditText) findViewById(R.id.ssid_text)).getText().toString()).commit();
        Intent serviceIntent = new Intent(this, MyForegroundService.class);
        this.startForegroundService(serviceIntent);
    }

    public void lerContatos() {
        Toast.makeText(this, "permissão concedida", Toast.LENGTH_LONG).show();


    }
}