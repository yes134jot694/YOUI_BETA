package com.example.youi_beta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

public class info extends AppCompatActivity {
    Bundle bData;
    private TextView Name, Style_price, Friendly_area, Addr_phone, Time, Information;
    private Button btn_map, btn_phone, btn_comment;
    //按鈕
    private String queryID;
    private String picturePath;
    private String infoID;
    private String infoArea;
    private String infoFriendly;
    private String infoStyle;
    private String infoName;
    private String infoPhone;
    private String infoAddress;
    private String infoTime;
    private String infoLatitude,infoLongitude;

    private String infoInformation;
    private float infoRating;
    private String infoPrice;
    //內容資訊
    private Bitmap bitmap;
    ImageView pics;//圖片
    RatingBar ratingStar;//星星

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        initView();
    bData = this.getIntent().getExtras();
    infoID=bData.getString("ID");
    infoArea=bData.getString("AREA");
    infoFriendly=bData.getString("FRIENDLY");
    infoStyle=bData.getString("STYLE");
    infoName=bData.getString("NAME");
    infoPhone=bData.getString("PHONE");
    infoAddress=bData.getString("ADDRESS");
    infoTime=bData.getString("TIME");
    infoLatitude=bData.getString("Latitude");
        Log.d("infoLatitude(info)=",String.valueOf(infoLatitude));
    infoLongitude=bData.getString("Longitude");
        Log.d("infoLongitude(info)=",String.valueOf(infoLongitude));
    infoInformation=bData.getString("information");
    infoRating=bData.getFloat("RATING");
    infoPrice=bData.getString("PRICE");

        Name.setText("店家: "+infoName);
        Friendly_area.setText("友善類型為: "+infoFriendly+   "地區為: "+infoArea);
        Style_price.setText("菜單類型為: "+infoStyle+ "平均價格為: "+infoPrice);
        Addr_phone.setText("店家地址:"+infoAddress+"\n"+"連絡電話: "+infoPhone);
        Time.setText("營業時間: "+infoTime);
        Information.setText("店家簡介:"+"\n"+infoInformation);
        ratingStar.setRating(infoRating);
        //圖片載入第一張
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/"+bData.getString("PIC1"),pics);

        String picurl=bData.getString("PIC1");
        picurl=picurl.substring(0,picurl.length()-4);

        String picurl2=picurl+"(b).jpg";
        String picurl3=picurl+"(c).jpg";
        String picurl4=picurl+"(d).jpg";
        String picurl5=picurl+"(e).jpg";
        String picurl1=bData.getString("PIC1");
        final String[]url={picurl1,picurl2,picurl3,picurl4,picurl5};
        grid adapter=new grid(info.this,url);
        GridView gridView=(GridView) findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        //
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/" +url [position],pics);
            }
        });


        //電話按鈕，按下即可打電話
        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri  call = Uri.parse("tel:"+(String) infoPhone);
                intent.setData(call);
                startActivity(intent);
            }
        });
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(info.this,MapsActivity.class);
                i.putExtra("name",infoName);
                i.putExtra("address",infoAddress);
                i.putExtra("Lat",infoLatitude);
                i.putExtra("Lon",infoLongitude);
                Log.d("Lat(put)=",String.valueOf(infoLatitude));
                Log.d("Lon(put)=",String.valueOf(infoLongitude));
                startActivity(i);

            }
        });
        //進去留言功能
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(info.this,ListShowActivity.class);
                startActivity(intent);
            }
        });
}

    private void initView() {

        Name = findViewById(R.id.txt_shop);
        Friendly_area = findViewById(R.id.txt_2);
        Style_price = findViewById(R.id.txt_3);
        Addr_phone = findViewById(R.id.txt_4);
        Time = findViewById(R.id.txt_time);
        Information = findViewById(R.id.txt_info);
        ratingStar = findViewById(R.id.ratingStar);
        btn_map = findViewById(R.id.btn_map);
        btn_phone = findViewById(R.id.btn_phone);
        btn_comment = findViewById(R.id.btn_message);
        pics = findViewById(R.id.pics);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this, Homepage.class);
                startActivity(i);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
