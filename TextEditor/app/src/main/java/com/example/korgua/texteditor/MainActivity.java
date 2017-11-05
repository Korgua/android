package com.example.korgua.texteditor;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText enterText;
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterText = (EditText)findViewById(R.id.editText);
        saveButton = (Button)findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enterText.getText().toString().equals("")) {
                    if(writeToFile(enterText.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Saved successfully",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter text",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(readFromFile() != null){
            enterText.setText(readFromFile());
        }

    }

    private boolean writeToFile(String myData){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("_diary.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(myData);
            outputStreamWriter.close();
            /*
            StringBuilder str = new StringBuilder();
            for(int i=0;i<myData.length();i++){
                char c = myData.charAt(i);
                int charCode = (int)c;
                str.append(charCode+" ");
            }
            enterText.setText(str.toString());
            */
            return  true;
        }
        catch (IOException e){
            Log.v("MyActivity",e.toString());
        }
        return false;
    }

    private String readFromFile(){
        String result = "";
        try {
            InputStream inputStream = openFileInput("_diary.txt");
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String tempString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((tempString = bufferedReader.readLine()) != null){
                    Log.v("TempString",tempString);
                    //tempString = arr.toString();
                    stringBuilder.append(tempString);
                    stringBuilder.append('\n');

                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e){
            Log.v("MyActivity",e.toString());
        }
        catch (IOException e){
            Log.v("MyActivity",e.toString());
        }

        return result;
    }
}
