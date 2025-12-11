package com.example.cis183_finalproject;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CreateColorActivity extends AppCompatActivity {

    Intent homeActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;

    TextView tv_j_mode;
    Spinner spn_j_mode;

    //====== RGB mode ==============================================================================
    ConstraintLayout cons_j_mode_RGB;
    TextView tv_j_RGB_redValue;
    SeekBar sb_j_RGB_red;
    TextView tv_j_RGB_greenValue;
    SeekBar sb_j_RGB_green;
    TextView tv_j_RGB_blueValue;
    SeekBar sb_j_RGB_blue;
    //==============================================================================================

    TextView tv_j_name;
    EditText et_j_name;

    View view_j_preview;
    TextView tv_j_hex;

    TextView tv_j_error;
    TextView tv_j_paletteSave;
    Button btn_j_paletteSave;
    Button btn_j_newPaletteSave;
    Spinner spn_j_paletteSave;
    PaletteListAdapter pLAdapter;
    List<Palette> paletteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_color);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeActivity = new Intent(CreateColorActivity.this, HomeActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_createColor_back);

        tv_j_mode = findViewById(R.id.tv_v_createColor_mode);
        spn_j_mode = findViewById(R.id.spn_v_createColor_mode);

        //====== RGB mode ==========================================================================
        cons_j_mode_RGB = findViewById(R.id.cons_v_createColor_mode_RGB);
        tv_j_RGB_redValue = findViewById(R.id.tv_v_createColor_RGB_redValue);
        sb_j_RGB_red = findViewById(R.id.sb_v_createColor_RGB_red);
        tv_j_RGB_greenValue = findViewById(R.id.tv_v_createColor_RGB_greenValue);
        sb_j_RGB_green = findViewById(R.id.sb_v_createColor_RGB_green);
        tv_j_RGB_blueValue = findViewById(R.id.tv_v_createColor_RGB_blueValue);
        sb_j_RGB_blue = findViewById(R.id.sb_v_createColor_RGB_blue);
        //==========================================================================================

        tv_j_name = findViewById(R.id.tv_v_createColor_name);
        et_j_name = findViewById(R.id.et_v_createColor_name);

        view_j_preview = findViewById(R.id.view_v_createColor_preview);
        tv_j_hex = findViewById(R.id.tv_v_createColor_hex);

        tv_j_error = findViewById(R.id.tv_v_createColor_error);
        tv_j_paletteSave = findViewById(R.id.tv_v_createColor_paletteSave);
        btn_j_paletteSave = findViewById(R.id.btn_v_createColor_paletteSave);
        btn_j_newPaletteSave = findViewById(R.id.btn_v_createColor_newPaletteSave);
        spn_j_paletteSave = findViewById(R.id.spn_v_createColor_paletteSave);

        paletteList = dbHelper.getPalettesByUser(SessionData.getCurrentUser());
        pLAdapter = new PaletteListAdapter(this, paletteList);
        spn_j_paletteSave.setAdapter(pLAdapter);

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
        sb_j_RGB_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_RGB_redValue.setText(Integer.toString(progress));
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_j_RGB_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_RGB_greenValue.setText(Integer.toString(progress));
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_j_RGB_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_j_RGB_blueValue.setText(Integer.toString(progress));
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updatePreview() {
        int r = sb_j_RGB_red.getProgress();
        int g = sb_j_RGB_green.getProgress();
        int b = sb_j_RGB_blue.getProgress();

        int color = Color.rgb(r, g, b);
        view_j_preview.setBackgroundColor(color);
        tv_j_hex.setText("#" + ColorData.RGBtoHex(r, g, b));
    }

    private void saveColor(boolean newPalette) {
        //needs error checking for:
        // - name contains special characters
        ColorData color = new ColorData();
        int r = sb_j_RGB_red.getProgress();
        int g = sb_j_RGB_green.getProgress();
        int b = sb_j_RGB_blue.getProgress();
        String hex = ColorData.RGBtoHex(r, g, b);
        String name = et_j_name.getText().toString();

        if (dbHelper.getColor(hex) == null && !name.isEmpty() && (spn_j_paletteSave.getSelectedItem() != null || newPalette)) {
            color.setHex(hex);
            color.setName(name);
            color.setAuthor(SessionData.getCurrentUser());
            dbHelper.addColorToDatabase(color);

            if (newPalette) {
                Palette palette = new Palette();
                List<ColorData> list = new ArrayList<>();
                list.add(color);
                palette.setColorList(list);
                palette.setAuthor(SessionData.getCurrentUser());

                dbHelper.addPaletteToDatabase(palette);
                dbHelper.addPaletteToUser(SessionData.getCurrentUser(), palette);
                paletteList.add(palette);
                pLAdapter.notifyDataSetChanged();
                spn_j_paletteSave.setSelection(paletteList.size() - 1);
            }
            else {
                dbHelper.addColorToPalette((Palette)spn_j_paletteSave.getSelectedItem(), color);
            }

            sb_j_RGB_red.setProgress(0);
            sb_j_RGB_green.setProgress(0);
            sb_j_RGB_blue.setProgress(0);
            et_j_name.setText("");

            //just for testing
            ColorData testColor = dbHelper.getColor(hex);
            Log.d("COLOR", "HEX: " + testColor.getHex() + ", NAME: " + testColor.getName() + ", AUTHOR: " + testColor.getAuthor().getUsername());
            tv_j_error.setVisibility(INVISIBLE);
        }
        else if (name.isEmpty()) {
            Log.d("ERROR", "NAME IS EMPTY");
            tv_j_error.setText("Name field cannot be empty!");
            tv_j_error.setVisibility(VISIBLE);
        }
        else if (spn_j_paletteSave.getSelectedItem() == null) {
            tv_j_error.setText("No palette is selected!");
            tv_j_error.setVisibility(VISIBLE);
        }
        else {
            Log.d("ERROR", "COLOR EXISTS IN DATABASE");
            tv_j_error.setText("Color already exists!");
            tv_j_error.setVisibility(VISIBLE);
        }
    }
}