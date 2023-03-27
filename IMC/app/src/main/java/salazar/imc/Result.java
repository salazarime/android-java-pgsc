package salazar.imc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private ImageView imagemImageView;
    private TextView textViewResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        imagemImageView = findViewById(R.id.imagemImageView);
        textViewResultado = findViewById(R.id.textViewResultado);

        Intent intent = getIntent();
        double imc = intent.getDoubleExtra("imc", 0);

        textViewResultado.setText(String.format("Seu IMC é %.2f", imc));

        if (imc < 18.5) {
            imagemImageView.setImageResource(R.drawable.abaixo);
        } else if (imc >= 18.5 && imc < 25) {
            imagemImageView.setImageResource(R.drawable.normal);
        } else if (imc >= 25 && imc < 30) {
            imagemImageView.setImageResource(R.drawable.acima);
        } else {
            imagemImageView.setImageResource(R.drawable.obeso);
        }

    }
}