package com.example.youi_beta;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class HomepageAdapter extends BaseAdapter {
    private GridView main_gridView;
    private Integer[]img={R.drawable.news,R.drawable.search,R.drawable.favorite,R.drawable.team};
    private Context context;
    public HomepageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ImageView imageView;
       if (convertView == null){
           imageView = new ImageView(context);

       }else {
           imageView = (ImageView)convertView;
       }
       imageView.setImageResource(img[position]);
       return imageView;
    }

}
