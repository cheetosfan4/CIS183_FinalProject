package com.example.cis183_finalproject;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    Intent searchActivity;
    Intent cameFrom;
    Intent activityToStart;
    DatabaseHelper dbHelper;
    Palette selectedPalette;

    Button btn_j_back;
    TextView tv_j_title;
    ListView lv_j_colors;
    ColorListAdapter cLAdapter;
    List<ColorData> colorList;

    TextView tv_j_info;
    Button btn_j_save;

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
        searchActivity = new Intent(EditPaletteActivity.this, SearchActivity.class);

        String startedMe = (String) cameFrom.getSerializableExtra("startedMe");

        if(startedMe != null && startedMe.equals("search")) {
            activityToStart = searchActivity;
        }
        else {
            activityToStart = managePalettesActivity;
        }

        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_editPalette_back);
        tv_j_title = findViewById(R.id.tv_v_editPalette_title);
        lv_j_colors = findViewById(R.id.lv_v_editPalette_colors);

        colorList = selectedPalette.getColorList();
        cLAdapter = new ColorListAdapter(this, colorList);
        lv_j_colors.setAdapter(cLAdapter);

        tv_j_info = findViewById(R.id.tv_v_editPalette_info);
        btn_j_save = findViewById(R.id.btn_v_editPalette_save);

        dbHelper.loadPalettesToUser(SessionData.getCurrentUser());
        tv_j_title.setText("Palette" + selectedPalette.getPaletteID());
        listeners();
        checkOwnership();
    }

    private void listeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(activityToStart);
                finish();
            }
        });
        lv_j_colors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //edit color
            }
        });
        lv_j_colors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPalette.getAuthor().getUsername().equals(SessionData.getCurrentUser().getUsername())) {
                    dbHelper.removeColorFromPalette(selectedPalette, colorList.get(position));
                    //doesn't have to remove the color from colorList since it is a direct reference to the palette's list
                    //colorList.remove(position);
                    cLAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        btn_j_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addPaletteToUser(SessionData.getCurrentUser(), selectedPalette);
                btn_j_save.setVisibility(GONE);
            }
        });
    }

    private void checkOwnership() {
        String paletteAuthor = selectedPalette.getAuthor().getUsername();
        User currentUser = SessionData.getCurrentUser();
        String currentUsername = currentUser.getUsername();
        List<Palette> currentPaletteList = currentUser.getPaletteList();

        if (!paletteAuthor.equals(currentUsername)) {
            tv_j_info.setVisibility(INVISIBLE);
            btn_j_save.setVisibility(VISIBLE);
            for (int i = 0; i < currentPaletteList.size(); i++) {
                if ((currentPaletteList.get(i).getPaletteID() == selectedPalette.getPaletteID())) {
                    btn_j_save.setVisibility(GONE);
                    break;
                }
            }
        }
    }
}