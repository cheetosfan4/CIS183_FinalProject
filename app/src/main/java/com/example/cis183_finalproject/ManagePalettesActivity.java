package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ManagePalettesActivity extends AppCompatActivity {

    Intent homeActivity;
    Intent editPaletteActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;
    ListView lv_j_palettes;
    PaletteListAdapter pLAdapter;
    List<Palette> paletteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_palettes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeActivity = new Intent(ManagePalettesActivity.this, HomeActivity.class);
        editPaletteActivity = new Intent(ManagePalettesActivity.this, EditPaletteActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_managePalettes_back);
        lv_j_palettes = findViewById(R.id.lv_v_managePalettes_palettes);

        dbHelper.loadPalettesToUser(SessionData.getCurrentUser());
        paletteList = SessionData.getCurrentUser().getPaletteList();
        pLAdapter = new PaletteListAdapter(this, paletteList);
        lv_j_palettes.setAdapter(pLAdapter);

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
        lv_j_palettes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPaletteActivity.putExtra("selectedPalette", paletteList.get(position));
                editPaletteActivity.putExtra("startedMe", "managePalettes");
                startActivity(editPaletteActivity);
                finish();
            }
        });
        lv_j_palettes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Palette clickedPalette = paletteList.get(position);
                if (clickedPalette.getAuthor().getUsername().equals(SessionData.getCurrentUser().getUsername())) {
                    dbHelper.deletePaletteFromDatabase(clickedPalette);
                    paletteList.remove(position);
                }
                else {
                    dbHelper.removePaletteFromUser(SessionData.getCurrentUser(), clickedPalette);
                }

                pLAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}