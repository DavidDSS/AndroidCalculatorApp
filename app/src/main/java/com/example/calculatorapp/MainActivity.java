package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.mariuszgromada.math.mxparser.*;

//Reference:
//Math Parser used in this application to perform basic math calculations:
//MXparser – math expressions parser for Java Android C# .NET/mono/xamarin –
//mathematical formula parser / evaluator library. mXparser – Math Expressions Parser for
//JAVA Android C# .NET/MONO/Xamarin – Mathematical Formula Parser / Evaluator Library. (n.d.).
//Retrieved October 11, 2021, from https://mathparser.org/.

public class MainActivity extends AppCompatActivity {

    //Variable that holds calculator display
    EditText calcDisplay;

    //Variables for radio buttons
    RadioButton basicRadio;
    RadioButton formulaRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcDisplay=findViewById(R.id.calcDisplay);
        basicRadio= findViewById(R.id.basicRadio);
        formulaRadio= findViewById(R.id.formulaRadio);
    }

    //Method to clear calculator display
    public void clearScreen(View view){
        calcDisplay.setText("");
    }

    //Method for changing calculator modes
    public void changeMode(View view){
        String mode;
        if(basicRadio.isChecked()) mode="Basic";
        else mode="Formula";
        calcDisplay.setText("");
        String msg = "Changed Calculator to "+mode+" mode";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void pressInputKey(View view){
        boolean lastValIsOper=false;
        boolean replaceOper=false;

        Button button = (Button)view;
        String bText = button.getText().toString();
        String oldValue = calcDisplay.getText().toString();
        char incomingInput=bText.charAt(0);

        //Ignore Error Message in Calculation
        if(oldValue.equals("Error")) oldValue="";

        //Checks to deal with duplicate operator and double operators
        if(incomingInput=='+' || incomingInput == '-' || incomingInput == '*' || incomingInput == '/' || incomingInput == '^') {
            if (oldValue.length() != 0) {
                char lastValue = oldValue.charAt(oldValue.length() - 1);
                if (lastValue == '+' || lastValue == '-' || lastValue == '*' || lastValue == '/' || lastValue == '^') {
                    lastValIsOper = true;
                }
                if((incomingInput!=lastValue)&&lastValIsOper){
                    replaceOper=true;
                }

                //Checks if basic calculator should perform calculation
                boolean doCalc=false;
                if(oldValue.contains(Character.toString('+'))||
                        oldValue.contains(Character.toString('-'))||
                        oldValue.contains(Character.toString('/'))||
                        oldValue.contains(Character.toString('*'))||
                        oldValue.contains(Character.toString('^'))) doCalc = true;

                //Calculation needs to be made in basic mode
                if(doCalc && !lastValIsOper && basicRadio.isChecked()){
                    pressEqual(view);

                    if(!calcDisplay.getText().toString().equals("Error")){
                        calcDisplay.setText(calcDisplay.getText().toString() + bText);
                    }
                    return;
                }
            }
            //Allow for negative sign as initial input if Formula Mode is Active
            else if(formulaRadio.isChecked()&&incomingInput == '-'){
                calcDisplay.setText(bText);
            }
            else{
                return;
            }
        }
        //Set Input in Calculator Display
        if(!lastValIsOper || replaceOper) {
            if(replaceOper){
                oldValue = oldValue.substring(0, oldValue.length() - 1);
            }
            calcDisplay.setText(oldValue + bText);
        }
    }

    //Method that deals with dealing input for calculator display
    public void deleteInput(View view) {
        String oldValue = calcDisplay.getText().toString();
        if (oldValue.length() != 0) {
            oldValue = oldValue.substring(0, oldValue.length() - 1);
            calcDisplay.setText(oldValue);
        }
    }

    public void pressEqual(View view){

        //Obtain the expression for the calculator display in string
        //form using the library referenced at the beginning of the class
        String expression= calcDisplay.getText().toString();

        //Use the parser library to obtain the answer of the expression
        Expression parser= new Expression(expression);
        String answer=String.valueOf(parser.calculate());

        //Deals with Error Message
        if(answer.equals("NaN")) answer="Error";

        //Display the answer
        calcDisplay.setText(answer);
    }

}