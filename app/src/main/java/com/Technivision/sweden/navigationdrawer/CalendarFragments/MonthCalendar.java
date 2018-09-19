package com.Technivision.sweden.navigationdrawer.CalendarFragments;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.Technivision.sweden.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthCalendar extends FragmentActivity {

    ArrayList <String> mm = new ArrayList<>();
    ArrayList <CalenderModel> calender_data = new ArrayList<>();
    LinearLayout myLinearLayout;
    RequestQueue requestQueue;
    Typeface custom_font;
    Button close_btn ;
    ListView listView ;
    int monthnum;
    private  static final String calendar_url = "http://e-marknad.com/app/subject/channels/getCalender";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_calendar);

        custom_font = Typeface.createFromAsset(getAssets(), "font/font.ttf");

        listView = (ListView) findViewById(R.id.calendar_list);
        close_btn = (Button) findViewById(R.id.close);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);

        // getting current date and day name ..
        Calendar calendar = Calendar.getInstance();
        int cur_minute = calendar.get(Calendar.MINUTE);
        int cur_hours = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int monthday = calendar.get(Calendar.DAY_OF_MONTH);
        monthnum = calendar.get(Calendar.MONTH) + 1;

        gettingData();


    }

    public void gettingData() {

        requestQueue = Volley.newRequestQueue(MonthCalendar.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, calendar_url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("Status");


                            if (success){

                                JSONArray data_jsonArray = response.getJSONArray("Data");

                                for (int i=0 ; i<data_jsonArray.length() ; i++){

                                    JSONObject month_object = data_jsonArray.getJSONObject(i);
                                    String month_id = month_object.getString("id");
                                    String month_number = month_object.getString("month");
                                    String month_name = month_object.getString("name");

                                    mm.add(month_name);

                                    if ( monthnum == Integer.parseInt(month_number)){
                                        JSONArray days_jsonarray =  month_object.getJSONArray("subDay");
                                        for (int j=0 ; j<days_jsonarray.length();j++) {

                                            JSONObject days_obj = days_jsonarray.getJSONObject(j);
                                            String day_name = days_obj.getString("dayname");
                                            JSONObject meqat_day_obj = days_obj.getJSONObject("meqat");
                                            String day_number = meqat_day_obj.getString("day");
                                            String fajr = meqat_day_obj.getString("Fajr");
                                            String zuhr = meqat_day_obj.getString("Zuhr");
                                            String aser = meqat_day_obj.getString("Asr");
                                            String maghrib = meqat_day_obj.getString("Maghrib");
                                            String isha = meqat_day_obj.getString("Isha");

                                            calender_data.add(new CalenderModel(month_id, month_number, day_name, day_number, fajr, zuhr, aser, maghrib, isha));
                                        }
                                    }
                                }

                                CalenderAdapter adapter = new CalenderAdapter(MonthCalendar.this , calender_data);
                                listView.setAdapter(adapter);

                                int size = mm.size();
                                TextView[] tv = new TextView[size];
                                TextView temp;
                                for (int i = 0; i < size; i++)
                                {
                                    temp = new TextView(MonthCalendar.this);
                                    temp.setTypeface(custom_font);
                                    temp.setText("  "+ mm.get(i).toString() + "  "); //arbitrary task
                                     int finalI = i;
                                     final int finalI1 = i;
                                    temp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            monthnum = finalI1+1;
                                            listView.setAdapter(null);
                                            calender_data.clear();
                                            gettingData();

                                        }
                                    });

                                    myLinearLayout.addView(temp);
                                    tv[i] = temp;
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", "ERROR");
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }
}

