package salazar.telalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String login_usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Acesso(View v){

        String Login = ((EditText)findViewById(R.id.login)).getText().toString();
        String Senha = ((EditText)findViewById(R.id.senha)).getText().toString();

        Intent intent = new Intent(this, TelaPrincipal.class);
        intent.putExtra(login_usuario, Login);
        startActivity(intent);
        finish();
    }
}