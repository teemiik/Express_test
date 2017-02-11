package com.example.tanat.express_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GeneralActivity extends AppCompatActivity implements View.OnClickListener{

    Button vibor_express;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        vibor_express = (Button) findViewById(R.id.vibor_express);
        vibor_express.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vibor_express) {
            Intent intent = new Intent(this, ScreenTestActivity.class);
            startActivity(intent);
        }
    }
}

