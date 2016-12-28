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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ViewRequestsActivityUser extends AppCompatActivity {

    public static final String READ_REQUESTS_URL = "http://172.16.80.54:8080/proj/read_user_request.php";


    public static final String KEY_USERNAME = "username";


    public static final String KEY_UID = "uid";
    public static final String KEY_MESSAGE = "message";
    private static final String KEY_TIMESTAMP = "tstamp";
    private static final String KEY_STATUS = "status";


    private String USERNAME;
    private String UID;

    private Context context;

    List<com.example.admin.demo.Request> requests = new ArrayList<com.example.admin.demo.Request>();

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv;
    String[] temp;
    //final ArrayList<String> list = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i;
        i = getIntent();
        String username = i.getStringExtra("username");
        String uid = i.getStringExtra("uid");

        USERNAME = username;
        UID = uid;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests_activity_user);
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


        ViewRequests();

   }


     public void ViewRequests() {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, READ_REQUESTS_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) throws JSONException {
                            showList(response);

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ViewRequestsActivityUser.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
                    map.put(KEY_UID, UID);
//                map.put(KEY_USERLEVEL, userlevel);
                    return map;
                }
            };

            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }


        public void showList(String response) {


            try {


                JSONObject jsonObj = new JSONObject(response);
                JSONArray jArray = jsonObj.getJSONArray("result");
                String message = "NULL";
                String time = "NULL";
                String status = "NULL";
                String[] req = new String[jArray.length()];
                String test = "";


                for (int i = 0; i < jArray.length(); i++) {
                    try {
                        JSONObject jObj = jArray.getJSONObject(i);


                        message = jObj.getString("2");
                        time = jObj.getString("3");
                        status = jObj.getString("8");
                        test = "Book:\n" + message + "\nTime of Request: " + time + "\nStatus: " + status;
                        req[i] = test;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                temp = req;


               /* for(int i=0;i<temp.length;i++)
                {
                    Toast.makeText(ViewRequestsActivityUser.this,temp[i],Toast.LENGTH_SHORT ).show();
                    Toast.makeText(ViewRequestsActivityUser.this,"Hello World!",Toast.LENGTH_SHORT ).show();
                }*/

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view_book,R.id.textView5, temp);

                ListView lv = (ListView) findViewById(R.id.listView);
                lv.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

}


