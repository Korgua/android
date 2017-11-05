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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils utils = new Utils();
        Data data = new Data(MainActivity.this);
        //data.deleteData(utils.SERVICE_CODE_KEY);
        String storedCode = data.getData(utils.SERVICE_CODE_KEY);

        a = (TextView)findViewById(R.id.vhServiceCodeTextView);
        if(storedCode.equals("") || storedCode.equals("00000")){
            startActivity(new Intent(MainActivity.this,service_code.class));
        }else{
            Log.v("Stored code",storedCode);
            Toast.makeText(MainActivity.this,"Stored code: "+storedCode,Toast.LENGTH_LONG).show();
        }
        PartnersHttpClient partnersHttpClient = new PartnersHttpClient();
    }
}
