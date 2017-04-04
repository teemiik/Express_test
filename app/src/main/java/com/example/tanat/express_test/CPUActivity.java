package com.example.tanat.express_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CPUActivity extends AppCompatActivity {

    double pi;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);
    }

    public long pi_chudnovsky(int val) {
        int k = 1;
        long a_k = val, a_sum = val, b_sum = 0, C3_OVER_24, total, pi;
        C3_OVER_24 = (long) (Math.pow(640320, 3) / 24);
        while (true) {
            a_k *= - (6 * k - 5) * (2 * k - 1) * (6 * k - 1);
            a_k /= k * k * k * C3_OVER_24;
            a_sum += a_k;
            b_sum += k * a_k;
            k += 1;
            if (a_k == 0)
                break;
        }

        total = 13591409 * a_sum + 545140134 * b_sum;
        pi = (426880 * sqrt(10005 * val, val) * val) / total;
        return pi;
    }

    public long sqrt(int n, int val) {
        double point = Math.pow(10, 16);
        double n_point = ((long) (n * point) / val) / point;
        long x = (long) (((long)(point * Math.sqrt(n_point)) * val) /point), x_old;
        int n_val = n * val;
        while (true) {
            x_old = x;
            x = (x + n_val / x) / 2;
            if (x == x_old)
                break;
        }
        return x;
    }
}
