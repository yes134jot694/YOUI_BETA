package com.example.youi_beta;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView img_area,img_friendly,img_style,bar_search;
    TextView areas,friendlys,styles;
    private String match="";


    String[]area_list ={"大園區","大溪區","中壢區","平鎮區","桃園區","楊梅區", "龍潭區","龜山區","觀音區","蘆竹區",};
    String[]friendly_list={"寵物友善","身障友善","親子友善","宗教友善"};
    String[]style_list={"中式","西式","異國","複合","甜點輕食"};
    private AlertDialog dialog =null;
    AlertDialog.Builder builder = null;
    int[]select={0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img_area = (ImageView)findViewById(R.id.img_area);
        img_friendly = (ImageView)findViewById(R.id.img_friendly);
        img_style = (ImageView)findViewById(R.id.img_style);
        bar_search = (ImageView)findViewById(R.id.bar_search);
        areas = (TextView)findViewById(R.id.area);
        friendlys = (TextView)findViewById(R.id.friendly);
        styles = (TextView)findViewById(R.id.style);
        final Bundle bag = new Bundle();
        //設立點擊事件(地區)
        img_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match="";
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("請選擇您要搜尋的地區");
                final List<String>area_item = new ArrayList<>();
                builder.setMultiChoiceItems(area_list, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked){
                            match =match+area_list[which];
                            //area_item.add(area_list[which]);
//                            if(match.equals("")){match=area_list[which];}
//                            else{match =match+area_list[which];}
                        }
                        else {
                            match = match.replace(area_list[which],"");
                        }
                    }
                });
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(match.equals("")){areas.setText("選擇地區");}
                        else{
                        areas.setText(match);}
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        areas.setText("選擇地區");
                        //area_item.clear();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
            }
        });
        //點擊事件(友善)
        img_friendly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match="";
                //select int[]={0}
                builder = new AlertDialog.Builder(MainActivity.this);
                final List<String>friendlyList = new ArrayList<>();
                builder.setTitle("請選擇您要的友善類型");
                builder.setSingleChoiceItems(friendly_list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                            select [0] = which;
                    }
                });
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            match = friendly_list[select[0]];
                            friendlys.setText(match);
//                            if(match.equals("")){friendlys.setText("友善類型");}
//                            else{friendlys.setText(match); }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        friendlys.setText("友善類型");
                        //friendlyList.clear();
                        dialog.dismiss();
                    }
                });
                dialog =builder.create();
                dialog.show();
            }
        });
        //請選擇您的菜單類型
        img_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match="";
                builder = new AlertDialog.Builder(MainActivity.this);
                final List<String>StyleList = new ArrayList<>();
                builder.setTitle("請選擇您想要的菜單類型");
                builder.setSingleChoiceItems(style_list, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       select[0]=which;
                    }
                });
                builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        match = style_list[select[0]];
                        styles.setText(match);
//                        if(match.equals("")){styles.setText("餐廳菜單類型");}
//                        else{styles.setText(match); }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        styles.setText("餐廳菜單類型");
                        //StyleList.clear();
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();

            }
        });
        bar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String s1,s2,s3;
//                s1=String.valueOf(area);
//                s2=String.valueOf(friendly);
//                s3=String.valueOf(style);
                Intent intent = new Intent(MainActivity.this,Link.class);
//                if(areas.getText().equals("選擇地區")){areas.setText("");}
//                if(friendlys.getText().equals("友善類型")){friendlys.setText("");}
//                if(styles.getText().equals("餐廳菜單類型")){areas.setText("");}
                intent.putExtra("areas",areas.getText());
                Log.d("areas=",String.valueOf(areas.getText()));
                intent.putExtra("friendlys",friendlys.getText());
                Log.d("friendlys=",String.valueOf(friendlys.getText()));
                intent.putExtra("styles",styles.getText());
                Log.d("styles=",String.valueOf(styles.getText()));
                //bag.putString("areas",String.valueOf(area));
                startActivity(intent);
            }
        });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Intent i = new Intent(this, Homepage.class);
                startActivity(i);

                break;
        }
        return true;

    }
}
