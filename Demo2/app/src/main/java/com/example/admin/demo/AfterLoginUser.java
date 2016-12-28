package com.example.admin.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AfterLoginUser extends AppCompatActivity{
    public static final String KEY_USERNAME = "username";
    public static final String KEY_UID = "uid";

//    Button but1 = (Button) findViewById(R.id.buttonReq);

    private String USERNAME;
    private String UID;




    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent i;
        i = getIntent();
        String username = i.getStringExtra("username");
        String uid = i.getStringExtra("uid");

        USERNAME = username;
        UID = uid;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Logged In!", Snackbar.LENGTH_LONG)
                            .setAction("Logout", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*Intent intent = new Intent(AfterLoginUser.this, MainActivity.class);
                                    startActivity(intent);*/
                                    SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            }).show();
                }
            });
        }
    }

    public void request(View v)
    {
        Intent intent = new Intent(AfterLoginUser.this, BookRequestActivity.class);
        intent.putExtra(KEY_USERNAME, USERNAME);
        intent.putExtra(KEY_UID, UID);
        startActivity(intent);

    }

    public void view_requests(View v)
    {
        Intent intent = new Intent(AfterLoginUser.this, ViewRequestsActivityUser.class);
        intent.putExtra(KEY_USERNAME, USERNAME);
        intent.putExtra(KEY_UID, UID);
        startActivity(intent);

    }
    public void change_password(View v)
    {
        Intent intent = new Intent(AfterLoginUser.this, ChangePasswordActivity.class);
        intent.putExtra(KEY_USERNAME, USERNAME);
        intent.putExtra(KEY_UID, UID);
        startActivity(intent);

    }
}

