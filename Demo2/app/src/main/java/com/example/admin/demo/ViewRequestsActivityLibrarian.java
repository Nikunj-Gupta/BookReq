package com.example.admin.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class ViewRequestsActivityLibrarian extends AppCompatActivity {


    public static final String READ_REQUESTS_URL = "http://172.16.80.54:8080/proj/read_accepted_requests.php";
    public static final String UPDATE_ACCEPT_URL = "http://172.16.80.54:8080/proj/update_order_requests.php";
    public static final String UPDATE_REJECT_URL = "http://172.16.80.54:8080/proj/update_couldnotorder_requests.php";
    public static final String UPDATE_PROCURED_URL = "http://172.16.80.54:8080/proj/update_procured_requests.php";



    public static final String KEY_USERNAME = "username";


    public static final String KEY_UID = "uid";
    public static final String KEY_MESSAGE = "message";
    private static final String KEY_TIMESTAMP = "tstamp";
    private static final String KEY_STATUS = "status";

    private String MESSAGE;


    private String USERNAME;
    private String UID;

    private String acceptUID;
    private String rejectUID;
    private String procureUID;

    private Context context;

    List<Request> requests = new ArrayList<Request>();

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv;
    String[] temp;
    //final ArrayList<String> list = new ArrayList<String>();

    /*android.os.Handler handler = new android.os.Handler() {

    };
    Runnable r;*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i;
        i = getIntent();
        String username = i.getStringExtra("username");
        String uid = i.getStringExtra("uid");

        USERNAME = username;
        UID = uid;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_requests_activity_librarian);
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


        /* r = new Runnable() {
            @Override
            public void run() {
                ViewRequests();


            }
        };*/
        ViewRequests();


    }

    public void ViewRequests() {


        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, READ_REQUESTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        showList(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
//                map.put(KEY_UID, UID);
//                map.put(KEY_USERLEVEL, userlevel);
                return map;
            }
        };

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //handler.postDelayed(r, 2000);


    }


    public void showList(String response) {


        try {


            JSONObject jsonObj = new JSONObject(response);
            JSONArray jArray = jsonObj.getJSONArray("result");
            String uid = "NULL";
            String message = "NULL";
            String time = "NULL";
            String status = "NULL";
            String[] req = new String[jArray.length()];
            String test = "";


            for (int i = 0; i < jArray.length(); i++) {
                try {
                    JSONObject jObj = jArray.getJSONObject(i);

                    uid = jObj.getString("1");
                    message = jObj.getString("2");
                    time = jObj.getString("3");
                    status = jObj.getString("8");
                    test = "(" + uid + ")" + "\nBook:\n" + message + "\nTime of Request: " + time + "\nStatus: " + status;
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

            ListView lv = (ListView) findViewById(R.id.listView3);
            lv.setAdapter(adapter);




                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        myDialogBox(parent, position);
                    }
                });



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void myDialogBox(final AdapterView<?> parent, final int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Order?");

        builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = (String) parent.getItemAtPosition(position);
                int x = 0;
                while (name.charAt(x) != ')') {
                    x++;
                }

                String uid = name.substring(1, x);
                //int user = Integer.parseInt(uid);
                accept(uid);
                Toast.makeText(ViewRequestsActivityLibrarian.this, "You clicked Order button\n" + uid, Toast.LENGTH_LONG).show();
                ViewRequests();

            }
        });

        builder.setNeutralButton("Cannot Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = (String) parent.getItemAtPosition(position);
                int x = 0;
                while (name.charAt(x) != ')') {
                    x++;
                }

                String uid = name.substring(1, x);
                //int user = Integer.parseInt(uid);
                reject(uid);
                Toast.makeText(ViewRequestsActivityLibrarian.this, "You clicked Cannot Order button", Toast.LENGTH_LONG).show();
                ViewRequests();

            }
        });

        builder.setNegativeButton("Procured", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = (String) parent.getItemAtPosition(position);
                int x=0;
                while(name.charAt(x) != ')')
                {
                    x++;
                }

                String uid = name.substring(1, x);
                //int user = Integer.parseInt(uid);
                procure(uid, name);
                Toast.makeText(ViewRequestsActivityLibrarian.this, "You clicked Procured button", Toast.LENGTH_LONG).show();
                ViewRequests();

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void accept(String uid)
    {

        acceptUID = uid;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, UPDATE_ACCEPT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
                map.put(KEY_UID, acceptUID);
//                map.put(KEY_USERLEVEL, userlevel);
                return map;
            }
        };

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void reject(String uid)
    {

        rejectUID = uid;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, UPDATE_REJECT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
                map.put(KEY_UID, rejectUID);
//                map.put(KEY_USERLEVEL, userlevel);
                return map;
            }
        };

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void procure(String uid, final String name)
    {

        procureUID = uid;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, UPDATE_PROCURED_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) throws JSONException {

                        sendEMAILID(response, name);

                        //Toast.makeText(ViewRequestsActivityLibrarian.this, response.toString().trim(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewRequestsActivityLibrarian.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
//                map.put(KEY_USERNAME, username);
//                map.put(KEY_PASSWORD, password);
                map.put(KEY_UID, procureUID);
//                map.put(KEY_USERLEVEL, userlevel);
                return map;
            }
        };

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void sendEMAILID(String response, String name) throws JSONException
    {
        JSONObject jsonobj = new JSONObject(response);
        String id = jsonobj.getString("result");
        JSONObject jsonobj2 = new JSONObject(id);

        String emailid = jsonobj2.getString("2");
        String message = "Your Book has Arrived. Kindly collect it from the library. " +
                "\n\nBook Details:\n" + name.trim() + "\n\nThanking you,\nLibrarian IIIT-B.";
        String subject = "Your ordered book has arrived";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailid});
        email.putExtra(Intent.EXTRA_TEXT, message);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Send Mail..."));



    }


}
