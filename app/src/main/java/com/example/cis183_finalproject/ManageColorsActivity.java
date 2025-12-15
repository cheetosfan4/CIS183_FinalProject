package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ManageColorsActivity extends AppCompatActivity {
    Intent homeActivity;
    Intent createColorActivity;
    Intent viewColorActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;
    ListView lv_j_colors;
    ColorListAdapter cLAdapter;
    List<ColorData> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_colors);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeActivity = new Intent(ManageColorsActivity.this, HomeActivity.class);
        createColorActivity = new Intent(ManageColorsActivity.this, CreateColorActivity.class);
        viewColorActivity = new Intent(ManageColorsActivity.this, ViewColorActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_manageColors_back);
        lv_j_colors = findViewById(R.id.lv_v_manageColors_colors);

        colorList = dbHelper.getColorsByUser(SessionData.getCurrentUser());
        cLAdapter = new ColorListAdapter(this, colorList);
        lv_j_colors.setAdapter(cLAdapter);

        listeners();
    }

    private void listeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeActivity);
                finish();
            }
        });
        lv_j_colors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewColorActivity.putExtra("selectedColor", colorList.get(position));
                viewColorActivity.putExtra("startedMe", "manageColors");
                startActivity(viewColorActivity);
                finish();
            }
        });
        lv_j_colors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dbHelper.deleteColorFromDatabase(colorList.get(position));
                colorList.remove(position);
                cLAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}