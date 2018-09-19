package com.Technivision.sweden.navigationdrawer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

public class Azan extends AppCompatActivity {

    private ListView listView;
    private TextView back;
    private String azan_url = "http://e-marknad.com/app/subject/channels/azan";
    public ArrayList<AzanModel> data_model = new ArrayList<>();
    public static String seslected_url ;
    public static int  seslected_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azan);

        listView = (ListView) findViewById(R.id.list_view);
        back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        StringRequest request = new StringRequest(Request.Method.GET, azan_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    dataParsing(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Azan.this);
                builder.setTitle("Internet")
                        .setMessage("Error Connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


        // listview  item long click .....
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                seslected_url = data_model.get(i).getSound_url();
                seslected_id = data_model.get(i).getId();
                // Toast.makeText(Azan.this , selected_item + " "+ id , Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(Azan.this);
                builder.setTitle("Azan")
                        .setMessage(data_model.get(i).getName() + "Selected")
                        .setCancelable(false);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        finish();
                    }
                });

                AlertDialog alert11 = builder.create();
                alert11.show();

                return true;
            }
        });

    }

    private void dataParsing(String response) throws JSONException {


        JSONObject jsonObject = new JSONObject(response);
        boolean success = jsonObject.getBoolean("Status");

        if (success) {

            JSONArray jsonArray = jsonObject.getJSONArray("Data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                int id = jsonObject1.getInt("id");
                String name = jsonObject1.getString("name");
                String description = jsonObject1.getString("description");
                String sound = jsonObject1.getString("sound");

                data_model.add(new AzanModel(id, name, description, sound));

//                Toast.makeText(Azan.this, data_model.get(i).getSound_url(), Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(this, String.valueOf(data_model.size()), Toast.LENGTH_SHORT).show();
            AzanAdapter adapter = new AzanAdapter(Azan.this, data_model);
            listView.setAdapter(adapter);
//            Toast.makeText(Azan.this, String.valueOf(data_model.size()), Toast.LENGTH_SHORT).show();


//            Toast.makeText(Azan.this, selected_url, Toast.LENGTH_SHORT).show();
        }

    }
}
