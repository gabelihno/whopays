package com.example.whopays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SettingsActivity extends AppCompatActivity  {

    private TextView inputPlayer1;
    private TextView inputPlayer2;
    private TextView player1pts;
    private TextView player2pts;
    private TextView isshaking;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private TextView anzWuerfel;
    private TextView currentPlayer;
    private int anzWuerfelint;
    private int counter = 1;
    private boolean isplayer1 = true;
    int delayTime = 20;
    int rollAnimations = 40;
    int[] diceImages = new int[]{R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5,R.drawable.d6};
    Random random = new Random();
    ImageView dice;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        inputPlayer1 = findViewById(R.id.player1Game);
        inputPlayer2 = findViewById(R.id.player2Game);
        anzWuerfel = findViewById(R.id.randomNumber);
        player1pts = findViewById(R.id.player1pts);
        player2pts = findViewById(R.id.player2pts);
        isshaking = findViewById(R.id.isshaking);
        currentPlayer = findViewById(R.id.currentPlayer);
        dice = findViewById(R.id.dice);
        mp = MediaPlayer.create(this, R.raw.app_src_main_res_raw_roll);

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
            }
        });

        Sensor sensorShake = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                    if(sensorEvent !=null){
                        float x_accl = sensorEvent.values[0];
                        float y_accl = sensorEvent.values[1];
                        float z_accl = sensorEvent.values[2];

                        float floatSum = Math.abs(x_accl) + Math.abs(y_accl) + Math.abs(z_accl);

                        if (floatSum > 14){
                            isshaking.setText("Yes, Shaking");
                            handleShakeEvent();
                        }
                    else {
                            isshaking.setText("No, NOT Shaking");
                        }
                    }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        mSensorManager.registerListener(sensorEventListener, sensorShake, SensorManager.SENSOR_DELAY_NORMAL);

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
                savePlayer1Points(calcPoints);
            }
            else {
                int inputValue = Integer.parseInt(player2pts.getText().toString());
                int calcPoints = inputValue + randomNum;
                player2pts.setText(""+calcPoints);
                currentPlayer.setText(getSpieler1());
                isplayer1 = true;
                savePlayer2Points(calcPoints);
            }
        }
        else{
            Intent change = new Intent(SettingsActivity.this, EndgameActivity.class);
            startActivity(change);
        }
    }

    public void rollDice() {
        counter++;
        if(counter-1 <= anzWuerfelint*2){
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    for(int i=0; i < rollAnimations; i++){
                        int dice1 = random.nextInt(6) +1;
                        dice.setImageResource(diceImages[dice1-1]);
                        if(isplayer1){
                            int inputValue = Integer.parseInt(player1pts.getText().toString());
                            int calcPoints = inputValue + dice1-1;
                            player1pts.setText(""+calcPoints);
                            isplayer1 = false;
                            currentPlayer.setText(getSpieler2());
                            savePlayer1Points(calcPoints);
                        }
                        else {
                            int inputValue = Integer.parseInt(player2pts.getText().toString());
                            int calcPoints = inputValue + dice1-1;
                            player2pts.setText(""+calcPoints);
                            currentPlayer.setText(getSpieler1());
                            isplayer1 = true;
                            savePlayer2Points(calcPoints);
                        }

                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
            if(mp != null){
                mp.start();
            }
        }
        else{
            Intent change = new Intent(SettingsActivity.this, EndgameActivity.class);
            startActivity(change);
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

    public void savePlayer1Points(int anz){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("player1pts", anz);
        editor.apply();
    }

    public void savePlayer2Points(int anz){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("player2pts", anz);
        editor.apply();
    }

}