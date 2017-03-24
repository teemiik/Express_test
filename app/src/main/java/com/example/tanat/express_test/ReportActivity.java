package com.example.tanat.express_test;

import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReportActivity extends AppCompatActivity {

    String line;
    String file_name;
    TextView text_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        text_report = (TextView) findViewById(R.id.multi_text) ;
        file_name = "report.txt";

        try
        {
            File myFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "android" + File.separator + "data" + File.separator + "com.android.express_test" + File.separator + file_name);

            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Буфферезируем данные из выходного потока файла
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Класс для создания строк из последовательностей символов
             */
            StringBuilder stringBuilder = new StringBuilder();
            try {
                /*
                 * Производим построчное считывание данных из файла в конструктор строки,
                 * Псоле того, как данные закончились, производим вывод текста в TextView
                 */
                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }
                text_report.setText(stringBuilder);

            } catch (Exception e) {
                text_report.setText("Серьезные неполадки");
            }
        } catch (FileNotFoundException e) {
            text_report.setText("Отчет ещё не создан");
        }
    }
}
