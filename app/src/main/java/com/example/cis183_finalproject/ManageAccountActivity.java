package com.example.cis183_finalproject;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ManageAccountActivity extends AppCompatActivity {
    Intent homeActivity;
    Intent mainActivity;
    DatabaseHelper dbHelper;

    Button btn_j_back;
    TextView tv_j_oldPassword;
    EditText et_j_oldPassword;
    TextView tv_j_newPassword;
    EditText et_j_newPassword;
    TextView tv_j_error;
    Button btn_j_password;
    Button btn_j_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeActivity = new Intent(ManageAccountActivity.this, HomeActivity.class);
        mainActivity = new Intent(ManageAccountActivity.this, MainActivity.class);
        dbHelper = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_manageAccount_back);
        tv_j_oldPassword = findViewById(R.id.tv_v_manageAccount_oldPassword);
        et_j_oldPassword = findViewById(R.id.et_v_manageAccount_oldPassword);
        tv_j_newPassword = findViewById(R.id.tv_v_manageAccount_newPassword);
        et_j_newPassword = findViewById(R.id.et_v_manageAccount_newPassword);
        tv_j_error = findViewById(R.id.tv_v_manageAccount_error);
        btn_j_password = findViewById(R.id.btn_v_manageAccount_password);
        btn_j_delete = findViewById(R.id.btn_v_manageAccount_delete);

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
        btn_j_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        btn_j_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void changePassword() {
        String oldPass = et_j_oldPassword.getText().toString();
        String newPass = et_j_newPassword.getText().toString();
        String truePass = SessionData.getCurrentUser().getPassword();

        if (oldPass.equals(truePass) && !oldPass.isEmpty() && !newPass.isEmpty()) {
            dbHelper.changeUserPassword(SessionData.getCurrentUser(), newPass);
            tv_j_error.setVisibility(INVISIBLE);
            et_j_oldPassword.setText("");
            et_j_newPassword.setText("");
        }
        else if (oldPass.isEmpty() || newPass.isEmpty()) {
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Both fields must be filled out!");
        }
        else {
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Old password is incorrect!");
        }
    }

    private void deleteAccount() {
        //maybe add some kind of confirmation boolean to this
        dbHelper.deleteUserFromDatabase(SessionData.getCurrentUser());
        startActivity(mainActivity);
        finish();
    }
}