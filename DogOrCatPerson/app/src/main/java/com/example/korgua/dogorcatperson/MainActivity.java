package com.example.korgua.dogorcatperson;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup canineRadioGroup;
    private RadioGroup droolRadioGroup;

    private RadioButton canineRadioButton;
    private RadioButton droolRadioButton;

    private SeekBar seekBar;
    private TextView seekBarTextView;
    private CheckBox cutestCheckBoxDog, cutestCheckBoxCat, cutestCheckBoxParrot;

    private Button showResultBtn;

    private int dogCounter, catCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        catCounter = 0;
        dogCounter = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        seekBar = (SeekBar)findViewById(R.id.seekBarFelineId);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
              seekBarTextView.setText("Comfortableness: "+i+"/"+seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void processDrool(RadioGroup radioGroup){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                droolRadioButton = (RadioButton)findViewById(radioId);
                if(droolRadioButton.getText().equals("Yes")){
                    dogCounter += 5;
                }
            }
        });
    }

    public void processCutest(CheckBox dog, CheckBox cat, CheckBox parrot){
        if(dog.isChecked() && !cat.isChecked() && !parrot.isChecked()){
            dogCounter += 5;
        }
        else if(!dog.isChecked() && cat.isChecked() && !parrot.isChecked()){
            catCounter += 5;
        }
    }

    public void processCanine(RadioGroup radioGroup){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                canineRadioButton = (RadioButton)findViewById(radioId);
                if(canineRadioButton.getText().equals("Yes")){
                    dogCounter += 5;
                }
            }
        });
    }

    public void setup(){
        canineRadioGroup = (RadioGroup)findViewById(R.id.RadioGroupCanineId);
        droolRadioGroup = (RadioGroup)findViewById(R.id.radioDroolID);
        seekBarTextView = (TextView)findViewById(R.id.seekBarFelineTextViewId);
        cutestCheckBoxCat = (CheckBox)findViewById(R.id.cutesCat);
        cutestCheckBoxDog = (CheckBox)findViewById(R.id.cutesDog);
        cutestCheckBoxParrot = (CheckBox)findViewById(R.id.cutesParrot);
        showResultBtn = (Button)findViewById(R.id.showResults);

        processCutest(cutestCheckBoxDog,cutestCheckBoxCat,cutestCheckBoxParrot);
        processDrool(droolRadioGroup);
        processCanine(canineRadioGroup);

        showResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity.this,resultActivity.class);
                i.putExtra("catCounter",catCounter);
                i.putExtra("dogCounter",dogCounter);
                startActivity(i);

            }
        });


    }
}
