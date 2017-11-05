package com.example.korgua.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private TextView showNameText;
    private static final String PREFS_NAME = "MyPrefsFile";
    private Button saveName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText)findViewById(R.id.nameEditText);
        showNameText = (TextView)findViewById(R.id.ShowMyName);
        saveName = (Button)findViewById(R.id.saveButton);
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences myPrefs = getSharedPreferences(PREFS_NAME,0);
                SharedPreferences.Editor editor = myPrefs.edit();

                if(name.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter a name",Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("name", name.getText().toString());
                    try {
                        editor.commit();
                        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
                        showNameText.setText("You are "+prefs.getString("name","Not found"));
                    }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Saving error: "+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
        if(prefs.contains("name")){
            String username = prefs.getString("name","Not found");
            showNameText.setText("You are "+username);
        }
        else{
            showNameText.setText("");
        }
    }


}
