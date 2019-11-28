package com.example.youi_beta;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private  DbOpenHelper mDbOpenHelper;
    private Context mCtx;
    SQLiteDatabase db=null;
    AlertDialog.Builder builder = null;
    AlertDialog dialog=null;

    Cursor cursor;
    int id;
    private ArrayList<article>articleArrayList;
    public ListAdapter(ArrayList<article>articleArrayList){

        this.articleArrayList=articleArrayList;}
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //對應子畫面，宣告viewholder與回傳vh
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,parent,false);

        ViewHolder vh=new ViewHolder(v);
            return  vh;
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage("https://explicative-offense.000webhostapp.com/PHP/YOUI/YOUI_Image/images.jpg",holder.list_pic);
        holder.name.setText(articleArrayList.get(position).getName());
        holder.title.setText(articleArrayList.get(position).getTitle());
        holder.article.setText(articleArrayList.get(position).getArticle());
        holder.time.setText(articleArrayList.get(position).getTime());
        holder.ratingBar.setRating(articleArrayList.get(position).getRating());
        Log.d("name=",articleArrayList.get(position).getName());
        Log.d("title=",articleArrayList.get(position).getTitle());
        Log.d("article=",articleArrayList.get(position).getArticle());
        Log.d("time=",articleArrayList.get(position).getTime());
        Log.d("rating=",String.valueOf(articleArrayList.get(position).getRating()));

    }
    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView name,title,time;
        public Button btn_edit,btn_delete;
        public EditText article;
        public ImageView list_pic;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.txtName);
            title=itemView.findViewById(R.id.txtTitle);
            article=itemView.findViewById(R.id.txtArticle);
            time=itemView.findViewById(R.id.txtTime);
            ratingBar=itemView.findViewById(R.id.ratingBar2);
            list_pic=itemView.findViewById(R.id.list_pic);


            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_delete=itemView.findViewById(R.id.btn_delete);
            btn_edit=itemView.findViewById(R.id.btn_edit);


            article.setFocusable(false);
            article.setFocusableInTouchMode(false);
            btn_edit.setOnClickListener(new View.OnClickListener(){
                Boolean edit=false;
                @Override

                public void onClick(View v) {
                    if(edit==false){
                    edit=true;
                    //getItemId();
                    btn_edit.setText("儲存變更");
                        Log.e("StartOfEdit","輸入開始");
                    article.setFocusableInTouchMode(true);
                    article.setFocusable(true);
                    article.requestFocus();}
                    else{edit=false;
                        article a=articleArrayList.get(getAdapterPosition());
                        final ContentValues contentValues = new ContentValues();
                        final String[]editTime={String.valueOf(a.getTime())};
                        final Context context=v.getContext();

                        Log.e("EndOfEdit","輸入結束");
                        article.setFocusable(false);
                        Date now=new Date();
                        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        article.setFocusableInTouchMode(false);
                        contentValues.put("article",article.getText().toString());
                        contentValues.put("time",ft.format(now));
                        btn_edit.setText("編輯");
                        article.setText(article.getText());
                        mDbOpenHelper=new DbOpenHelper(context,"comment.db",null,1);
                        SQLiteDatabase commentDb = mDbOpenHelper.getWritableDatabase();
                        commentDb.update("comment",contentValues,"time=?",editTime);
                        //notifyDataSetChanged();
                        articleArrayList.get(getAdapterPosition());
                        //notifyItemChanged(getAdapterPosition());
                        commentDb.close();
                    }
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener(){
                @RequiresApi(api = Build.VERSION_CODES.O_MR1)
                @Override
                public void onClick(View v) {

                    final Context context=v.getContext();

                    article a=articleArrayList.get(getAdapterPosition());
                    Log.d("position=", String.valueOf(getAdapterPosition()));
                    Log.d("time=",a.getTime());
                    final String[]deleteTime={String.valueOf(a.getTime())};
                    //
                    builder = new AlertDialog.Builder(context);
                    builder.setTitle("meaasge")
                            .setMessage("確定刪除此留言?")
                            .setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    mDbOpenHelper=new DbOpenHelper(context,"comment.db",null,1);
                                    SQLiteDatabase commentDb = mDbOpenHelper.getWritableDatabase();
                                    commentDb.delete("comment","time=?",deleteTime);

                                    commentDb.close();
                                    articleArrayList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    dialog.dismiss();

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                }
                            });
                    dialog=builder.create();
                    dialog.show();

                }
            });



            itemView.setOnClickListener(new View.OnClickListener () {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.btn_edit:
                            break;
                        case R.id.btn_delete:
                            getItemId();
                            Log.d("ItemId=",String.valueOf(getItemId()));
                            break;
                    }
                }
             });

            }

        }

    }




