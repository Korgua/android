package com.example.korgua.alertdialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.ShowButtonId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(getResources().getString(R.string.dialog_title));
                dialog.setMessage(getResources().getString(R.string.dialog_message));
                dialog.setCancelable(false);
                dialog.setIcon(android.R.drawable.btn_star);

                dialog.setPositiveButton(getResources().getString(R.string.positive_btn),
                       new DialogInterface.OnClickListener(){
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               //Toast.makeText(getApplicationContext(),"Ok button selected",Toast.LENGTH_SHORT).show();
                               MainActivity.this.finish();
                           }


                       });
                dialog.setNegativeButton(getResources().getString(R.string.negative_btn),
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(getApplicationContext(),"Ok button selected",Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();

                            }


                        });
                AlertDialog alertD = dialog.create();
                alertD.show();
            }
        });
    }
}
