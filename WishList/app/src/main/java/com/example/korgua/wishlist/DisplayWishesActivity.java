package com.example.korgua.wishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyWish;

public class DisplayWishesActivity extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<MyWish> dbWishes = new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wishes);

        listView = (ListView)findViewById(R.id.list);
        refreshData();
    }

    private void refreshData() {
        dbWishes.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<MyWish> wishesFromDB = dba.getWishes();

        for(int i=0;i<wishesFromDB.size();i++){
            String title = wishesFromDB.get(i).getTitle();
            String dateText = wishesFromDB.get(i).getRecordDate();
            String content = wishesFromDB.get(i).getContent();
            int id = wishesFromDB.get(i).getItemId();
            MyWish myWish = new MyWish();

            myWish.setTitle(title);
            myWish.setContent(content);
            myWish.setRecordDate(dateText);
            myWish.setItemId(id);

            dbWishes.add(myWish);
        }
        dba.close();

        //setup adapter
        WishAdapter wishAdapter = new WishAdapter(DisplayWishesActivity.this,R.layout.wish_row,dbWishes);
        listView.setAdapter(wishAdapter);
    }
}



