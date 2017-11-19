package hu.vhcom.www.petrolcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import StoreData.Data;
import Utils.*;

public class MainActivity extends AppCompatActivity {

    private TextView a;
    private Data data;
    private String xml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data(MainActivity.this);
    }




    @Override
    protected void onResume() {
        super.onResume();
        String storedCode = data.getData(Utils.getServiceCodeKey());
        if(storedCode.equals("") || storedCode.equals("00000")){
            startActivity(new Intent(MainActivity.this,service_code.class));
        }else{
            startActivity(new Intent(MainActivity.this,GetData.class));
            //auth();
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        //super.onActivityReenter(resultCode, data);
        auth();
    }

    public void auth(){
        //utils = new Utils();
        /*String storedCode = data.getData(Utils.getServiceCodeKey());
        Log.v("Stored code",storedCode);
        Toast.makeText(MainActivity.this,"Stored code: "+data.getData(Utils.getServiceCodeKey()),Toast.LENGTH_LONG).show();
        try {
            PartnersHttpClient partnersHttpClient = new PartnersHttpClient(MainActivity.this);
            xml = partnersHttpClient.getDataXml();
            Log.i("MainActiviy XML",xml);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
