package com.example.youi_beta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Link extends AppCompatActivity {
    private StoreAdapter adapter;
    private Intent intent;
    private ListView storelist = null;
    private String areas,friendlys,styles;
    Bundle bData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.link);
        bData = this.getIntent().getExtras();
        areas=bData.getString("areas");
        Log.d("areas(link)=",String.valueOf(areas));
        friendlys=bData.getString("friendlys");
        Log.d("friendlys(link)=",String.valueOf(friendlys));
        styles=bData.getString("styles");
        Log.d("styles(link)=",String.valueOf(styles));
        if(areas.equals("選擇地區")){areas="";}
        if(friendlys.equals("友善類型")){friendlys="";}
        if(styles.equals("餐廳菜單類型")){styles="";}
//        if(areas.equals("")){}
//        else{querys=querys+" && arr.getJSONObject(i).getString(\"AREA\").equals(\""+areas+"\")";}
//        if(friendlys.equals("")){}
//        else{querys=querys+" && arr.getJSONObject(i).getString(\"FRIENDLY\").equals(\""+friendlys+"\")";}
//        if(styles.equals("")){}
//        else{querys=querys+" && arr.getJSONObject(i).getString(\"STYLE\").equals(\""+styles+"\")";}


        adapter = new StoreAdapter(new ArrayList<Store>(), this);
        storelist= (ListView) findViewById(R.id.listview);
        storelist.setAdapter(adapter);


        (new ConnectMysql()).execute("https://explicative-offense.000webhostapp.com/PHP/YOUI/list_YOUI.php");

        storelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                intent.putExtra("ID",adapter.getItem(position).getID());
                intent.putExtra("PIC1",adapter.getItem(position).getPicURL1());
                intent.putExtra("AREA",adapter.getItem(position).getArea());
                intent.putExtra("FRIENDLY",adapter.getItem(position).getFriendly());
                intent.putExtra("STYLE",adapter.getItem(position).getStyle());
                intent.putExtra("NAME",adapter.getItem(position).getName());
                intent.putExtra("PHONE",adapter.getItem(position).getPhone());
                intent.putExtra("ADDRESS",adapter.getItem(position).getAddress());
                intent.putExtra("TIME",adapter.getItem(position).getTime());
                intent.putExtra("Latitude",adapter.getItem(position).getLatitude());
                Log.d("Latitude(link)=",adapter.getItem(position).getLatitude());
                intent.putExtra("Longitude",adapter.getItem(position).getLongitude());
                Log.d("Longitude(link)=",adapter.getItem(position).getLongitude());
                intent.putExtra("information",adapter.getItem(position).getInfo());
                intent.putExtra("RATING",adapter.getItem(position).getRating());
                intent.putExtra("PRICE",adapter.getItem(position).getPrice());
                Log.i("ID=",String.valueOf(adapter.getItem(position).getID()));
                intent.setClass(Link.this, info.class);

                startActivity(intent);

                //changeView(MainActivity.this, EditActivity.class);
            }
        });

    }
    private class ConnectMysql extends AsyncTask<String,Void,List<Store>> {
        private final ProgressDialog dialog = new ProgressDialog(Link.this);
        @Override
        protected List<Store> doInBackground(String... params) {
            List<Store> result = new ArrayList<Store>();
            URL u = null;
            try {
                //依據傳過來的網址參數建立建線
                u = new URL(params[0]);
                Log.v("params=",params[0]);
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                //讀取網頁上的資料
                InputStream is = conn.getInputStream();
                // Read the stream
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ( is.read(b) != -1)
                    baos.write(b);

                String JSONResp = new String(baos.toByteArray());
                Log.i("JSONResp=", JSONResp);
                JSONArray arr = new JSONArray(JSONResp);

                for (int i=0; i < arr.length(); i++) {
                    if(arr.getJSONObject(i)!=null){// && arr.getJSONObject(i).getString("FRIENDLY").equals(friendlys))
                        if (areas.contains(arr.getJSONObject(i).getString("AREA"))){
                               // arr.getJSONObject(i).getString("AREA").contains(areas))
                                //areas.contains(arr.getJSONObject(i).getString("AREA")))


                            if(friendlys.contains(arr.getJSONObject(i).getString("FRIENDLY"))
                                    //arr.getJSONObject(i).getString("FRIENDLY").contains(friendlys)
                            )
                            {if(styles.contains(arr.getJSONObject(i).getString("STYLE")))
                                    //arr.getJSONObject(i).getString("STYLE").contains(styles))
                                {
                                result.add(convertStore(arr.getJSONObject(i)));
                            }
                            else if(styles.equals(""))
                                    //arr.getJSONObject(i).getString("STYLE").equals(""))
                            {
                                result.add(convertStore(arr.getJSONObject(i)));
                            }
                            }
                            else if(friendlys.equals(""))
                                    //arr.getJSONObject(i).getString("FRIENDLY").equals(""))
                            {
                                if(styles.contains(arr.getJSONObject(i).getString("STYLE")))
                                        //arr.getJSONObject(i).getString("STYLE").contains(styles))
                                {result.add(convertStore(arr.getJSONObject(i)));}
                                else if(styles.equals(""))
                                        //arr.getJSONObject(i).getString("STYLE").equals(""))
                                {result.add(convertStore(arr.getJSONObject(i)));}
                            }
                        }
                        else if(areas.equals("")){
                            if(friendlys.contains(arr.getJSONObject(i).getString("FRIENDLY")))
                                    //arr.getJSONObject(i).getString("FRIENDLY").contains(friendlys))
                            {
                                if(styles.contains(arr.getJSONObject(i).getString("STYLE")))
                                       // arr.getJSONObject(i).getString("STYLE").contains(styles))
                                {
                                    result.add(convertStore(arr.getJSONObject(i)));
                                }
                                else if(styles.equals(""))
                                       // arr.getJSONObject(i).getString("STYLE").equals(""))
                                {
                                    result.add(convertStore(arr.getJSONObject(i)));
                                }
                            }
                            else if(friendlys.equals(""))
                                   // arr.getJSONObject(i).getString("FRIENDLY").equals(""))
                            {
                                if(styles.contains( arr.getJSONObject(i).getString("STYLE")))
                                        //arr.getJSONObject(i).getString("STYLE").contains(styles))
                                {result.add(convertStore(arr.getJSONObject(i)));}
                                else if(styles.equals(""))
                                       // arr.getJSONObject(i).getString("STYLE").equals(""))
                                {result.add(convertStore(arr.getJSONObject(i)));}
                            }
                        }
                    }
                }
                Log.d("result=",String.valueOf(result));

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        private Store convertStore(JSONObject obj) throws JSONException {
            Bitmap bitmap;
            if(obj.getString("Picture1") != null) {
                bitmap = LoadImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/" + obj.getString("Picture1"));

            }else{
                bitmap = LoadImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/images.jpg");
            }
            int ID=obj.getInt("ID");
            String PIC1=obj.getString("Picture1");

            String AREA=obj.getString("AREA");

            String FRIENDLY=obj.getString("FRIENDLY");
            String STYLE =obj.getString("STYLE");
            String NAME =obj.getString("NAME");
            String PHONE =obj.getString("PHONE");
            String ADDRESS =obj.getString("ADDRESS");
            String TIME = obj.getString("TIME");
            String Latitude = obj.getString("latitude");
            String Longitude = obj.getString("longitude");
            String information = obj.getString("INFORMATION");
            float RATING = (float) obj.getDouble("RATING");
            String PRICE = obj.getString("PRICE");

            return new Store(ID,bitmap,PIC1,AREA,FRIENDLY,STYLE,NAME,PHONE,ADDRESS,TIME,Latitude,Longitude,information,RATING,PRICE);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("資料下載中...");
            dialog.show();
        }
        //載入遠端圖片，轉換成bitmap
        private Bitmap LoadImage(String imageUrl){
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();


                conn.setRequestMethod("GET");
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();

                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bitmap;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(List<Store> result) {
            super.onPostExecute(result);
            dialog.dismiss();

            adapter.setItemList(result);
            adapter.notifyDataSetChanged();
            if(adapter.isEmpty()){Toast.makeText(Link.this,"沒有符合的資料!",Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Link.this,MainActivity.class);
            startActivity(i);finish();

            }
        }
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
