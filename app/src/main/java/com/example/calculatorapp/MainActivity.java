package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText calcDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcDisplay=findViewById(R.id.calcDisplay);
    }

    public void clearScreen(View view){
        calcDisplay.setText("");
    }

    public void pressInputKey(View view){
        Button button = (Button)view;
        String bText = button.getText().toString();
        String oldValue= calcDisplay.getText().toString();
        calcDisplay.setText(oldValue+bText);
    }

    public void pressParentheses(View view){

    }

    public void pressEqual(View view){

    }

}