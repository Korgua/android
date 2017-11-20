package com.example.korgua.rdp_test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.rdp_btn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //rdp://full%20address=s:mypc:3389&audiomode=i:2&disable%20themes=i:1
                Uri.Builder builder = new Uri.Builder();
                Uri uri = Uri.parse("rrdp://full%20address=s:mail.vhcom.hu:999&audiomode=i:2&disable%20themes=i:1");
                builder.scheme("rdp")
                        .appendQueryParameter("full addres","s:mail.vhcom.hu:999")
                        .appendQueryParameter("audiomode","i:2")
                        .appendQueryParameter("disable theme","i:1");
                String myUri = builder.build().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,uri.toString()+" megnyitása sikertelen! RDP Kliens fel van telepítve?",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
