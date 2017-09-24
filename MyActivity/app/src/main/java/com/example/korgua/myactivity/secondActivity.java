package com.example.korgua.myactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class secondActivity extends AppCompatActivity {

    private TextView myTV;
    private Button myBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        myTV = (TextView)findViewById(R.id.secondActivityTextViewId);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String myString = extras.getString("activityOne");
            myTV.setText(myString);
        }
        myBtn = (Button)findViewById(R.id.ButtonBackId);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = getIntent();
                returnIntent.putExtra("returnData","I am from the second activity");
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
