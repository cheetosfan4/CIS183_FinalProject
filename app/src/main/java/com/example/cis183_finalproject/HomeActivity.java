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

    Button btn_j_back;
    Button btn_j_createColor;
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

        btn_j_back = findViewById(R.id.btn_v_home_back);
        btn_j_createColor = findViewById(R.id.btn_v_home_createColor);
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
    }
}