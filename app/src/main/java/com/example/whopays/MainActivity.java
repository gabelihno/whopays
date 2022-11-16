package com.example.whopays;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText inputPlayer1;
    private EditText inputPlayer2;
    private EditText anzWuerfel;
    private Button diceButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputPlayer1 = findViewById(R.id.player1);
        inputPlayer2 = findViewById(R.id.player2);
        anzWuerfel = findViewById(R.id.anz);
        anzWuerfel.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "3")});
        diceButton = findViewById(R.id.diceButton);

        diceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveSpieler1(inputPlayer1.getText().toString());
                saveSpieler2(inputPlayer2.getText().toString());
                saveInt(Integer.parseInt(anzWuerfel.getText().toString()));

                Intent change = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(change);
            }
        });

    }


    public void onButtonClick(View button) {
        Intent change = new Intent(this, SettingsActivity.class);
        startActivity(change);
    }


    public void saveSpieler1(String name){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("spieler1", name);
        editor.apply();
    }

    public String getSpieler1(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        String spieler1 = sharedPreferences.getString("spieler1", null);
        return spieler1;
    }

    public void saveSpieler2(String name){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("spieler2", name);
        editor.apply();
    }

    public String getSpieler2(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        String spieler1 = sharedPreferences.getString("spieler2", null);
        return spieler1;
    }

    public void saveInt(int anz){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("int", anz);
        editor.apply();
    }

    public int getInt(){
        SharedPreferences sharedPreferences = getSharedPreferences("lernsetSpeicher", MODE_PRIVATE);
        int anz = sharedPreferences.getInt("int", 0);
        return anz;
    }

}