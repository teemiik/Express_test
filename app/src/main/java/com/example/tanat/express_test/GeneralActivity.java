package com.example.tanat.express_test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class GeneralActivity extends AppCompatActivity implements View.OnClickListener{

    Button vibor_express;
    Button b_report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        vibor_express = (Button) findViewById(R.id.vibor_express);
        b_report = (Button) findViewById(R.id.b_report);
        vibor_express.setOnClickListener(this);
        b_report.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vibor_express) {
            Intent intent = new Intent(this, ScreenTestActivity.class);
            startActivity(intent);
        }
        else if (v.getId() == R.id.b_report) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
        }
    }


    //функция записи отчета
    public static void recordReport(HashMap report) {
        String file_name = "report.txt";
        String text;
        Set<HashMap.Entry> set_values = report.entrySet();
        try {
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "android" + File.separator + "data" + File.separator + "com.android.express_test");
            if (!folder.exists()) {
                folder.mkdir();
            }
            File file_report = new File(folder, file_name);
            file_report.createNewFile();
            try  (FileOutputStream outputStream = new FileOutputStream(file_report, true)) {
                for (HashMap.Entry value : set_values) {
                    text = String.valueOf(value.getKey()) + " | " + String.valueOf(value.getValue() + "\n");
                    outputStream.write(text.getBytes());
                }

            } catch (IOException e) {
                Log.e("report" ,e.getMessage());
            }

        } catch (Exception ex) {
            Log.e("report" ,ex.getMessage());
        }
    }

}

