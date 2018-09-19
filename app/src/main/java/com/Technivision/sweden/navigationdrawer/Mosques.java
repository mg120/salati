package com.Technivision.sweden.navigationdrawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Mosques extends AppCompatActivity {

    String url = "http://e-marknad.com/app/subject/index/page/6";

    private TextView id_textV, name_textV , subject_textV, dir_textV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosques);


        id_textV = (TextView) findViewById(R.id.id_p);
        name_textV = (TextView) findViewById(R.id.name_p);
        subject_textV = (TextView) findViewById(R.id.subject_p);
        dir_textV = (TextView) findViewById(R.id.dir_p);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data_obj = jsonObject.getJSONObject("Data");
                    id_textV.setText(data_obj.getString("id_p"));
                    name_textV.setText(data_obj.getString("name_p"));
                    subject_textV.setText(data_obj.getString("subject_p"));
                    dir_textV.setText(data_obj.getString("dir_p"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Mosques.this , "Error Connection", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
