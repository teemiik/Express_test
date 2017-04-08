package com.example.tanat.express_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

public class CPUActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_cpu;
    TextView t_cpu;
    EditText e_cpu;
    double pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        b_cpu = (Button) findViewById(R.id.b_cpu);
        t_cpu = (TextView) findViewById(R.id.t_cpu);
        e_cpu = (EditText) findViewById(R.id.e_cpu);

        b_cpu.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        //chudnovsky.start();
        pi = pi_chudnovsky(Integer.valueOf(String.valueOf(e_cpu.getText())));
        t_cpu.setText(String.valueOf(pi));
    }


    /*Thread chudnovsky = new Thread( // создаём новый поток
            new Runnable() { // описываем объект Runnable в конструкторе
                public void run() {

                }
            }
    );*/

    /*public long pi_chudnovsky(int val) {
        int k = 1;
        long a_k = val, a_sum = val, b_sum = 0, C3_OVER_24, total, pi;
        C3_OVER_24 = (long) (Math.pow(640320, 3) / 24);
        while (true) {
            a_k *= - 1 * (6 * k - 5) * (2 * k - 1) * (6 * k - 1);
            a_k /= k * k * k * C3_OVER_24;
            a_sum += a_k;
            b_sum += k * a_k;
            k += 1;
            if (a_k == 0)
                break;
        }

        total = 13591409 * a_sum + 545140134 * b_sum;
        pi = (long) ((426880 * Math.pow(10005 * val, 1.0/val) * val) / total);
        return pi;
    }*/

   /* public long sqrt(int n, int val) {
        double point = Math.pow(10, 16);
        double n_point = (n * point / val) / point;
        long x = (long)(((point * Math.sqrt(n_point)) * val) / point);
        int n_val = n * val;
        long x_old;
        while (true) {
            x_old = x;
            x = (x + n_val / x) / 2;
            if (x == x_old)
                break;
        }
        return x;
    }*/

    public double pi_chudnovsky(int val) {
       int k = 0;
       double k1 = Math.sqrt(10005);
       double k2 = 426880 * k1;
       double k3 = 1/k2;

       double s = 0, f1, f2, f3, f4, f5, f6, f7, f8;

       while (k < val) {
           f1 = Math.pow((-1), k) * factorial(6 * k);
           f2 = 13591409 + 545140134 * k;
           f3 = f1 * f2;
           f4 = factorial(3 * k);
           f5 = Math.pow(factorial(k), 3);
           f6 = Math.pow(640320, (3*k + 3./2));
           f7 = f4 * f5 * f6;
           f8 = f3 / f7;
           s = s + f8;
           k++;
       }

       s = s * 12;

       //total = 13591409 * a_sum + 545140134 * b_sum;
       return 1 / s;
    }

   /* public double pi_chudnovsky(int val) {
       int k = 0;
       double k1 = 0;

       while (k < val) {
           k1 += (Math.pow(-1, k) * factorial(6 * k)) / ((Math.pow(factorial(k), 3) * factorial(3 * k)) * (13591409 + 545140134 * k) / (Math.pow(640320, 3*k)));
           k++;
       }

       k1 = k1 * Math.sqrt(10005) / (2135467200 * 2);

       //total = 13591409 * a_sum + 545140134 * b_sum;
       return k1;
    }*/

    /*public double pi_chudnovsky(int val) {
        pi = 13591409;
        double ak = 1;
        int k = 1;
        double val1;
        while (k < val) {
            ak *= -(6 * k - 5) * (2 * k - 1) * (6 * k - 1) / (k * k * k * 26680 * 640320 * 640320);

            val1 = ak * (13591409 + 545140134 * k);

            // d = ((6 * k - 5) * (2 * k - 1) * (6 * k - 1)) / (k * k * k * 26680 * 640320 * 640320);


            pi += val1;
            k += 1;
        }
        pi = pi * Math.sqrt(10005) / (2135467200 * 2);
        pi = Math.pow(pi, (-1));
        return pi;
    }*/

    public int factorial( int iNo ) {

        // Make sure that the input argument is positive

        if (iNo < 0) throw
                new IllegalArgumentException("iNo must be >= 0");

        // Use simple look to compute factorial....

        int factorial = 1;
        for(int i = 2; i <= iNo; i++)
            factorial *= i;

        return factorial;
    }
}
