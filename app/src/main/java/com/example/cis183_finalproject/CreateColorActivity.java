package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateColorActivity extends AppCompatActivity {

    Intent homeActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;

    TextView tv_j_mode;
    Spinner spn_j_mode;

    //====== RGB mode ==============================================================================
    ConstraintLayout cons_j_mode_RGB;
    TextView tv_j_RGB_red;
    EditText et_j_RGB_red;
    TextView tv_j_RGB_green;
    EditText et_j_RGB_green;
    TextView tv_j_RGB_blue;
    EditText et_j_RGB_blue;
    //==============================================================================================

    TextView tv_j_name;
    EditText et_j_name;

    View view_j_preview;
    TextView tv_j_hex;

    Button btn_j_save;
    TextView tv_j_paletteSave;
    Spinner spn_j_paletteSave;
    Button btn_j_paletteSave;


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
        tv_j_RGB_red = findViewById(R.id.tv_v_createColor_RGB_red);
        et_j_RGB_red = findViewById(R.id.et_v_createColor_RGB_red);
        tv_j_RGB_green = findViewById(R.id.tv_v_createColor_RGB_green);
        et_j_RGB_green = findViewById(R.id.et_v_createColor_RGB_green);
        tv_j_RGB_blue = findViewById(R.id.tv_v_createColor_RGB_blue);
        et_j_RGB_blue = findViewById(R.id.et_v_createColor_RGB_blue);
        //==========================================================================================

        tv_j_name = findViewById(R.id.tv_v_createColor_name);
        et_j_name = findViewById(R.id.et_v_createColor_name);

        view_j_preview = findViewById(R.id.view_v_createColor_preview);
        tv_j_hex = findViewById(R.id.tv_v_createColor_hex);

        btn_j_save = findViewById(R.id.btn_v_createColor_save);
        tv_j_paletteSave = findViewById(R.id.tv_v_createColor_paletteSave);
        spn_j_paletteSave = findViewById(R.id.spn_v_createColor_paletteSave);
        btn_j_paletteSave = findViewById(R.id.btn_v_createColor_paletteSave);

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
        btn_j_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveColor();
            }
        });
    }

    private void saveColor() {
        //needs error checking for:
        // - r, g, or b value isn't in the range of 0-255
        // - one of the fields is empty
        // - name contains special characters
        // - hex already exists in database
        ColorData color = new ColorData();
        int r = Integer.parseInt(et_j_RGB_red.getText().toString());
        int g = Integer.parseInt(et_j_RGB_green.getText().toString());
        int b = Integer.parseInt(et_j_RGB_blue.getText().toString());
        String hex = ColorData.RGBtoHex(r, g, b);

        color.setHex(hex);
        color.setName(et_j_name.getText().toString());
        color.setAuthor(SessionData.getCurrentUser());
        dbHelper.addColorToDatabase(color);

        et_j_RGB_red.setText("");
        et_j_RGB_green.setText("");
        et_j_RGB_blue.setText("");
        et_j_name.setText("");

        //just for testing
        ColorData testColor = dbHelper.getColor(hex);
        Log.d("COLOR", "HEX: " + testColor.getHex() + ", NAME: " + testColor.getName() + ", AUTHOR: " + testColor.getAuthor().getUsername());
    }
}