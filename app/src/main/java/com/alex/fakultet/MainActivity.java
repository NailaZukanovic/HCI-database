package com.alex.fakultet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginBtn, registerBtn;
    private TextView errText;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        errText = findViewById(R.id.errLogin);
        db = new DBHelper(this);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTxt = username.getText().toString();
                String passTxt = password.getText().toString();

                boolean checkUsername = db.checkUsername(userTxt);
                boolean checkPassword = db.checkPassword(passTxt);
                if (!checkUsername) {
                    errText.setText("Username doesn't exist");
                } else {
                    if (!checkPassword) {
                        errText.setText("Incorrect password");
                    } else {
                        Intent intent = new Intent(getBaseContext(), ProfessorInfo.class);
                        intent.putExtra("username", userTxt);
                        startActivity(intent);
                    }
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Register.class);
                startActivity(intent);
            }
        });
    }
}