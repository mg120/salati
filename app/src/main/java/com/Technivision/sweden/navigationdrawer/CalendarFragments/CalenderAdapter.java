package com.Technivision.sweden.navigationdrawer.CalendarFragments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Technivision.sweden.navigationdrawer.R;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 2/6/2018.
 */

public class CalenderAdapter extends BaseAdapter {


    Context context ;
    ArrayList<CalenderModel> calender_list ;

    public CalenderAdapter(Context context, ArrayList<CalenderModel> calender_list) {
        this.context = context;
        this.calender_list = calender_list;
    }

    @Override
    public int getCount() {
        return calender_list.size();
    }

    @Override
    public Object getItem(int i) {
        return calender_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.calender_row , viewGroup , false);

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font/font.ttf");

        TextView  hgry_txt = (TextView) view.findViewById(R.id.hgry_day);
        hgry_txt.setTypeface(custom_font);
        TextView  melady_txt = (TextView) view.findViewById(R.id.melady_day);
        melady_txt.setTypeface(custom_font);
        TextView  fajr_cal = (TextView) view.findViewById(R.id.fajr_cal);
        fajr_cal.setTypeface(custom_font);
        TextView  zuhr_cal = (TextView) view.findViewById(R.id.zuhr_cal);
        zuhr_cal.setTypeface(custom_font);
        TextView  aser_cal = (TextView) view.findViewById(R.id.aser_cal);
        aser_cal.setTypeface(custom_font);
        TextView  maghrib_cal = (TextView) view.findViewById(R.id.maghrib_cal);
        maghrib_cal.setTypeface(custom_font);
        TextView  isha_cal = (TextView) view.findViewById(R.id.isha_cal);
        isha_cal.setTypeface(custom_font);


        hgry_txt.setText(calender_list.get(i).getMeladay_day());
        melady_txt.setText(calender_list.get(i).getDay_name());
        fajr_cal.setText(calender_list.get(i).getFajr_cal());
        zuhr_cal.setText(calender_list.get(i).getZuhr_cal());
        aser_cal.setText(calender_list.get(i).getAser_cal());
        maghrib_cal.setText(calender_list.get(i).getMaghrib_cal());
        isha_cal.setText(calender_list.get(i).getIsha_cal());

        return view;
    }
}
