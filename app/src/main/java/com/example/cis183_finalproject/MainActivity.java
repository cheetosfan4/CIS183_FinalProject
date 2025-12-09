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

public class MainActivity extends AppCompatActivity {

    Intent signUpActivity;
    Intent homeActivity;
    DatabaseHelper dbHelper;

    EditText et_j_username;
    EditText et_j_password;
    TextView tv_j_error;
    Button btn_j_signin;
    Button btn_j_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signUpActivity = new Intent(MainActivity.this, SignUpActivity.class);
        homeActivity = new Intent(MainActivity.this, HomeActivity.class);
        dbHelper = new DatabaseHelper(this);
        dbHelper.fakeData();
        SessionData.setCurrentUser(null);

        et_j_username = findViewById(R.id.et_v_main_username);
        et_j_password = findViewById(R.id.et_v_main_password);
        tv_j_error = findViewById(R.id.tv_v_main_error);
        btn_j_signin = findViewById(R.id.btn_v_main_signin);
        btn_j_signup = findViewById(R.id.btn_v_main_signup);

        listeners();
    }

    private void listeners() {
        btn_j_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
        btn_j_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpActivity);
                finish();
            }
        });
    }

    private void signin() {
        String user = et_j_username.getText().toString();
        String pass = et_j_password.getText().toString();
        boolean empty = false;
        boolean found = false;

        if (!user.isEmpty() && !pass.isEmpty()) {
            found = dbHelper.checkCredentials(user, pass);
        }
        else {
            empty = true;
        }

        if (found) {
            Log.d("SIGNIN", "CORRECT");
            User currentUser = dbHelper.getUser(user);
            SessionData.setCurrentUser(currentUser);
            //this is to make sure the database actually gets the full user and it is correctly sent to the sessiondata
            Log.d("CURRENT USER", "USERNAME: " + SessionData.getCurrentUser().getUsername() + ", PASSWORD: " + SessionData.getCurrentUser().getPassword());
            tv_j_error.setVisibility(INVISIBLE);
            startActivity(homeActivity);
            finish();
        }
        else if (empty) {
            Log.d("SIGNIN", "EMPTY");
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Both fields must be filled out!");
        }
        else {
            Log.d("SIGNIN", "INCORRECT");
            tv_j_error.setVisibility(VISIBLE);
            tv_j_error.setText("Username or password is incorrect!");
        }
    }
}