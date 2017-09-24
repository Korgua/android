package com.example.korgua.mybio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
    static final int REQUEST_CODE = 1;
    private ImageView bachImage;
    private ImageView mozartImage;
    private String bachBio = "Johann Sebastian Bach[a] (31 March [O.S. 21 March] 1685 – 28 July 1750) was a German composer and musician of the Baroque period. He is known for instrumental compositions such as the Brandenburg Concertos and the Goldberg Variations, and vocal music such as the St Matthew Passion and the Mass in B minor. Since the 19th-century Bach Revival he has been generally regarded as one of the greatest composers of all time. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";
    private String mozartBio = "Born in Salzburg, he showed prodigious ability from his earliest childhood. Already competent on keyboard and violin, he composed from the age of five and performed before European royalty. At 17, Mozart was engaged as a musician at the Salzburg court, but grew restless and traveled in search of a better position. While visiting Vienna in 1781, he was dismissed from his Salzburg position. He chose to stay in the capital, where he achieved fame but little financial security. During his final years in Vienna, he composed many of his best-known symphonies, concertos, and operas, and portions of the Requiem, which was largely unfinished at the time of his death. Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bachImage = (ImageView)findViewById(R.id.ImageBachId);
        mozartImage = (ImageView)findViewById(R.id.ImageMozartId);

        bachImage.setOnClickListener(this);
        mozartImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ImageBachId:
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name","bach");
                intent.putExtra("bach",bachBio);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.ImageMozartId:
                intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name","mozart");
                intent.putExtra("mozart",mozartBio);
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("returnData");
                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
