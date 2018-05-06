package com.example.ahmed.refreshlistview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ahmed on 4/4/18.
 */

public class ListAdapter extends BaseAdapter {
    List<Movie> movieList;
    Context context;
    String[] bgColor;
    LayoutInflater inflater;

    public ListAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
        bgColor = context.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, parent, false);
        }
        TextView serial = convertView.findViewById(R.id.serial);
        TextView title = convertView.findViewById(R.id.title);

        serial.setText(String.valueOf(movieList.get(position).id));
        title.setText(movieList.get(position).title);

        String color = bgColor[position % bgColor.length];
        serial.setBackgroundColor(Color.parseColor(color));
        return convertView;
    }
}
