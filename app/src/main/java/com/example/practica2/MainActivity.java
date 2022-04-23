package com.example.practica2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int n;

    private EditText eNumber;
    private Button btnJugar, btnReiniciar;
    private TextView puntuacion, intentos, maxima, trampa;
    private int aleatorio;
    private int intento = 5, puntaje = 0, highScore;

    public int randomNumberGenerator() {
        Random rand = new Random();
        n = rand.nextInt ( 6 );
        return n;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eNumber = findViewById(R.id.etTextNumber);
        btnJugar = findViewById(R.id.btnJugar);
        btnReiniciar = findViewById(R.id.btnReiniciar);
        puntuacion = findViewById(R.id.numPuntuacion);
        intentos = findViewById(R.id.numIntentos);
        maxima = findViewById(R.id.numMaxima);

        aleatorio = randomNumberGenerator();
        btnReiniciar.setEnabled(false);

        trampa = findViewById(R.id.TRAMPA);

        trampa.setText(Integer.toString(aleatorio));

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        highScore = prefs.getInt("score", 0);
        maxima.setText(Integer.toString(highScore));


        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int nroUsuario;

                nroUsuario = Integer.parseInt(eNumber.getText().toString());
                if (nroUsuario < 0 || nroUsuario > 5) {
                    Toast.makeText(MainActivity.this, "Numero no valido", Toast.LENGTH_SHORT).show();
                } else if (aleatorio != nroUsuario || intento > 0){
                    Toast.makeText(MainActivity.this, "Segui intentando!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Has ganado! El numero era: " + aleatorio, Toast.LENGTH_SHORT).show();
                    btnJugar.setEnabled(false);
                    btnReiniciar.setEnabled(true);
                    puntaje = puntaje + 10;
                    puntuacion.setText(Integer.toString(puntaje));

                    if (puntaje > highScore) {
                        prefs.edit().putInt("score", puntaje).apply();
                        highScore = prefs.getInt("score", 0);
                        maxima.setText(Integer.toString(highScore));
                    }

                }
                intento = intento -1;
                intentos.setText(Integer.toString(intento));
                if (intento == 0) {
                    Toast.makeText(MainActivity.this, "Perdiste!", Toast.LENGTH_LONG).show();
                    btnJugar.setEnabled(false);
                    btnReiniciar.setEnabled(true);
                }
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intento = 5;
                intentos.setText(Integer.toString(intento));
                aleatorio = randomNumberGenerator();
                btnJugar.setEnabled(true);
                btnReiniciar.setEnabled(false);
                trampa.setText(Integer.toString(aleatorio));
            }
        });
    }
}