package com.example.cis183_finalproject;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewColorActivity extends AppCompatActivity {
    Intent searchActivity;
    Intent editPaletteActivity;
    Intent homeActivity;
    Intent cameFrom;
    Intent activityToStart;
    Intent manageColorsActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;
    TextView tv_j_name;
    TextView tv_j_hex;
    View view_j_color;
    Button btn_j_paletteSave;
    Button btn_j_newPaletteSave;
    Spinner spn_j_paletteSave;
    PaletteListAdapter pLAdapter;
    List<Palette> paletteList;
    boolean saved;


    ColorData selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_color);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saved = false;

        searchActivity = new Intent(ViewColorActivity.this, SearchActivity.class);
        editPaletteActivity = new Intent(ViewColorActivity.this, EditPaletteActivity.class);
        homeActivity = new Intent(ViewColorActivity.this, HomeActivity.class);
        manageColorsActivity = new Intent(ViewColorActivity.this, ManageColorsActivity.class);
        dbHelper = new DatabaseHelper(this);

        cameFrom = getIntent();
        String startedMe = (String) cameFrom.getSerializableExtra("startedMe");
        if(startedMe != null && startedMe.equals("search")) {
            activityToStart = searchActivity;
        }
        else if (startedMe != null && startedMe.equals("editPalette")){
            activityToStart = editPaletteActivity;
            if (cameFrom.getSerializableExtra("selectedPalette") != null) {
                activityToStart.putExtra("selectedPalette", cameFrom.getSerializableExtra("selectedPalette"));
            }
        }
        else if (startedMe != null && startedMe.equals("manageColors")) {
            activityToStart = manageColorsActivity;
        }
        else {
            activityToStart = homeActivity;
        }

        if (cameFrom.getSerializableExtra("selectedColor") != null) {
            selectedColor = (ColorData) cameFrom.getSerializableExtra("selectedColor");
        }
        else {
            startActivity(searchActivity);
            finish();
        }

        btn_j_back = findViewById(R.id.btn_v_viewColor_back);
        tv_j_name = findViewById(R.id.tv_v_viewColor_name);
        tv_j_hex = findViewById(R.id.tv_v_viewColor_hex);
        view_j_color = findViewById(R.id.view_v_viewColor_color);
        btn_j_paletteSave = findViewById(R.id.btn_v_viewColor_paletteSave);
        btn_j_newPaletteSave = findViewById(R.id.btn_v_viewColor_newPaletteSave);

        spn_j_paletteSave = findViewById(R.id.spn_v_viewColor_paletteSave);
        paletteList = dbHelper.getPalettesByUser(SessionData.getCurrentUser());
        pLAdapter = new PaletteListAdapter(this, paletteList);
        spn_j_paletteSave.setAdapter(pLAdapter);

        setColor();
        listeners();
    }

    private void setColor() {
        String hex = selectedColor.getHex();
        int r = ColorData.getRedFromHex(hex);
        int g = ColorData.getGreenFromHex(hex);
        int b = ColorData.getBlueFromHex(hex);

        int color = Color.rgb(r, g, b);
        view_j_color.setBackgroundColor(color);
        tv_j_hex.setText("#" + hex);
        tv_j_name.setText(selectedColor.getName());
    }

    private void listeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activityToStart);
                finish();
            }
        });
        btn_j_paletteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColor(false);
            }
        });
        btn_j_newPaletteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColor(true);
            }
        });
    }

    private void saveColor(boolean newPalette) {
        if (newPalette) {
            Palette palette = new Palette();
            List<ColorData> list = new ArrayList<>();
            list.add(selectedColor);
            palette.setColorList(list);
            palette.setAuthor(SessionData.getCurrentUser());

            dbHelper.addPaletteToDatabase(palette);
            dbHelper.addPaletteToUser(SessionData.getCurrentUser(), palette);
            paletteList.add(palette);
            pLAdapter.notifyDataSetChanged();
            spn_j_paletteSave.setSelection(paletteList.size() - 1);
        }
        else {
            dbHelper.addColorToPalette((Palette)spn_j_paletteSave.getSelectedItem(), selectedColor);
        }
    }
}