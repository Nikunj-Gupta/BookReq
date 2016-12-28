package com.example.admin.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BookRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUEST_URL = "http://172.16.80.54:8080/proj/request_book.php";


//    public static final String KEY_USERNAME = "username";
    public static final String KEY_UID = "uid";

   /* public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_PUBLISHER = "publisher";
    public static final String KEY_ISBN = "isbn";
    public static final String KEY_VOLUME = "volume";
    public static final String KEY_EDITION = "edition";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_CATEGORY = "category";*/

    public static final String KEY_MESSAGE = "message";


    private String USERNAME;
    private String UID;

    private EditText edit_text_title;
    private EditText edit_text_author;
    private EditText edit_text_publisher;
    private EditText edit_text_isbn;
    private EditText edit_text_volume;
    private EditText edit_text_edition;
    private EditText edit_text_subject;
    private EditText edit_text_category;

    private Button butRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        Intent i;
        i = getIntent();
        String username = i.getStringExtra("username");
        String uid = i.getStringExtra("uid");

        USERNAME = username;
        UID = uid;



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_request);
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


        edit_text_title = (EditText) findViewById(R.id.editText14);
        edit_text_author = (EditText) findViewById(R.id.editText15);
        edit_text_publisher = (EditText) findViewById(R.id.editText16);
        edit_text_isbn = (EditText) findViewById(R.id.editText17);
        edit_text_volume = (EditText) findViewById(R.id.editText18);
        edit_text_edition = (EditText) findViewById(R.id.editText19);
        edit_text_subject = (EditText) findViewById(R.id.editText20);
        edit_text_category = (EditText) findViewById(R.id.editText21);


        butRequest = (Button) findViewById(R.id.buttonRequest);

        butRequest.setOnClickListener(this);
    }

    public void requestBook()
    {
        final String title = edit_text_title.getText().toString().trim();
        final String author = edit_text_author.getText().toString().trim();
        final String publisher = edit_text_publisher.getText().toString().trim();
        final String isbn = edit_text_isbn.getText().toString().trim();
        final String volume = edit_text_volume.getText().toString().trim();
        final String edition = edit_text_edition.getText().toString().trim();
        final String subject = edit_text_subject.getText().toString().trim();
        final String category = edit_text_category.getText().toString().trim();



        final String message = "Title: " + title + "\n" + "Author: " + author + "\n" + "Publisher: " + publisher +
                "\n" + "ISBN: " + isbn + "\n" + "Volume: " + volume + "\n" + "Edition: " + edition + "\n" +
                    "Subject: " + subject + "\n" + "Category: " + category;

        if(message.equals("")){
            Toast.makeText(BookRequestActivity.this, "Enter the details" , Toast.LENGTH_LONG).show();
        }

        else{

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BookRequestActivity.this, response.trim() , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookRequestActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                /*params.put(KEY_TITLE,title);
                params.put(KEY_AUTHOR,author);
                params.put(KEY_PUBLISHER,publisher);
                params.put(KEY_ISBN,isbn);
                params.put(KEY_VOLUME,volume);
                params.put(KEY_EDITION,edition);
                params.put(KEY_SUBJECT,subject);
                params.put(KEY_CATEGORY,category);*/
                params.put(KEY_UID,UID);
                params.put(KEY_MESSAGE,message);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
    }


    @Override
    public void onClick(View v) {
        if (v==butRequest) {
            requestBook();
        }
    }

}
