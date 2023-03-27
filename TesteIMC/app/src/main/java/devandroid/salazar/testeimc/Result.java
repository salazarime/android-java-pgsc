package devandroid.salazar.testeimc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewResultado = findViewById(R.id.textViewResultado);

        Intent intent = getIntent();
        double imc = intent.getDoubleExtra("imc", 0);

        textViewResultado.setText(String.format("Seu IMC é %.2f", imc));
    }
}