package com.example.cis183_finalproject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SimplePaletteListAdapter extends BaseAdapter {
    Context context;
    List<Palette> paletteList;

    public SimplePaletteListAdapter (Context c, List<Palette> pL) {
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
            convertView = mInflater.inflate(R.layout.simple_palette_cell, null);
        }

        TextView tv_j_paletteID = convertView.findViewById(R.id.tv_v_simplePaletteCell_ID);
        View view_j_display1 = convertView.findViewById(R.id.view_v_simplePaletteCell_display1);
        View view_j_display2 = convertView.findViewById(R.id.view_v_simplePaletteCell_display2);
        View view_j_display3 = convertView.findViewById(R.id.view_v_simplePaletteCell_display3);
        View view_j_display4 = convertView.findViewById(R.id.view_v_simplePaletteCell_display4);
        TextView tv_j_ellipses = convertView.findViewById(R.id.tv_v_simplePaletteCell_ellipses);

        Palette palette = paletteList.get(position);
        tv_j_paletteID.setText(Integer.toString(palette.getPaletteID()));
        tv_j_ellipses.setVisibility(GONE);

        int displaysToFill = 0;
        if (palette.getColorList().size() > 4) {
            displaysToFill = 4;
            tv_j_ellipses.setVisibility(VISIBLE);
        }
        else {
            displaysToFill = palette.getColorList().size();
        }

        switch (displaysToFill) {
            case 4: {
                ColorData color = palette.getColorList().get(3);
                view_j_display4.setBackgroundColor(ColorData.colorToInt(color));
            }
            case 3: {
                ColorData color = palette.getColorList().get(2);
                view_j_display3.setBackgroundColor(ColorData.colorToInt(color));
            }
            case 2: {
                ColorData color = palette.getColorList().get(1);
                view_j_display2.setBackgroundColor(ColorData.colorToInt(color));
            }
            case 1: {
                ColorData color = palette.getColorList().get(0);
                view_j_display1.setBackgroundColor(ColorData.colorToInt(color));
            }
        }

        return convertView;
    }
}
