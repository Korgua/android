package hu.vhcom.www.petrolcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import StoreData.Data;
import Utils.Regexp;
import Utils.Utils;

public class service_code extends Activity {

    private EditText vhServiceCode;
    private Button vhServiceButton;
    private Data data;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_code);
        vhServiceCode = (EditText)findViewById(R.id.vhServiceCodeEditText);
        vhServiceButton = (Button)findViewById(R.id.vhServiceCodeButton);
        data = new Data(service_code.this);
        vhServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceCode = vhServiceCode.getText().toString();
                Regexp regexp = new Regexp(serviceCode,utils.vhServiceCodeRegexp);
                if(regexp.getResult()){
                    try {
                        data.storeData(Utils.SERVICE_CODE_KEY, serviceCode);
                        Toast.makeText(service_code.this,R.string.code_saved,Toast.LENGTH_LONG).show();
                        finish();
                        /*Intent intent = new Intent(service_code.this,MainActivity.class);
                        startActivity(intent);*/
                    }
                    catch (Exception e){
                        Toast.makeText(service_code.this,R.string.code_saving_error,Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(service_code.this,R.string.code_format_error,Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}
