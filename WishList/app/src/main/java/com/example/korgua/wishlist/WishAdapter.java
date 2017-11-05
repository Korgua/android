package com.example.korgua.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.MyWish;

/**
 * Created by korgua on 2017. 11. 02..
 */

public class WishAdapter extends ArrayAdapter<MyWish> {
    Activity activity;
    int layoutResource;
    MyWish wish;
    ArrayList<MyWish> mData = new ArrayList<>();
    public WishAdapter(@NonNull Activity act, @LayoutRes int resource, @NonNull ArrayList<MyWish> data) {
        super(act, resource, data);
        activity = act;
        layoutResource = resource;
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Nullable
    @Override
    public MyWish getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getPosition(@Nullable MyWish item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        final ViewHolder holder;

        if(row == null || row.getTag()==null){
            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();

            holder.mTitle = (TextView)row.findViewById(R.id.name);
            holder.mDate = (TextView)row.findViewById(R.id.dateText);

            row.setTag(holder);
        }
        else{
            holder = (ViewHolder)row.getTag();
        }
        holder.myWish = getItem(position);

        holder.mTitle.setText(holder.myWish.getTitle());
        holder.mDate.setText((holder.myWish.getRecordDate()));

        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = holder.myWish.getContent().toString();
                String datetext = holder.myWish.getRecordDate().toString();
                String title = holder.myWish.getTitle().toString();
                int id = holder.myWish.getItemId();

                Intent intent = new Intent(getContext(),WishDetailActivity.class);
                intent.putExtra("content",text);
                intent.putExtra("date",datetext);
                intent.putExtra("title",title);
                intent.putExtra("id",id);

                activity.startActivity(intent);

            }
        });

        return row;
    }

    class ViewHolder{

        MyWish myWish;
        TextView mTitle;
        int  mId;
        TextView mContent;
        TextView mDate;

    }
}