package com.example.bridge_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.view.View.OnClickListener;

public class AdminHome extends AppCompatActivity implements OnClickListener{
    Button Bts, Btt, Btp, Btb, Btc;
    ImageButton lg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Bts = findViewById(R.id.bts);
        Btt = findViewById(R.id.btt);
        Btp = findViewById(R.id.btp);
        Btb = findViewById(R.id.btb);
        Btc = findViewById(R.id.btc);
        lg = findViewById(R.id.log_out);
        Bts.setOnClickListener(this);
        Btt.setOnClickListener(this);
        Btp.setOnClickListener(this);
        Btb.setOnClickListener(this);
        Btc.setOnClickListener(this);
        lg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bts:
                startActivity(new Intent(AdminHome.this, Students.class));
                break;
            case R.id.btt:
                startActivity(new Intent(AdminHome.this, Teachers.class));
                break;
            case R.id.btp:
                startActivity(new Intent(AdminHome.this, Parents.class));
                break;
            case R.id.btb:
                startActivity(new Intent(AdminHome.this, Buses.class));
                break;
            case R.id.btc:
                startActivity(new Intent(AdminHome.this, Circulars.class));
                break;
            case R.id.log_out:
                startActivity(new Intent(AdminHome.this, LoginActivity.class));
                break;

        }
    }
}
