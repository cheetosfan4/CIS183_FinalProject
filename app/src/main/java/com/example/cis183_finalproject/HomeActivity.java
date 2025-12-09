package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    Intent mainActivity;
    Intent createColorActivity;
    Intent manageColorsActivity;
    Intent managePalettesActivity;
    Intent manageAccountActivity;

    Button btn_j_back;
    Button btn_j_createColor;
    Button btn_j_manageColors;
    Button btn_j_managePalettes;
    Button btn_j_manageAccount;
    Button btn_j_search;
    TextView tv_j_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainActivity = new Intent(HomeActivity.this, MainActivity.class);
        createColorActivity = new Intent(HomeActivity.this, CreateColorActivity.class);
        manageColorsActivity = new Intent(HomeActivity.this, ManageColorsActivity.class);
        managePalettesActivity = new Intent(HomeActivity.this, ManagePalettesActivity.class);
        manageAccountActivity = new Intent(HomeActivity.this, ManageAccountActivity.class);

        btn_j_back = findViewById(R.id.btn_v_home_back);
        btn_j_createColor = findViewById(R.id.btn_v_home_createColor);
        btn_j_manageColors = findViewById(R.id.btn_v_home_manageColors);
        btn_j_managePalettes = findViewById(R.id.btn_v_home_managePalettes);
        btn_j_manageAccount = findViewById(R.id.btn_v_home_manageAccount);
        btn_j_search = findViewById(R.id.btn_v_home_search);
        tv_j_user = findViewById(R.id.tv_v_home_user);

        tv_j_user.setText("Signed in as: " + SessionData.getCurrentUser().getUsername());
        listeners();
    }

    private void listeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivity);
                finish();
            }
        });
        btn_j_createColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(createColorActivity);
                finish();
            }
        });
        btn_j_manageColors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(manageColorsActivity);
                finish();
            }
        });
        btn_j_managePalettes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(managePalettesActivity);
                finish();
            }
        });
        btn_j_manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(manageAccountActivity);
                finish();
            }
        });
    }
}