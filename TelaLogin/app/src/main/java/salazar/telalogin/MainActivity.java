package salazar.telalogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.KeyEventDispatcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private Logar logar;
    Intent intent;
    public static final String login_usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = new Intent(this, Logar.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Logar.MyBinder b = (Logar.MyBinder) iBinder;
        logar = b.getService();
        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        logar = null;
    }

    public void Acesso(View v) {

        EditText id1 = ((EditText) findViewById(R.id.login));
        EditText id2 = ((EditText) findViewById(R.id.senha));

        String login = id1.getText().toString();
        String senha = id2.getText().toString();

        String status = logar.validar(login, senha) ? "Acesso Autorizado" : "Erro";
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();


    }
}