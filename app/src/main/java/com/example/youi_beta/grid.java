package com.example.youi_beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class grid extends BaseAdapter {

    private Context context;
    private String[]url;
//    private int[]pics;


    public grid(Context context, String[] url) {
        this.context = context;
        this.url = url;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }

    @Override
    public int getCount() {
        return url.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null) {
            grid=new View(context);
            grid=layoutInflater.inflate(R.layout.gridview,null);

        }
        else
        {grid=(View)convertView; }
        //載入資料-->切記,下面的設定資料最後再做
        //之前塞在=null時沒考慮到畫面被拖曳後的情形,造成畫面一旦移動,內容物的順序就亂跳的情況
        ImageLoader imageLoader=ImageLoader.getInstance();
        ImageView imageview=(ImageView)grid.findViewById(R.id.imageView);
        imageLoader.displayImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/" +url[position],imageview);


        return grid;

    }



}