package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class EditPaletteActivity extends AppCompatActivity {

    Intent managePalettesActivity;
    Intent cameFrom;
    DatabaseHelper dbHelper;
    Palette selectedPalette;

    Button btn_j_back;
    TextView tv_j_title;
    ListView lv_j_colors;
    ColorListAdapter cLAdapter;
    List<ColorData> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_palette);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cameFrom = getIntent();
        if(cameFrom.getSerializableExtra("selectedPalette") != null) {
            selectedPalette = (Palette) cameFrom.getSerializableExtra("selectedPalette");
        }
        else {
            startActivity(managePalettesActivity);
            finish();
        }

        managePalettesActivity = new Intent(EditPaletteActivity.this, ManagePalettesActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_editPalette_back);
        tv_j_title = findViewById(R.id.tv_v_editPalette_title);
        lv_j_colors = findViewById(R.id.lv_v_editPalette_colors);

        colorList = selectedPalette.getColorList();
        cLAdapter = new ColorListAdapter(this, colorList);
        lv_j_colors.setAdapter(cLAdapter);

        listeners();
    }

    private void listeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(managePalettesActivity);
                finish();
            }
        });
    }
}