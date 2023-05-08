package devandroid.salazar.sendsms;

import static android.Manifest.permission.READ_CONTACTS;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyForegroundService extends Service {
    public MyForegroundService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ContextCompat.checkSelfPermission(this, READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            Log.v("Diogo", "onStartCmd");
            String currentSsid = getCurrentSsid(this);
            if (currentSsid != null) {
                Log.v("Diogo", "primeiro if" + currentSsid);
                String savedSsid = this.getSharedPreferences("myAppSendSMS", Context.MODE_PRIVATE).getString("SSID", null);
                if (savedSsid != null && savedSsid.equals(currentSsid)) {
                    Log.v("Diogo", "segundo if" + currentSsid + " ---" + savedSsid);
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    if (!this.getSharedPreferences("myAppSendSMS", Context.MODE_PRIVATE).contains(date)) {
                        Log.v("Diogo", "terceiro if" + currentSsid + " ---" + savedSsid);
                        enviarSMS(this);
                    }


                }

            }

            final String CHANNELID = "Foreground Service ID";
            NotificationChannel channel = new NotificationChannel(
                    CHANNELID,
                    CHANNELID,
                    NotificationManager.IMPORTANCE_LOW
            );

            getSystemService(NotificationManager.class).createNotificationChannel(channel);
            Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                    .setContentText("Service is running")
                    .setContentTitle("Service enabled")
                    .setSmallIcon(R.drawable.ic_launcher_background);

            startForeground(1001, notification.build());
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void enviarSMS(Context context) {

        Log.v("Diogo", "Enviar SMS");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        context.getSharedPreferences("myAppSendSMS", Context.MODE_PRIVATE).edit().putBoolean(date, true).commit();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0 && name.contains("#F#")) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    pCur.moveToNext();

                    String phoneNo = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, "Já cheguei ao trabalho vivo!", null, null);
                        Toast.makeText(getApplicationContext(), "Mensagem enviada!",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                                Toast.LENGTH_LONG).show();
                        ex.printStackTrace();
                    }
                    Log.i("DIOGO", "Name: " + name);
                    Log.i("DIOGO", "Phone Number: " + phoneNo);

                    pCur.close();
                }
            }
        }
        if (cur != null) {
            cur.close();
        }


    }

    public String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID().substring(1, connectionInfo.getSSID().length() - 1);
            }
        }
        return ssid;
    }
}