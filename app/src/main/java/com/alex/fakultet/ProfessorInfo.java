package com.alex.fakultet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProfessorInfo extends AppCompatActivity {

    private TextView professorID, usernameInfo, firstName, lastName, subject, semester;
    private Button logoutBtn;
    private DBHelper db;
    private ArrayList<String> professorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_info);

        professorID = findViewById(R.id.professorID);
        usernameInfo = findViewById(R.id.usernameInfo);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        subject = findViewById(R.id.subject);
        semester = findViewById(R.id.semester);
        logoutBtn = findViewById(R.id.logoutBtn);
        db = new DBHelper(this);
        professorData = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        String username = extras.getString("username");

        Cursor result = db.getData(username);

        while (result.moveToNext()) {
            professorID.setText("Professor's ID: " + String.valueOf(result.getInt(0)));
            usernameInfo.setText("Professor's username: " + result.getString(1));
            String[] full_name = result.getString(3).split(" ");
            firstName.setText("Professor's First Name:" + full_name[0]);
            lastName.setText("Professor's Last name: " + full_name[1]);
            subject.setText("His teaching subject: " + result.getString(4));
            semester.setText("Semester: " + result.getString(5));
        }


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}