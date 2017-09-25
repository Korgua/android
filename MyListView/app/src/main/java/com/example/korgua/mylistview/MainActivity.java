package com.example.korgua.mylistview;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.ListViewId);
        final String[] values = {
                "Hosszú szöveg",
                "Alma",
                "Körte",
                "Kajszibarack",
                "Kaktuszfüge",
                "Kalamondin",
                "Kései meggy",
                "Keserű narancs",
                "Kínai gesztenye",
                "Kivi (gyümölcs)",
                "Kopasz kivi",
                "Kökény (növényfaj)",
                "Kumkvat",
                "Kuruba",
                "Vadalma",
                "Vadcitrom",
                "Vadcseresznye",
                "Vadkörte",
                "Vadszeder",
                "Vörös áfonya",
                "Vörös ribiszke"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,android.R.id.text1,values);

        lv.setAdapter(adapter);
        lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int intPostition = i;
                String clickedValue = lv.getItemAtPosition(intPostition).toString();
                Toast.makeText(getApplicationContext(),clickedValue,Toast.LENGTH_LONG).show();
            }
        });
    }

}
