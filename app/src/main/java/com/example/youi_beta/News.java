package com.example.youi_beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.lang.reflect.Field;

public class News extends AppCompatActivity {
    ViewFlipper news_flipper;
    private ListView newlist;
    private String[]activity = {
            "2019溫泉美食嘉年華-北橫香菇美食系列活動報名開跑了!活動於108年11月23日(六)及11月30日(六)兩日，分別在羅浮泰雅故事公園及拉拉山的泰雅康乃馨花園",
    "復興三民地區為北台灣重要聖誕紅產地，前年首度舉辦的｢桃園山城紅花節｣，打造全台海拔最高的聖誕紅樹，吸引許多遊客前來打卡，感受山林中不一樣的聖誕氛圍。",
    "建於1941年的中壢警察局日式宿舍群，不僅見證台灣百年警政及邁向民主的歷程，也是記錄中壢地區發展的重要史蹟。館舍經由民眾票選命名為｢壢景町｣，並於11/15(五)正式開幕",
            "親愛的，是不是該把這兩天留給桃園鐵玫瑰？","2019桃園石門活魚節將於11月1日正式開鑼！今年活動規劃「ㄚ桃園哥遊石門」、「傳承好味道」網路票選及「秋品活魚贈好禮」。",
            "「以低碳運動做為水庫觀光新品牌，可提升旅遊品質與深度，結合創意配套措施，期待帶動地方觀光熱潮」",
            "「石門水庫藍色公路」自7/13首航，廣受好評，吸引大批遊客前往「大壩碼頭」、「阿姆坪碼頭」搭乘體驗，因此桃園市政府規劃台灣好行小烏來線串聯藍色公路，11/17起增加停靠石門水庫「阿姆坪碼頭」。",
            "桃園市秋冬補助使用即將用罄，市府再爭取補助額度 旅客入住別忘夜市券200元並可享免費泡湯",
            "｢2019桃園仙草花節｣5公頃浪漫花海打造｢台版普羅旺斯｣，開展前夕即引發關注熱潮！"};
    private int[]new_photo={R.drawable.artisteat,R.drawable.czflower,R.drawable.czpolice,R.drawable.rosemusicconcert,R.drawable.shiman1,
    R.drawable.shiman2,R.drawable.shimanblueroad,R.drawable.taoyuanbanner,R.drawable.taoyuanflower};
    private float x;
    String url_banner="https://travel.tycg.gov.tw/zh-tw/event/news/2858";
    String url_tf="https://travel.tycg.gov.tw/zh-tw/event/news/2863";
    String url_s1="https://travel.tycg.gov.tw/zh-tw/event/news/2838";
    String url_music="https://ironrose2019.com.tw/";
    String url_eat="https://travel.tycg.gov.tw/zh-tw/event/news/2864";
    String url_czf="https://travel.tycg.gov.tw/zh-tw/event/news/2872";
    String url_czp="https://travel.tycg.gov.tw/zh-tw/event/news/2849";
    String url_sbr="https://travel.tycg.gov.tw/zh-tw/event/news/2852";
    String url_s2="https://travel.tycg.gov.tw/zh-tw/event/news/2697";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        news_flipper = (ViewFlipper)findViewById(R.id.news_viewflipper);
        //設立動態導入方式
        for (int i=0;i<new_photo.length;i++){
            news_flipper.addView(getImageView(new_photo[i]));
        }
        news_flipper.setInAnimation(this,R.anim.right_in);//圖片進入效果
        news_flipper.setOutAnimation(this,R.anim.left_out);//圖片出去效果
        news_flipper.setFlipInterval(3000);//圖片切換時間間隔
        news_flipper.startFlipping();
        newlist = (ListView)findViewById(R.id.newList);
        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return activity.length;
            }

            @Override
            public Object getItem(int position) {
                return activity[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = View.inflate(News.this,R.layout.nes_list,null);
                ImageView img = (ImageView)v.findViewById(R.id.img);
                TextView txt_activity = (TextView)v.findViewById(R.id.txt_activity);
                img.setImageResource(new_photo[position]);
                txt_activity.setText(activity[position]);
                return v;
            }
        };
        newlist.setAdapter(adapter);
        newlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_eat));
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_czf));
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_czp));
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_music));
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_s1));
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_s2));
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_sbr));
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_banner));
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(Intent.ACTION_VIEW,Uri.parse(url_tf));
                        startActivity(intent8);
                        break;
                }
            }
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            //手指碰觸螢幕
            case MotionEvent.ACTION_DOWN:
            x=event.getX();
            break;
            //手指移動
            case MotionEvent.ACTION_MOVE:
                //向右滑動前一頁
                if (event.getX()-x>100){
                    news_flipper.stopFlipping();//手指翻動時暫停播放
                    news_flipper.setInAnimation(this,R.anim.left_in);
                    news_flipper.setOutAnimation(this,R.anim.right_out);
                    news_flipper.showPrevious();//查看前一個View
                    news_flipper.startFlipping();
                }
                //向左滑動前一頁
                if (x - event.getX()>100){
                    news_flipper.stopFlipping();
                    news_flipper.setInAnimation(this,R.anim.right_in);
                    news_flipper.setOutAnimation(this,R.anim.left_out);
                    news_flipper.showPrevious();
                    news_flipper.startFlipping();
                }
                break;
            case MotionEvent.ACTION_UP://手指離開螢幕
                break;
        }
        news_flipper.setInAnimation(this,R.anim.right_in);
        news_flipper.setOutAnimation(this,R.anim.left_out);
        news_flipper.setFlipInterval(3000);
        return super.onTouchEvent(event);
    }
    private ImageView getImageView(int new_photo){
        ImageView view = new ImageView(this);
        view.setImageResource(new_photo);
        return view;
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
