package com.example.youi_beta;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListShowActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public DbOpenHelper mDbOpenHelper;
    public ListAdapter adapter=null;
    Button btn_add,btn_edit,btn_delete;
    private static final String DB_FILE = "comment.db",
            DB_TABLE = "comment";

    public SQLiteDatabase commentDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagelist);
        //mdb初始化開始
        mDbOpenHelper =
                new DbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
        commentDb = mDbOpenHelper.getWritableDatabase();

        // 檢查資料表是否已經存在
        Cursor cursor = commentDb.rawQuery(
                "select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                        DB_TABLE + "'", null);

        if (cursor != null) {
            if (cursor.getCount() == 0)    // 沒有資料表，需要建立一個資料表
                commentDb.execSQL("CREATE TABLE " + DB_TABLE + " (" +
                        "_id INTEGER PRIMARY KEY," +
                        "name TEXT NOT NULL," +
                        "title TEXT," +
                        "article TEXT,"+
                        "time TEXT,"+
                        "rating REAL);");
            //cursor.close();
            //commentDb.close();
        }
        cursor.close();
        commentDb.close();
//        //mdb初始化結束
        btn_add=findViewById(R.id.addComment);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(ListShowActivity.this,Message.class);
                startActivity(i);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        ArrayList<article> data=new ArrayList<>();
        mDbOpenHelper =
                new DbOpenHelper(getApplicationContext(), "comment.db", null, 1);
        SQLiteDatabase commentDb = mDbOpenHelper.getWritableDatabase();

        Cursor c = commentDb.query(true, " comment", new String[]{"name", "title",
                "article","time","rating"}, null, null, null, null, null, null);
        if (c == null)
            return;

        if (c.getCount() == 0) {

            Toast.makeText(ListShowActivity.this, "沒有資料", Toast.LENGTH_SHORT).show();

        } else {
            c.moveToFirst();
            data.add(new article(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getFloat(4)));

            while (c.moveToNext())
                data.add(new article(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getFloat(4)));
        }


        adapter=new ListAdapter(data);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        commentDb.close();

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
