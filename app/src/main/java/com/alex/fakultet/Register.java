package com.alex.fakultet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    private EditText username, password, firstName, lastName;
    private Spinner fieldsSpin, semesterSpin;
    private ArrayAdapter<String> fieldsArrayAdapter, semesterArrayAdapter;
    private Button regBtn;
    private TextView errText;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.usernameReg);
        password = findViewById(R.id.passwordReg);
        firstName = findViewById(R.id.firstNameReg);
        lastName = findViewById(R.id.lastNameReg);
        fieldsSpin = findViewById(R.id.fieldReg);
        semesterSpin = findViewById(R.id.semesterReg);
        regBtn = findViewById(R.id.registerBtnReg);
        errText = findViewById(R.id.regErr);
        db = new DBHelper(this);

        fieldsArrayAdapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.firelds_array));
        fieldsSpin.setAdapter(fieldsArrayAdapter);

        semesterArrayAdapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.semester_array));
        semesterSpin.setAdapter(semesterArrayAdapter);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();
                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String subjectTxt = fieldsSpin.getSelectedItem().toString();
                String semesterTxt = semesterSpin.getSelectedItem().toString();
                String fullNameTxt = firstNameTxt + " " + lastNameTxt;

                boolean checkUsername = db.checkUsername(userTxt);

                if (checkUsername) {
                    Toast.makeText(Register.this, "Username taken.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean insert = db.registerProfessor(userTxt, passwordTxt, fullNameTxt, subjectTxt, semesterTxt);
                    db.insertSubject(subjectTxt,semesterTxt);
                    Cursor result = db.getData(userTxt);

                    while (result.moveToNext()) {
                        db.isnertProfessorPredmet(String.valueOf(result.getInt(0)));
                    }

                    if(insert) {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }
}