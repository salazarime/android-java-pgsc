package salazar.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText editTextAltura;
    private EditText editTextPeso;
    private Button buttonCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAltura = findViewById(R.id.altura);
        editTextPeso = findViewById(R.id.peso);
        buttonCalcular = findViewById(R.id.btnCalcularIMC);

        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alturaStr = editTextAltura.getText().toString();
                String pesoStr = editTextPeso.getText().toString();

                if(!alturaStr.isEmpty() && !pesoStr.isEmpty() ){
                    double altura = Double.parseDouble(alturaStr);
                    double peso = Double.parseDouble(pesoStr);

                    double imc = peso / (altura * altura);

                    Intent intent = new Intent(MainActivity.this, Result.class);
                    intent.putExtra("imc", imc);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos! ", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}