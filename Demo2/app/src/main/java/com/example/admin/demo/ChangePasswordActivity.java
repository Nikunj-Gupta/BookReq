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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String CHANGE_PASSWORD_URL = "http://172.16.80.54:8080/proj/change_password.php";
    public static final String KEY_UID = "uid";
    public static final String KEY_MESSAGE = "message";
    private static final String KEY_TIMESTAMP = "tstamp";
    private static final String KEY_STATUS = "status";
    private static final String KEY_VALUE = "value";

    private static final String KEY_CURRENT_PASSWORD = "curr_pass";
    private static final String KEY_NEW_PASSWORD = "new_pass";


    private String USERNAME;
    private String UID;
    private String password;
    private String VALUE = "false";

    private EditText current_password;
    private EditText new_password;

    private Button butChange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i;
        i = getIntent();
        String username = i.getStringExtra("username");
        String uid = i.getStringExtra("uid");

        USERNAME = username;
        UID = uid;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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

        current_password = (EditText) findViewById(R.id.editText12);
        new_password = (EditText) findViewById(R.id.editText13);


        butChange = (Button) findViewById(R.id.buttonSubmit);

        butChange.setOnClickListener(this);

    }

    public void changePassword()
    {

        final String current_pass = current_password.getText().toString().trim();
        final String new_pass = new_password.getText().toString().trim();

        if(new_pass.equals("")){
            Toast.makeText(ChangePasswordActivity.this, "Enter new password!" , Toast.LENGTH_LONG).show();
        }

        else {


            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, CHANGE_PASSWORD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) throws JSONException {
                            if(!response.trim().equals("failure")){
                                Toast.makeText(ChangePasswordActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Wrong Details Entered!", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ChangePasswordActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
                    map.put(KEY_UID, UID);
                    map.put(KEY_CURRENT_PASSWORD, current_pass);
                    map.put(KEY_NEW_PASSWORD, new_pass);
                    map.put(KEY_VALUE, VALUE);
                    return map;
                }
            };

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }

    public void checkCurrentPassword(String response) throws JSONException {
        JSONObject jsonobj = new JSONObject(response);
        String id = jsonobj.getString("result");
        JSONObject jsonobj2 = new JSONObject(id);

        String pass = jsonobj2.getString("3");
        Toast.makeText(ChangePasswordActivity.this,id.toString().trim() /*"Password : " + pass*/, Toast.LENGTH_LONG).show();

        password = pass.toString();

        if (!password.equals(current_password))
            Toast.makeText(ChangePasswordActivity.this,"Current Passwords do not match", Toast.LENGTH_LONG).show();
        else {
            VALUE = "true";
            Toast.makeText(ChangePasswordActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onClick(View v) {
        if (v==butChange) {
            changePassword();
        }
    }

}
