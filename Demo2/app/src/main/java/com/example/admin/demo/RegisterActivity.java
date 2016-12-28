package com.example.admin.demo;

import android.content.Intent;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://172.16.80.54:8080/proj/register.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SALT = "salt";

    public static String SALT;



    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button buttonRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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



        editTextUsername = (EditText) findViewById(R.id.editText3);
        editTextEmail= (EditText) findViewById(R.id.editText4);
        editTextPassword = (EditText) findViewById(R.id.editText5);


        buttonRegister = (Button) findViewById(R.id.regButton);

        buttonRegister.setOnClickListener(this);

    }

    private void registerUser() throws NoSuchAlgorithmException {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        byte[] salt = getSalt();
        password = hashPassWord(password);
        Toast.makeText(RegisterActivity.this, "Hashed Password: " + password.trim() + "\nPlease Login now", Toast.LENGTH_LONG).show();


        final String finalPassword = password;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.toString().trim().equals("Could not register")){
                            Toast.makeText(RegisterActivity.this, response.trim() + "\nPlease Login now", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(RegisterActivity.this, response.toString().trim(), Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, finalPassword);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        if (v==buttonRegister) {
            try {
                registerUser();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
