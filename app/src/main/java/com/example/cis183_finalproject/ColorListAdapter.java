package com.example.cis183_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ColorListAdapter extends BaseAdapter {
    Context context;
    List<ColorData> colorList;

    public ColorListAdapter(Context c, List<ColorData> cL) {
        context = c;
        colorList = cL;
    }

    @Override
    public int getCount() {
        return colorList.size();
    }

    @Override
    public Object getItem(int position) {
        return colorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.color_cell, null);
        }

        TextView tv_j_hex = convertView.findViewById(R.id.tv_v_colorCell_hex);
        TextView tv_j_name = convertView.findViewById(R.id.tv_v_colorCell_name);
        TextView tv_j_author = convertView.findViewById(R.id.tv_v_colorCell_author);

        ColorData color = colorList.get(position);
        tv_j_hex.setText("#" + color.getHex());
        tv_j_name.setText(color.getName());
        tv_j_author.setText(color.getAuthor().getUsername());

        return convertView;
    }
}
