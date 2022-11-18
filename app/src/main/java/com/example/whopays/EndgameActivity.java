package com.example.whopays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class EndgameActivity extends AppCompatActivity {

    private TextView player1;
    private TextView player2;
    private TextView winner;
    private TextView whopays;
    private ImageButton restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);


        player1 = findViewById(R.id.player1points);
        player2 = findViewById(R.id.player2points);
        winner = findViewById(R.id.winner);
        whopays = findViewById(R.id.whopays);
        restartBtn = findViewById(R.id.restartBtn);

        player1.setText(""+ getSpieler1()+": "+getPlayer1Points());
        player2.setText(""+ getSpieler2()+": "+getPlayer2Points());

        if(getPlayer1Points() > getPlayer2Points()){
            winner.setText(getSpieler1()+" hat gewonnen mit "+getPlayer1Points()+" Punkten!");
            whopays.setText(getSpieler2()+" muss zahlen!");

        }
        else if(getPlayer1Points() < getPlayer2Points()){
            winner.setText(getSpieler2()+" hat gewonnen mit "+getPlayer2Points()+" Punkte!");
            whopays.setText(getSpieler1()+" muss zahlen!");
        }
        else if(getPlayer1Points() == getPlayer2Points()){
            winner.setText("Unentschieden!");
        }

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();

                Intent change = new Intent(EndgameActivity.this, MainActivity.class);
                startActivity(change);
            }
        });

    }

    public String getSpieler1(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        String spieler1 = sharedPreferences.getString("spieler1", null);
        return spieler1;
    }


    public String getSpieler2(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        String spieler2 = sharedPreferences.getString("spieler2", null);
        return spieler2;
    }

    public int getPlayer1Points(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        int anz = sharedPreferences.getInt("player1pts", 0);
        return anz;
    }

    public int getPlayer2Points(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        int anz = sharedPreferences.getInt("player2pts", 0);
        return anz;
    }


}