package com.example.cis183_finalproject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    Intent homeActivity;
    Intent editPaletteActivity;
    Intent createColorActivity;
    Intent viewColorActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;
    Button btn_j_search;
    Spinner spn_j_searchFor;
    String[] searchFor;
    ArrayAdapter<String> spinnerAdapter;

    ConstraintLayout cl_j_users;
    EditText et_j_users_username;
    EditText et_j_users_favColor;

    ConstraintLayout cl_j_colors;
    EditText et_j_colors_name;
    EditText et_j_colors_hex;

    ConstraintLayout cl_j_palettes;
    EditText et_j_palettes_ID;
    //EditText et_j_palettes_colorName;
    EditText et_j_palettes_colorHex;

    ListView lv_j_results;
    UserListAdapter uLAdapter;
    List<User> userList;
    ColorListAdapter cLAdapter;
    List<ColorData> colorList;
    PaletteListAdapter pLAdapter;
    List<Palette> paletteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeActivity = new Intent(SearchActivity.this, HomeActivity.class);
        editPaletteActivity = new Intent(SearchActivity.this, EditPaletteActivity.class);
        createColorActivity = new Intent(SearchActivity.this, CreateColorActivity.class);
        viewColorActivity = new Intent(SearchActivity.this, ViewColorActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_search_back);
        btn_j_search = findViewById(R.id.btn_v_search_search);

        spn_j_searchFor = findViewById(R.id.spn_v_search_searchFor);
        searchFor = new String[]{"Users", "Colors", "Palettes"};
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, searchFor);
        spn_j_searchFor.setAdapter(spinnerAdapter);


        cl_j_users = findViewById(R.id.cl_v_search_filters_users);
        et_j_users_username = findViewById(R.id.et_v_search_filters_users_username);
        et_j_users_favColor = findViewById(R.id.et_v_search_filters_user_favColor);

        cl_j_colors = findViewById(R.id.cl_v_search_filters_colors);
        et_j_colors_name = findViewById(R.id.et_v_search_filters_colors_name);
        et_j_colors_hex = findViewById(R.id.et_v_search_filters_colors_hex);

        cl_j_palettes = findViewById(R.id.cl_v_search_filters_palettes);
        et_j_palettes_ID = findViewById(R.id.et_v_search_filters_palettes_ID);
        //et_j_palettes_colorName = findViewById(R.id.et_v_search_filters_palettes_colorName);
        et_j_palettes_colorHex = findViewById(R.id.et_v_search_filters_palettes_colorHex);

        lv_j_results = findViewById(R.id.lv_v_search_results);
        userList = dbHelper.findUsers();
        colorList = dbHelper.findColors();
        paletteList = dbHelper.findPalettes();
        uLAdapter = new UserListAdapter(this, userList);
        cLAdapter = new ColorListAdapter(this, colorList);
        pLAdapter = new PaletteListAdapter(this, paletteList);
        lv_j_results.setAdapter(uLAdapter);

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
        spn_j_searchFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        cl_j_users.setVisibility(VISIBLE);
                        cl_j_colors.setVisibility(GONE);
                        cl_j_palettes.setVisibility(GONE);

                        lv_j_results.setAdapter(uLAdapter);
                        userList.clear();
                        userList.addAll(dbHelper.findUsers());
                        uLAdapter.notifyDataSetChanged();
                        break;
                    }
                    case 1: {
                        cl_j_colors.setVisibility(VISIBLE);
                        cl_j_users.setVisibility(GONE);
                        cl_j_palettes.setVisibility(GONE);

                        lv_j_results.setAdapter(cLAdapter);
                        colorList.clear();
                        colorList.addAll(dbHelper.findColors());
                        cLAdapter.notifyDataSetChanged();
                        break;
                    }
                    case 2: {
                        cl_j_palettes.setVisibility(VISIBLE);
                        cl_j_users.setVisibility(GONE);
                        cl_j_colors.setVisibility(GONE);

                        lv_j_results.setAdapter(pLAdapter);
                        paletteList.clear();
                        paletteList.addAll(dbHelper.findPalettes());
                        pLAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                et_j_users_username.setText("");
                et_j_users_favColor.setText("");
                et_j_colors_name.setText("");
                et_j_colors_hex.setText("");
                et_j_palettes_ID.setText("");
                //et_j_palettes_colorName.setText("");
                et_j_palettes_colorHex.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cl_j_users.setVisibility(VISIBLE);
                cl_j_colors.setVisibility(GONE);
                cl_j_palettes.setVisibility(GONE);

                lv_j_results.setAdapter(uLAdapter);
                userList.clear();
                userList.addAll(dbHelper.findUsers());
                uLAdapter.notifyDataSetChanged();

                et_j_users_username.setText("");
                et_j_users_favColor.setText("");
                et_j_colors_name.setText("");
                et_j_colors_hex.setText("");
                et_j_palettes_ID.setText("");
                //et_j_palettes_colorName.setText("");
                et_j_palettes_colorHex.setText("");
            }
        });
        lv_j_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (lv_j_results.getAdapter() == pLAdapter) {
                    editPaletteActivity.putExtra("selectedPalette", paletteList.get(position));
                    editPaletteActivity.putExtra("startedMe", "search");
                    startActivity(editPaletteActivity);
                    finish();
                }
                else if (lv_j_results.getAdapter() == cLAdapter) {
                    /*if (colorList.get(position).getAuthor().getUsername().equals(SessionData.getCurrentUser().getUsername())) {
                        createColorActivity.putExtra("selectedColor", colorList.get(position));
                        createColorActivity.putExtra("startedMe", "search");
                        startActivity(createColorActivity);
                        finish();
                    }
                    else {
                        viewColorActivity.putExtra("selectedColor", colorList.get(position));
                        viewColorActivity.putExtra("startedMe", "search");
                        startActivity(viewColorActivity);
                        finish();
                    }*/
                    viewColorActivity.putExtra("selectedColor", colorList.get(position));
                    viewColorActivity.putExtra("startedMe", "search");
                    startActivity(viewColorActivity);
                    finish();
                }
            }
        });
        btn_j_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lv_j_results.getAdapter() == uLAdapter) {
                    String usernameFilter = et_j_users_username.getText().toString();
                    String favColorFilter = et_j_users_favColor.getText().toString();

                    userList.clear();
                    userList.addAll(dbHelper.filterUsers(usernameFilter, favColorFilter));
                    uLAdapter.notifyDataSetChanged();
                }
                else if (lv_j_results.getAdapter() == cLAdapter) {
                    String hexFilter = et_j_colors_hex.getText().toString();
                    String nameFilter = et_j_colors_name.getText().toString();

                    colorList.clear();
                    colorList.addAll(dbHelper.filterColors(hexFilter, nameFilter));
                    cLAdapter.notifyDataSetChanged();
                }
                else if (lv_j_results.getAdapter() == pLAdapter) {
                    int idFilter = 999;
                    String idFilterString = et_j_palettes_ID.getText().toString();
                    String colorHexFilter = et_j_palettes_colorHex.getText().toString();

                    if (!idFilterString.isEmpty()) {
                        idFilter = Integer.parseInt(idFilterString);
                    }
                    paletteList.clear();
                    paletteList.addAll(dbHelper.filterPalettes(idFilter, colorHexFilter));
                    pLAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}