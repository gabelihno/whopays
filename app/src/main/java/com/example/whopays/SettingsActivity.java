package com.example.whopays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SettingsActivity extends AppCompatActivity  {

    private TextView inputPlayer1;
    private TextView inputPlayer2;
    private TextView player1pts;
    private TextView player2pts;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private TextView anzWuerfel;
    private TextView currentPlayer;
    private Button diceButton;
    private int anzWuerfelint;
    private int counter = 1;
    private boolean isplayer1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        inputPlayer1 = findViewById(R.id.player1Game);
        inputPlayer2 = findViewById(R.id.player2Game);
        anzWuerfel = findViewById(R.id.randomNumber);
        diceButton = findViewById(R.id.dicesimbtn);
        player1pts = findViewById(R.id.player1pts);
        player2pts = findViewById(R.id.player2pts);
        currentPlayer = findViewById(R.id.currentPlayer);
        diceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleShakeEvent();

            }
        });

        inputPlayer1.setText(getSpieler1());
        inputPlayer2.setText(getSpieler2());
        currentPlayer.setText(getSpieler1());
        anzWuerfelint = getInt();

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                handleShakeEvent();
            }
        });


    }

    public void handleShakeEvent(){
        System.out.println(anzWuerfelint);
        counter++;
        if(counter-1 <= anzWuerfelint*2){

            Random rand = new Random();
            int randomNum = rand.nextInt((6 - 1) + 1) + 1;
            anzWuerfel.setText(""+randomNum);

            if(isplayer1){
                int inputValue = Integer.parseInt(player1pts.getText().toString());
                int calcPoints = inputValue + randomNum;
                player1pts.setText(""+calcPoints);
                isplayer1 = false;
                currentPlayer.setText(getSpieler2());
            }
            else {
                int inputValue = Integer.parseInt(player2pts.getText().toString());
                int calcPoints = inputValue + randomNum;
                player2pts.setText(""+calcPoints);
                currentPlayer.setText(getSpieler1());
                isplayer1 = true;
            }

        }


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

    public int getInt(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        int anz = sharedPreferences.getInt("int", 0);
        return anz;
    }

}