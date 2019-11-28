package com.example.youi_beta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message extends AppCompatActivity {
    private static final String DB_FILE = "comment.db",
            DB_TABLE = "comment",
            DB_TABLE2="collect";
    FirebaseAuth auth;

    public RecyclerView mRecyclerView;
    public ListAdapter adapter=null;
    private  DbOpenHelper mDbOpenHelper;

    private EditText mEdtName,
            mEdtTitle,
            mEdtMessage;
    private RatingBar mEditRating;
    private TextView mTxtList;
    public SQLiteDatabase commentDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        ArrayList<article> data=new ArrayList<>();
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
//        mDbOpenHelper =
//                new DbOpenHelper(getApplicationContext(), "comment.db", null, 1);
//        SQLiteDatabase commentDb = mDbOpenHelper.getWritableDatabase();
//
//        Cursor c = commentDb.query(true, " comment",new String[]{"name", "title",
//                "article","time","rating"}, null, null, null, null, null, null);
//        if (c == null)
//            return;
//
//        if (c.getCount() == 0) {
//
//            Toast.makeText(Message.this, "沒有資料", Toast.LENGTH_SHORT).show();
//
//        } else {
//            c.moveToFirst();
//            data.add(new article(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getFloat(4)));
//
//            while (c.moveToNext())
//                data.add(new article(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getFloat(4)));
//        }
//        adapter=new ListAdapter(data);
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setAdapter(adapter);
//        commentDb.close();
//        //
        mEdtName = findViewById(R.id.edt_name);
        mEdtTitle = findViewById(R.id.edt_article);
        mEdtMessage = findViewById(R.id.edt_messqge);
        mEditRating=findViewById(R.id.ratingBar);


        Button btnAdd = findViewById(R.id.btn_add);
        Button btn_clear = findViewById(R.id.btn_clear);
        btnAdd.setOnClickListener(btnAddOnClick);
        btn_clear.setOnClickListener(btnList3OnClick);
        //
    }

    private View.OnClickListener btnList3OnClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           mEdtName.setText("");
            mEdtTitle.setText("");
            mEdtMessage.setText("");
            mEditRating.setRating(0);

        }
    };

    private View.OnClickListener btnAddOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDbOpenHelper =
                    new DbOpenHelper(getApplicationContext(), DB_FILE, null, 1);
            SQLiteDatabase commentDb = mDbOpenHelper.getWritableDatabase();
            Date now=new Date();
            SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Log.d("time",ft.format(now));
            ContentValues newRow = new ContentValues();
            newRow.put("name", mEdtName.getText().toString());
            newRow.put("title", mEdtTitle.getText().toString());
            newRow.put("article", mEdtMessage.getText().toString());
            newRow.put("rating",mEditRating.getRating());
            newRow.put("time",ft.format(now));

            commentDb.insert(DB_TABLE, null, newRow);

            commentDb.close();
            Toast.makeText(Message.this,"新增成功",Toast.LENGTH_SHORT).show();
            //adapter.notifyItemChanged();
           // adapter.notifyDataSetChanged();
            //adapter.notifyItemInserted();
            //adapter.notify();
            Intent i=new Intent(Message.this,ListShowActivity.class);

            startActivity(i);
            finish();

        }
    };

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

