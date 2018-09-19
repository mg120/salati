package com.Technivision.sweden.navigationdrawer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.Technivision.sweden.navigationdrawer.Alarm.AlarmReceiver;
import com.Technivision.sweden.navigationdrawer.CalendarFragments.MonthCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ArrayList<Integer> left_times_arr = new ArrayList<>();
    private ArrayList<DataModel> datalist;
    private AlarmManager manager;
    private PendingIntent pendingIntent;
    public static DrawerLayout drawer;
    private SlideAdapter adapter;
    String salah_txt;
    ViewPager viewPager;
    String monthh, dayy;
    TextView next_salah, time_counter, sim_day_txt;
    String json_url;
    int current_time, seslected_id ;

    MediaPlayer mediaPlayer ;
    String selected_url ;

    private ImageView qibla_img, calendar_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/font.ttf");

//
        // getting current date and day name ..
        Calendar calendar = Calendar.getInstance();
        int cur_minute = calendar.get(Calendar.MINUTE);
        int cur_hours = calendar.get(Calendar.HOUR_OF_DAY);
        current_time = cur_hours * 60 + cur_minute;

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int monthday = calendar.get(Calendar.DAY_OF_MONTH);
        int monthnum = calendar.get(Calendar.MONTH) + 1;

        if (monthday < 10) {
            dayy = "0" + monthday;
        } else {
            dayy = String.valueOf(monthday);
        }

        monthh = String.valueOf(monthnum);

        json_url = "http://e-marknad.com/app/subject/channels/getDay?month=" + monthh + "&day=" + dayy;
        datalist = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        seslected_id = Azan.seslected_id;
//                        selected_url = Azan.seslected_url;
//                    }
//                });
//            }
//        }, 0, 2000);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackgroundResource(R.drawable.bg1);

        ImageView menuRight = (ImageView) findViewById(R.id.navi_image);
        TextView date_txtV = (TextView) findViewById(R.id.date_txt);
        TextView town_txt = (TextView) findViewById(R.id.town_txt);
        next_salah = (TextView) findViewById(R.id.salah_in);
        next_salah.setTypeface(custom_font);
        time_counter = (TextView) findViewById(R.id.time_left);
        time_counter.setTypeface(custom_font);
        sim_day_txt = (TextView) findViewById(R.id.day_txt);
        sim_day_txt.setTypeface(custom_font);
        qibla_img = (ImageView) findViewById(R.id.islam);

        // calendar_img = (ImageView) findViewById(R.id.calend_day);

        // Monthly calender ....

        findViewById(R.id.calend_day).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MonthCalendar.class));
            }
        });

        findViewById(R.id.islam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Qibla.class));
            }
        });

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        DateFormat sim_dateformat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        sim_day_txt.setText(sim_dateformat.format(date));

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                date_txtV.setText("SUNDAY - " + dateFormat.format(date));
                break;
            case Calendar.MONDAY:
                // Current day is Monday
                date_txtV.setText("MONDAY - " + dateFormat.format(date));
                break;

            case Calendar.TUESDAY:
                date_txtV.setText("TUESDAY - " + dateFormat.format(date));
                break;

            case Calendar.WEDNESDAY:
                date_txtV.setText("WEDNESDAY - " + dateFormat.format(date));
                break;

            case Calendar.THURSDAY:
                date_txtV.setText("THURSDAY - " + dateFormat.format(date));
                break;

            case Calendar.FRIDAY:
                date_txtV.setText("FRIDAY - " + dateFormat.format(date));
                break;

            case Calendar.SATURDAY:
                date_txtV.setText("SATURDAY - " + dateFormat.format(date));
                break;

        }


        getData();


        //inflate drawer ..
        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });


        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        navigationView2.setNavigationItemSelectedListener(this);

    }

    public void getData() {
        // getting data ...

        StringRequest request = new StringRequest(Request.Method.GET, json_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    jsondata(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Internet")
                        .setMessage("Error Connection")
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

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    private void jsondata(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        boolean status = jsonObject.getBoolean("Status");

        if (status) {

            JSONArray jsonArray = jsonObject.getJSONArray("Data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject data_obj = jsonArray.getJSONObject(i);
                JSONObject meqat_data = data_obj.getJSONObject("meqat");
                String day_num = meqat_data.getString("day");
                String fajr_time = meqat_data.getString("Fajr");
                String zuhr_time = meqat_data.getString("Zuhr");
                String aser_time = meqat_data.getString("Asr");
                String maghrib_time = meqat_data.getString("Maghrib");
                String isha_time = meqat_data.getString("Isha");

                datalist.add(new DataModel(day_num, fajr_time, zuhr_time, aser_time, maghrib_time, isha_time));
            }

        }
        adapter = new SlideAdapter(MainActivity.this, datalist);
        viewPager.setAdapter(adapter);


        int x = 0;
        String[] day_string_times = {datalist.get(0).getFajr_time(), datalist.get(0).getZuhr_time(),
                datalist.get(0).getAser_time(), datalist.get(0).getMaghrib_time(), datalist.get(0).getIsha_time()};

        for (int i = 0; i < day_string_times.length; i++) {

            String data = day_string_times[i];
            String[] items = data.split(":");
            int salah_hour = Integer.parseInt(items[0]);
            int salah_minute = Integer.parseInt(items[1]);

            int time_by_min = salah_hour * 60 + salah_minute;

            x = time_by_min - current_time;

            if (x > 0) {
                left_times_arr.add(x);
            } else {
                x = 500000;
                left_times_arr.add(x);
            }

            //countdownstart(min_num);
        }
        int min = left_times_arr.get(0);
        for (int i : left_times_arr) {
            min = min < i ? min : i;
        }

        int index = left_times_arr.indexOf(min);
        if (min == 500000) {
            String fagr = day_string_times[0];
            String[] items = fagr.split(":");
            int salah_hour = Integer.parseInt(items[0]);
            int salah_minute = Integer.parseInt(items[1]);

            int fagr_min = salah_hour * 60 + salah_minute;

            int difference = 24 * 60 - current_time;

            int zz = difference + fagr_min;

            countdownstart(zz * 60 * 1000);
            //fireAlarm(min , index);

        } else {
            countdownstart(min * 60 * 1000);
            // fireAlarm(min , index);
        }
        if (index == 0) {
            salah_txt = "FAJR IN ";
            next_salah.setText("FAJR IN ");
        }
        if (index == 1) {
            salah_txt = "ZUHR IN";
            next_salah.setText("ZUHR IN");
        } else if (index == 2) {
            salah_txt = "ASER IN";
            next_salah.setText("ASER IN");
        } else if (index == 3) {
            salah_txt = "MAGHRIB IN";
            next_salah.setText("MAGHRIB IN");
        } else if (index == 4) {
            salah_txt = "ISHA IN";
            next_salah.setText("ISHA IN");
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.themes) {

            Intent intent = new Intent(MainActivity.this, Themes.class);
            startActivity(intent);

        } else if (id == R.id.reklam) {

            startActivity(new Intent(MainActivity.this, Reklam.class));

        } else if (id == R.id.mosques) {

            startActivity(new Intent(MainActivity.this, Mosques.class));

        } else if (id == R.id.location) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("LOCATION")
                    .setMessage("Kronoberg län - Sweden")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.azan) {

            startActivity(new Intent(MainActivity.this, Azan.class));
        } else if (id == R.id.rate_app) {

//            String App_link = "";
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse(App_link));
//            startActivity(intent);

        } else if (id == R.id.share_app) {

            int applicationNameId = this.getApplicationInfo().labelRes;
            final String appPackageName = this.getPackageName();
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(applicationNameId));
            String text = "Install this cool application: ";
            String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
            i.putExtra(Intent.EXTRA_TEXT, text + " " + link);
            startActivity(Intent.createChooser(i, "Share link:"));

        } else if (id == R.id.about_app) {

            startActivity(new Intent(MainActivity.this, AboutActivity.class));

        } else if (id == R.id.contact_us) {

            startActivity(new Intent(MainActivity.this, ContactActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    public void countdownstart(long time) {

        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int hours = seconds / (60 * 60);
                int tempMint = (seconds - (hours * 60 * 60));
                int minutes = tempMint / 60;
                seconds = tempMint - (minutes * 60);

                time_counter.setText(String.format("%02d", hours)
                        + ":" + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));

            }

            public void onFinish() {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                        .setTicker("My notification")
                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                        .setContentTitle("Current " + salah_txt)
                        .setContentText(salah_txt + " is running now ")
                        .setAutoCancel(true);

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0, mBuilder.build());

                // media player ....

                if (Azan.seslected_id == 1){

                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource("http://e-marknad.com/app/Public/uploads/file/naser-alkatamy.mp3");
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
//
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(Azan.seslected_url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }

    public void fireAlarm(int value, int index) {

        final Intent receiver_intent = new Intent(MainActivity.this, AlarmReceiver.class);
        receiver_intent.putExtra("index", index);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, receiver_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(value), pendingIntent);

    }

}
