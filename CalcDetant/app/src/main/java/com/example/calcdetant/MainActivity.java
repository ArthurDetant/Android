package com.example.calcdetant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Numero1;
    private EditText Numero2;

    private RadioButton Plus;
    private RadioButton Moins;
    private RadioButton Multiplie;
    private RadioButton Divise;

    private TextView Resultat;

    private Button razButton;
    private Button egalButton;

    private Button quitButton;

    private int idoperation = 0;

    private float val1;
    private float val2;
    private float res;


    public void buttonclicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_plus:
                if (checked)
                    idoperation = 1; // +
                break;
            case R.id.radio_moins:
                if (checked)
                    idoperation = 2; // -
                break;
            case R.id.radio_multiplie:
                if (checked)
                    idoperation = 3; // *
                break;
            case R.id.radio_divise:
                if (checked)
                    idoperation = 4; // /
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Numero1 = (EditText) findViewById(R.id.num1);
        Numero2 = (EditText) findViewById(R.id.num2);

        Plus = (RadioButton) findViewById(R.id.radio_plus);
        Moins = (RadioButton) findViewById(R.id.radio_moins);
        Multiplie = (RadioButton) findViewById(R.id.radio_multiplie);
        Divise = (RadioButton) findViewById(R.id.radio_divise);

        Resultat = (TextView) findViewById(R.id.resultat);

        razButton  = (Button) findViewById(R.id.button_raz);
        egalButton  = (Button) findViewById(R.id.button_egal);
        quitButton = (Button) findViewById(R.id.button_quit);


        egalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Vérification avant calcul
                if(Numero1.getText().toString().matches("") || Numero1.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(),"Tous les champs doivent être remplis",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    val1 = Float.parseFloat(Numero1.getText().toString());
                    val2 = Float.parseFloat(Numero2.getText().toString());
                }

                if(idoperation == 0){
                    Toast.makeText(getApplicationContext(),"Merci de séléctionner une opération",Toast.LENGTH_SHORT).show();
                    return;
                }else if(idoperation == 1){
                    res = val1+val2;
                }else if(idoperation == 2){
                    res = val1-val2;
                }else if(idoperation == 3){
                    res = val1*val2;
                }else if(idoperation == 4){
                    res = val1/val2;
                }
                Resultat.setText(Float.toString(res));
            }
        });


        razButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Numero1.setText(null);
                Numero2.setText(null);
                Resultat.setText("Résultat");
                Plus.setChecked(false);
                Moins.setChecked(false);
                Multiplie.setChecked(false);
                Divise.setChecked(false);

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
