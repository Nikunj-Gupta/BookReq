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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOGIN_URL = "http://172.16.80.54:8080/proj/login.php";
    //public static final String READ_URL = "http://172.16.80.54:8080/proj/read.php";

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";
    public static final String KEY_UID = "uid";
    public static final String KEY_USERLEVEL = "userlevel";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    private String username;
    private String password;
    private String uid;
    private String userlevel;
    private static String SALT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Welcome!", Snackbar.LENGTH_LONG)
                            .setAction("", null).show();
                }
            });
        }


        editTextUsername = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);

        buttonLogin = (Button) findViewById(R.id.buttonReq);

        assert buttonLogin != null;
        buttonLogin.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    private void userLogin() {
        username = editTextUsername.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        try {
            byte[] salt = getSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            password = hashPassWord(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = sharedpreferences.edit();

        uid = "-1";
        userlevel = "-1";

        if(username.equals("")){
            Toast.makeText(LoginActivity.this, "Enter username!" , Toast.LENGTH_LONG).show();
        }

        else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();

                            if (!response.trim().equals("failure")) {
                                try {
                                    openProfile(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //Toast.makeText(LoginActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this, "Sorry! Wrong Password OR Not Registered!", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_USERNAME, username);
                    map.put(KEY_PASSWORD, password);
                    map.put(KEY_UID, uid);
                    map.put(KEY_USERLEVEL, userlevel);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_PASSWORD, password);
            editor.putString(KEY_UID, uid);
            editor.putString(KEY_USERLEVEL, userlevel);
            editor.commit();
        }
    }

    private void openProfile(String response) throws JSONException {

        JSONObject jsonobj = new JSONObject(response);
        String id = jsonobj.getString("result");
        JSONObject jsonobj2 = new JSONObject(id);

        String userid = jsonobj2.getString("0");
        uid = userid.toString();
        //Toast.makeText(LoginActivity.this, "uid = " + uid, Toast.LENGTH_LONG).show();

        String level = jsonobj2.getString("4");
        userlevel = level.toString();
        //Toast.makeText(LoginActivity.this, "userlevel = " + userlevel, Toast.LENGTH_LONG).show();


        // Checking User Level
        if(Integer.parseInt(level.trim()) == 1 ) {

            Intent intent = new Intent(LoginActivity.this, AfterLoginUser.class);
            intent.putExtra(KEY_USERNAME, username);
            intent.putExtra(KEY_UID, uid);
            startActivity(intent);
        }
        else if(Integer.parseInt(level.trim()) == 2 )
        {


            Intent intent = new Intent(LoginActivity.this, AfterLoginLibrarian.class);
            intent.putExtra(KEY_USERNAME, username);
            intent.putExtra(KEY_UID, uid);
            startActivity(intent);
        }
        else if(Integer.parseInt(level.trim()) == 3 )
        {

            Intent intent = new Intent(LoginActivity.this, AfterLoginAdmin.class);
            intent.putExtra(KEY_USERNAME, username);
            intent.putExtra(KEY_UID, uid);
            startActivity(intent);
        }

    }

    public String hashPassWord(String pass) throws NoSuchAlgorithmException {

        String generatedPassword = null;
        //byte[] salt = getSalt();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(pass.getBytes());
            byte[] tempSalt = md.digest(SALT.getBytes());
            md.update(tempSalt);
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                //sb.append(tempSalt);
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;



       /* String hashed="";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            hashed = Base64.encodeToString(md.digest(),);
            byte[] digest = md.digest();
            hashed = digest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return hashed;*/
    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        SALT = salt.toString();
        return salt;
    }

    @Override
    public void onClick(View v) {
        if (v==buttonLogin) {
            userLogin();
        }
    }

}

