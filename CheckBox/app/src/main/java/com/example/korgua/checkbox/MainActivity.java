package com.example.korgua.checkbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TextView> textViewAnimals = new ArrayList<>();
    private List<CheckBox> checkBoxesAnimals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewAnimals.add((TextView)findViewById(R.id.textViewCat));
        textViewAnimals.add((TextView)findViewById(R.id.textViewDog));
        textViewAnimals.add((TextView)findViewById(R.id.textViewFish));

        checkBoxesAnimals.add((CheckBox)findViewById(R.id.checkBoxFirstId));
        checkBoxesAnimals.add((CheckBox)findViewById(R.id.checkBoxSecondId));
        checkBoxesAnimals.add((CheckBox)findViewById(R.id.checkBoxThirdId));

        for(int i=0;i<checkBoxesAnimals.size();i++){
            final int idx = i;
            textViewAnimals.get(idx).setText(
                    checkBoxesAnimals.get(idx).getText()
                            +" is "
                            +checkBoxesAnimals.get(idx).isChecked());
            checkBoxesAnimals.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),Integer.toString(idx),Toast.LENGTH_SHORT).show();
                    textViewAnimals.get(idx).setText(
                            checkBoxesAnimals.get(idx).getText()
                                    +" is "
                                    +checkBoxesAnimals.get(idx).isChecked());

                }
            });
        }
    }
}
