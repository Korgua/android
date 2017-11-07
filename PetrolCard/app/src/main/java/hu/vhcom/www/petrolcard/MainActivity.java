package hu.vhcom.www.petrolcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import StoreData.Data;
import Utils.*;

public class MainActivity extends AppCompatActivity {

    private TextView a;
    private Data data;
    private static Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new Data(MainActivity.this);
        utils = new Utils();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String storedCode = data.getData(utils.SERVICE_CODE_KEY);
        if(storedCode.equals("") || storedCode.equals("00000")){
            startActivity(new Intent(MainActivity.this,service_code.class));
        }else{
            auth();
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        //super.onActivityReenter(resultCode, data);
        auth();
    }

    public void auth(){
        //utils = new Utils();
        String storedCode = data.getData(utils.SERVICE_CODE_KEY);
        Log.v("Stored code",storedCode);
        Toast.makeText(MainActivity.this,"Stored code: "+data.getData(utils.SERVICE_CODE_KEY),Toast.LENGTH_LONG).show();
        PartnersHttpClient partnersHttpClient = new PartnersHttpClient(MainActivity.this);
    }

}
