package com.example.korgua.mybio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
    private ImageView imageDetail;
    private TextView textDetail;
    private TextView textName;
    private Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageDetail = (ImageView)findViewById(R.id.detailsImageId);
        textDetail = (TextView)findViewById(R.id.detailsTextId);
        textName = (TextView)findViewById(R.id.detailsNameId);
        textDetail.setMovementMethod(new ScrollingMovementMethod());

        extras = getIntent().getExtras();
        if(extras != null) {
            String name = extras.getString("name");
            showDetail(name);
            //Toast.makeText(getApplicationContext(), bio, Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("returnData","Back button pressed from: "+extras.get("name"));
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void showDetail(String mName){
        if(mName.equals("bach")){
            imageDetail.setImageResource(R.drawable.bach);
            textDetail.setText(extras.getString(mName));
        }
        else if(mName.equals("mozart")){
            imageDetail.setImageResource(R.drawable.mozart);
            textDetail.setText(extras.getString(mName));
        }
        textName.setText(mName);
    }

}