package devandroid.salazar.passwordgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText lowercaseCountEditText;
    private EditText uppercaseCountEditText;
    private EditText numberCountEditText;
    private EditText symbolCountEditText;
    private Button generateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lowercaseCountEditText = findViewById(R.id.lowercaseCountEditText);
        uppercaseCountEditText = findViewById(R.id.uppercaseCountEditText);
        numberCountEditText = findViewById(R.id.numberCountEditText);
        symbolCountEditText = findViewById(R.id.symbolCountEditText);
        generateButton = findViewById(R.id.generateButton);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lowercaseCount = Integer.parseInt(lowercaseCountEditText.getText().toString());
                int uppercaseCount = Integer.parseInt(uppercaseCountEditText.getText().toString());
                int numberCount = Integer.parseInt(numberCountEditText.getText().toString());
                int symbolCount = Integer.parseInt(symbolCountEditText.getText().toString());

                PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
                List<String> passwords = passwordGeneratorService.generatePasswords(lowercaseCount, uppercaseCount, numberCount, symbolCount);

                StringBuilder passwordListBuilder = new StringBuilder();
                for (String password : passwords) {
                    passwordListBuilder.append(password).append("\n");
                }

                TextView passwordListTextView = new TextView(MainActivity.this);
                passwordListTextView.setText(passwordListBuilder.toString());

                setContentView(passwordListTextView);
            }
        });
    }
}