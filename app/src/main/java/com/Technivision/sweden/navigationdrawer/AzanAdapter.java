package com.Technivision.sweden.navigationdrawer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ma7MouD on 2/9/2018.
 */

public class AzanAdapter extends BaseAdapter {

    Context context ;
    ArrayList<AzanModel> list ;
    MediaPlayer mediaPlayer;

    public AzanAdapter(Context context, ArrayList<AzanModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

        view = LayoutInflater.from(context).inflate(R.layout.list_row_item, parent, false);

        // item views ...
        TextView name = view.findViewById(R.id.name);
        final ImageView play_img = view.findViewById(R.id.playsound_img);
        final ImageView stop_image = view.findViewById(R.id.stop_sound_img);

        // put data to views ..
        name.setText(list.get(i).getName());

        play_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(list.get(i).getSound_url());
                } catch (IllegalArgumentException e) {
                    //Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                   // Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                   // Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IllegalStateException e) {
                   // Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    //Toast.makeText(context, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                }
                mediaPlayer.start();
                play_img.setEnabled(false);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        play_img.setEnabled(true);
                        mediaPlayer.release();
                    }
                });
            }
        });

        stop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    play_img.setEnabled(true);
                }
            }
        });

        return view;
    }
}
