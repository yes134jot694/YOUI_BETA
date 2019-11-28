package com.example.youi_beta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class StoreAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Store>itemList;

   public StoreAdapter(List<Store>itemList, Context Ctx){
        inflater = (LayoutInflater)Ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemList = itemList;
   }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        if (itemList !=null){
            return itemList.size();
        }else {
        return 0;
        }//因為一定要回傳一個整數,所以有資料時回傳資料筆數,沒資料也要回傳0
    }

    @Override
    public Store getItem(int position) {
       if (itemList !=null)
           return itemList.get(position);
         return null;
    }

    @Override
    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        else
        return 0;
    }
    @SuppressLint("WrongConstant")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.link_view,null);
        Store store = itemList.get(position);
        ImageView pic = convertView.findViewById(R.id.image);
        pic.setImageBitmap(store.getPic());
        TextView Name =  convertView.findViewById(R.id.name);
        Name.setText(store.getName());
        TextView Style = convertView.findViewById(R.id.style);
        Style.setText(store.getStyle());
        TextView Friendly = (TextView) convertView.findViewById(R.id.friendly);
        Friendly.setText(store.getFriendly());
        TextView Area = (TextView) convertView.findViewById(R.id.area);
        Area.setText(store.getArea());
        TextView rating=(TextView)convertView.findViewById(R.id.Rating) ;
        rating.setText("評價:"+String.valueOf(store.getRating())+"分");
        RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.RatingBar);
        ratingBar.setRating(store.getRating());
        return convertView;
    }
    public void setItemList(List<Store> itemList) {
        this.itemList = itemList;
    }
    public List<Store> getItemList() {
        return itemList;
    }
}
