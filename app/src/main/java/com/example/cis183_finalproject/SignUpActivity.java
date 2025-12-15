package com.example.cis183_finalproject;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    Intent mainActivity;
    DatabaseHelper dbHelper;

    EditText et_j_username;
    EditText et_j_password;
    EditText et_j_confirmpass;
    Button btn_j_signup;
    Button btn_j_back;
    TextView tv_j_error;

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
        dbHelper = new DatabaseHelper(this);

        et_j_username = findViewById(R.id.et_v_signup_username);
        et_j_password = findViewById(R.id.et_v_signup_password);
        et_j_confirmpass = findViewById(R.id.et_v_signup_confirmpass);
        btn_j_signup = findViewById(R.id.btn_v_signup_signup);
        btn_j_back = findViewById(R.id.btn_v_signup_back);
        tv_j_error = findViewById(R.id.tv_v_signup_error);

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
        btn_j_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        User user = new User();
        String enteredUsername = et_j_username.getText().toString();
        String enteredPassword = et_j_password.getText().toString();
        String confirmedPassword = et_j_confirmpass.getText().toString();
        boolean empty = false;
        boolean mismatch = false;

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || confirmedPassword.isEmpty()) {
            empty = true;
        }
        if (!enteredPassword.equals(confirmedPassword)) {
            mismatch = true;
        }

        //makes sure that the user can't sign up with the same username as an existing user
        if (!enteredUsername.isEmpty() && dbHelper.getUser(enteredUsername) != null) {
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Username already exists!");
            return;
        }

        if (!empty && !mismatch) {
            Log.d("SIGNUP", "SUCCESS");
            user.setUsername(enteredUsername);
            user.setPassword(enteredPassword);
            dbHelper.addUserToDatabase(user);

            tv_j_error.setVisibility(INVISIBLE);
            et_j_username.setText("");
            et_j_password.setText("");
            et_j_confirmpass.setText("");
        }
        else if (empty) {
            Log.d("SIGNUP", "EMPTY");
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("All fields must be filled out!");
        }
        else {
            Log.d("SIGNUP", "MISMATCH");
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Password fields do not match!");
        }
    }
}