package com.example.cis183_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    Intent mainActivity;

    EditText et_j_username;
    EditText et_j_password;
    EditText et_j_confirmpass;
    Button btn_j_signup;
    Button btn_j_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainActivity = new Intent(SignUpActivity.this, MainActivity.class);

        et_j_username = findViewById(R.id.et_v_signup_username);
        et_j_password = findViewById(R.id.et_v_signup_password);
        et_j_confirmpass = findViewById(R.id.et_v_signup_confirmpass);
        btn_j_signup = findViewById(R.id.btn_v_signup_signup);
        btn_j_back = findViewById(R.id.btn_v_signup_back);

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