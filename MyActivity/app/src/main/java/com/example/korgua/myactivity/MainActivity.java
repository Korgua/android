package com.example.korgua.myactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    static final int REQUEST_CODE = 1;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button)findViewById(R.id.changeActivity);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, secondActivity.class);
                intent.putExtra("activityOne","I am form the First Activity, please, beleive me!!");
                startActivityForResult(intent,REQUEST_CODE);

                //startActivity(new Intent(MainActivity.this, secondActivity.class));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("returnData");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),"void onStart",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(),"void onRestart",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(),"void onPause",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(),"void onStop",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"void onDestroy",Toast.LENGTH_LONG).show();
    }*/


}
