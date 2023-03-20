package salazar.telalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        Intent intent = getIntent();
        String usuario = intent.getStringExtra(MainActivity.login_usuario);

        TextView login = findViewById(R.id.status);
        login.setText(usuario);
    }
}