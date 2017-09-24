package com.example.korgua.buttons_taptap;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button myButton;
    private TextView myTextView;
    private EditText editText;
    private int clicked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button)findViewById(R.id.buttonId);
        myTextView = (TextView)findViewById(R.id.textViewId);
        editText = (EditText)findViewById(R.id.editText);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().length()!=0){
                    myTextView.setText(editText.getText());
                    Toast.makeText(getApplicationContext(),editText.getText(), Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }
            }
        });


    }
}
