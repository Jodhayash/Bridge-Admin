package com.example.bridge_admin;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity  {



    EditText AdminPassEt;
    ImageButton submitbt;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AdminPassEt = findViewById(R.id.adminpass);
        submitbt = findViewById(R.id.submit_bt);

        submitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    checkAdminpass();
                }

                private void checkAdminpass () {

                    String AdminPass = AdminPassEt.getText().toString().trim();

                    if (AdminPass.isEmpty()) {
                        AdminPassEt.setError("Enter Password to continue");
                    } else if (AdminPass.equalsIgnoreCase("admin")) {
                        Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, AdminHome.class));

                    }
                    else
                        AdminPassEt.setError("Wrong Password, Try Again");
                }



        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}