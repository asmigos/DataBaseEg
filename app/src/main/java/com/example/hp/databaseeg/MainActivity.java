package com.example.hp.databaseeg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    Button insert,show;
    EditText firstname,lastname,age;
    TextView tv;
    RequestQueue requestQueue;
    String insertUrl="http://ekumeed.esy.es/insertStudent.php";
    String showUrl="http://ekumeed.esy.es/showstudent.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insert=(Button)findViewById(R.id.insert);
        show=(Button)findViewById(R.id.show);


        firstname=(EditText) findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        age=(EditText)findViewById(R.id.age);

        tv=(TextView)findViewById(R.id.tv);
        requestQueue= Volley.newRequestQueue(getApplicationContext());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students= response.getJSONArray("students");

                            for (int i=0; i<students.length();i++){
                                  JSONObject student= students.getJSONObject(i);
                                String firstname=student.getString("firstname");
                                String lastname=student.getString("lastname");
                                String age=student.getString("age");

                                tv.append(firstname+" "+lastname+" "+age+"\n");
                            }
                            tv.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }
                        , new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parameter = new HashMap<String, String>();
                        parameter.put("firstname", firstname.getText().toString());
                        parameter.put("lastname", lastname.getText().toString());
                        parameter.put("age", age.getText().toString());

                        return parameter;

                    }
                };
                requestQueue.add(request);

            }
        });


    }
}
