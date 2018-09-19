package com.Technivision.sweden.navigationdrawer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by Ma7MouD on 1/31/2018.
 */

public class ThemesAdapter  extends RecyclerView.Adapter<ThemesAdapter.ViewHolder>{

    private Context context ;
    private ArrayList<ThemesModel> list ;

    public ThemesAdapter(Context context, ArrayList<ThemesModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ThemesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThemesAdapter.ViewHolder holder, int position) {

        Picasso.with(context).load(list.get(position).getImage()).into(holder.imageView);
        holder.textView.setText(list.get(position).getTitle());
        holder.cardView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView ;
        ImageView imageView ;
        CardView cardView ;
        public ViewHolder(View itemView) {
            super(itemView);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "font/font.ttf");

            imageView = (ImageView) itemView.findViewById(R.id.recycler_img);
            textView = (TextView) itemView.findViewById(R.id.img_title);
            textView.setTypeface(custom_font);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();
            Picasso.with(context).load(list.get(position).getImage()).into(new Target(){

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    MainActivity.drawer.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                }

                @Override
                public void onBitmapFailed(final Drawable errorDrawable) {
                    Log.d("TAG", "FAILED");
                }

                @Override
                public void onPrepareLoad(final Drawable placeHolderDrawable) {
                    Log.d("TAG", "Prepare Load");
                }
            });

            ((Activity)context).finish();
        }
    }
}
