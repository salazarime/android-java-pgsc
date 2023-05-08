package devandroid.salazar.telaloginrevisao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String login_usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Acesso(View view){
        String Login = ((TextView)findViewById(R.id.editText_Login)).getText().toString();
        String Senha = ((TextView)findViewById(R.id.editText_Senha)).getText().toString();

        Intent intent = new Intent(this, TelaPrincipal.class);
        intent.putExtra(login_usuario, Login);
        startActivity(intent);
        finish();
    }
}