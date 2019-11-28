package com.example.youi_beta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    TextView info;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        info=findViewById(R.id.info);
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(
                    @NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null) {
                    Log.d("onAuthStateChanged", "登入:"+
                            user.getUid());
                    user.getEmail();
                    user.getDisplayName();
                    user.sendEmailVerification();

                    userUID =  user.getUid();
                    if(user.isEmailVerified())
                    {Log.d("verified=","OK");}
                    info.setText(user.getUid()+"\n"+user.getDisplayName()+"\n"+user.getEmail()+"\n"+user.toString()+"\n"+user.getPhotoUrl()+"\n"+"已登入");
                }else{
                    Log.d("onAuthStateChanged", "已登出");
                    //info.setText(user.getUid()+"已登出");
                }
            }
        };

    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authListener);
    }
    public void login(View v){
        final String email = ((EditText)findViewById(R.id.email))
                .getText().toString();
        final String password = ((EditText)findViewById(R.id.password))
                .getText().toString();
        Log.d("AUTH", email+"/"+password);
        info.setText("AUTH"+email+"/n"+password);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Log.d("onComplete", "登入失敗");
                    info.setText("登入失敗");
                    register(email, password);}
                else{info.setText("登入成功");
                    Thread timer = new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(2000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finally {
                                startActivity(new Intent(Login.this,Homepage.class));
                                finish();
                            }
                        }
                    };
                    timer.start();


                }
            }
        });
    }
    private void register(final String email, final String password) {
        new AlertDialog.Builder(Login.this)
                .setTitle("登入問題")
                .setMessage("無此帳號，是否要以此帳號與密碼註冊?")
                .setPositiveButton("註冊",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createUser(email, password);
                            }
                        })
                .setNeutralButton("取消", null)
                .show();
    }
    private void createUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                String message =
                                        task.isSuccessful() ? "註冊成功" : "註冊失敗";
                                new AlertDialog.Builder(Login.this)
                                        .setMessage(message)
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                        });
    }




}



