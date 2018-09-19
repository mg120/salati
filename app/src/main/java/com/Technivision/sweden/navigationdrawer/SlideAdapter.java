package com.Technivision.sweden.navigationdrawer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ma7MouD on 1/18/2018.
 */

public class SlideAdapter extends PagerAdapter {


    private Context context;
    List<DataModel> list ;
    private LayoutInflater inflater;
    private TextView fajr_time, zuhr_time, aser_time, maghrib_time, isha_time;

    public SlideAdapter(Context context, List<DataModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font/font.ttf");

        fajr_time = (TextView) view.findViewById(R.id.elfagr_time);
        fajr_time.setTypeface(custom_font);
        zuhr_time = (TextView) view.findViewById(R.id.duhur_time);
        zuhr_time.setTypeface(custom_font);
        aser_time = (TextView) view.findViewById(R.id.aser_time);
        aser_time.setTypeface(custom_font);
        maghrib_time = (TextView) view.findViewById(R.id.maghrib_time);
        maghrib_time.setTypeface(custom_font);
        isha_time = (TextView) view.findViewById(R.id.isha_time);
        isha_time.setTypeface(custom_font);

        // put data to texts ...
        DataModel dataModel = list.get(position);
        fajr_time.setText(dataModel.getFajr_time());
        zuhr_time.setText(dataModel.getZuhr_time());
        aser_time.setText(dataModel.getAser_time());
        maghrib_time.setText(dataModel.getMaghrib_time());
        isha_time.setText(dataModel.getIsha_time());

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
