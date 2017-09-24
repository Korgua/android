package com.example.korgua.tempconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText convert;
    private Button c;
    private Button f;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        convert = (EditText)findViewById(R.id.Convert);
        c = (Button)findViewById(R.id.CelsiusButtonId);
        f = (Button)findViewById(R.id.FahrenheitButtonId);
        result = (TextView)findViewById(R.id.ResultTextViewId);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //result.setText();
                double cel = toCels(convertStringToInt());
                result.setText(String.format("%.2f",cel));
            }
        });

        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double fah = toFahren(convertStringToInt());
                result.setText(String.format("%.2f",fah));
            }
        });

    }

    public double convertStringToInt(){
        String editTextVal = convert.getText().toString();
        if(editTextVal.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter a value, please",Toast.LENGTH_LONG).show();
        }
        else {
            return  Double.parseDouble(editTextVal);
        }
        return 0;
    }

    public double toFahren(double input){
        return input *  9 / 5 + 32;
    }

    public double toCels(double input){
        return (input - 32) * 5 / 9;
    }
}
