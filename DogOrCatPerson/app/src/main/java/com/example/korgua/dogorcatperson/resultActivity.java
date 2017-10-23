package com.example.korgua.dogorcatperson;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class resultActivity extends AppCompatActivity {

    private TextView result;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (TextView)findViewById(R.id.textViewDogCatId);
        imageView = (ImageView)findViewById(R.id.imageViewId);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int catResult = extras.getInt("catCounter");
            int dogResult = extras.getInt("dogCounter");
            if(catResult>dogResult){
                result.setText("cat");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_lg_cat));
            }else if(catResult<dogResult){
                result.setText("dog");
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_lg_dog));
            }else{
                result.setText("Neither");
            }
        }

    }
}
