package com.example.cis183_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    Context context;
    List<User> userList;

    public UserListAdapter (Context c, List<User> uL) {
        context = c;
        userList = uL;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(MainActivity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.user_cell, null);
        }

        TextView tv_j_username = convertView.findViewById(R.id.tv_v_userCell_username);

        User user = userList.get(position);
        tv_j_username.setText(user.getUsername());

        return convertView;
    }
}
