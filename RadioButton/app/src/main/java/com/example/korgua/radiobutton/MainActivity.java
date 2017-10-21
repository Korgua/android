package com.example.korgua.radiobutton;

import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioAnswerButton;
    private TextView showAnswerTextView;
    private Button showAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showAnswerTextView = (TextView)findViewById(R.id.showAnswerTextId);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupId);
        showAnswerButton = (Button)findViewById(R.id.buttonShowAnswerId);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioAnswerButton = (RadioButton)findViewById(selectedId);
                showAnswerTextView.setText(radioAnswerButton.getText());
                Toast.makeText(getApplicationContext(),"Selected id: "+selectedId,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
