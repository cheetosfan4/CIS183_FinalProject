package com.example.cis183_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PaletteListAdapter extends BaseAdapter {
    Context context;
    List<Palette> paletteList;

    public PaletteListAdapter(Context c, List<Palette> pL) {
        context = c;
        paletteList = pL;
    }

    @Override
    public int getCount() {
        return paletteList.size();
    }

    @Override
    public Object getItem(int position) {
        return paletteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.palette_cell, null);
        }

        TextView tv_j_paletteID = convertView.findViewById(R.id.tv_v_paletteCell_ID);
        TextView tv_j_author = convertView.findViewById(R.id.tv_v_paletteCell_author);

        Palette palette = paletteList.get(position);
        tv_j_paletteID.setText(Integer.toString(palette.getPaletteID()));
        tv_j_author.setText(palette.getAuthor().getUsername());

        return convertView;
    }
}
