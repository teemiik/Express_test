package com.example.tanat.express_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

public class CPUActivity extends AppCompatActivity implements View.OnClickListener{

    Button b_cpu;
    TextView t_cpu;
    EditText e_cpu;
    BigInteger pi;
    int z_pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpu);

        b_cpu = (Button) findViewById(R.id.b_cpu);
        t_cpu = (TextView) findViewById(R.id.t_cpu);
        e_cpu = (EditText) findViewById(R.id.e_cpu);

        b_cpu.setOnClickListener(this);

        chudnovsky.start();


    }

    @Override
    public void onClick(View v) {
        try {
            z_pi = Integer.parseInt(String.valueOf(e_cpu.getText()));
            chudnovsky.run();
        }
        catch (Exception e) {
                Log.e("report" ,e.getMessage());
            }
    }


    Thread chudnovsky = new Thread( // создаём новый поток
            new Runnable() { // описываем объект Runnable в конструкторе
                public void run() {
                    final long st = System.nanoTime();
                    try {
                        if (Integer.parseInt(String.valueOf(e_cpu.getText())) == 0) {
                            pi_chudnovsky(new BigInteger(String.valueOf(10)).pow(z_pi));
                        } else {
                            pi_chudnovsky(new BigInteger(String.valueOf(10 * 10)).pow(z_pi));
                        }
                        final long fin = System.nanoTime();
                        // правильный вывод в View
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                t_cpu.setText(String.format("%,12d", fin - st) + "ns");
                            }
                        });
                    }
                    catch (Exception e) {
                        Log.e("report" ,e.getMessage());
                    }
                }
            }
    );

    /*public long pi_chudnovsky(int val) {
        int k = 1;
        long a_k = val, a_sum = val, b_sum = 0, C3_OVER_24, total;
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
        pi = (426880 * sqrt(10005 * val, val) * val) / total;
        return (long) pi;
    }

    public long sqrt(int n, int val) {
        double point = Math.pow(10, 16);
        double n_point = (n * point) / val / point;
        long x = (long)((point * Math.sqrt(n_point)) * val / point);
        long n_val = n * val;
        long x_old;
        while (true) {
            x_old = x;
            x = (x + n_val / x) / 2;
            if (x == x_old)
                break;
        }
        return x;
    }*/

   /* public BigInteger pi_chudnovsky(BigInteger val) {
        int k = 1;
        BigInteger a_k = val;
        BigInteger a_sum =  val;
        BigInteger b_sum = new BigInteger(String.valueOf(0));
        BigInteger C3_OVER_24 = new BigInteger(String.valueOf(640320)).pow(3).divide(new BigInteger(String.valueOf(24)));
        while (true) {
            a_k = a_k.multiply(new BigInteger(String.valueOf((-(6 * k - 5) * (2 * k - 1) * (6 * k - 1)))));
            a_k = a_k.divide(new BigInteger(String.valueOf(k * k * k)).multiply(C3_OVER_24));
            a_sum = a_sum.add(a_k);
            b_sum = b_sum.add(new BigInteger(String.valueOf(k)).multiply(a_k));
            k += 1;
            if ( Integer.valueOf(String.valueOf(a_k)) == 0)
                break;
        }

        BigInteger total = new BigInteger(String.valueOf(13591409)).multiply(a_sum).add(new BigInteger(String.valueOf(545140134)).multiply(b_sum));
        pi =  sqrt(new BigInteger(String.valueOf(10005)).multiply(val), val).multiply(new BigInteger(String.valueOf(426880))).multiply(val).divide(total);
        return pi;
    }

    public BigInteger sqrt(BigInteger number, BigInteger trial)
    {
        BigInteger result = BigInteger.ZERO;
        BigInteger a = result;
        BigInteger b = result;

        boolean first = true;

        while (result.compareTo(trial) != 0) {

            if (!first)
                trial = result;
            else
                first = false;

            result = number.divide(trial).add(trial).divide(BigInteger.valueOf(2));

            if (result.equals(b)) {
                return a;
            }

            b = a;
            a = result;
        }
        return result;
    }*/

    public BigInteger pi_chudnovsky(BigInteger val) {
        int k = 1;
        BigInteger a_k = val;
        BigInteger a_sum =  val;
        BigInteger b_sum = new BigInteger(String.valueOf(0));
        BigInteger C3_OVER_24 = new BigInteger(String.valueOf(640320)).pow(3).divide(new BigInteger(String.valueOf(24)));
        while (true) {
            a_k = a_k.multiply(BigInteger.valueOf(-1).multiply(BigInteger.valueOf(6).multiply(new BigInteger(String.valueOf(k)).subtract(BigInteger.valueOf(5)))))
                    .multiply(BigInteger.valueOf(2).multiply(new BigInteger(String.valueOf(k)).subtract(BigInteger.valueOf(1))))
                    .multiply(BigInteger.valueOf(6).multiply(new BigInteger(String.valueOf(k)).subtract(BigInteger.valueOf(1))));
            a_k = a_k.divide(new BigInteger(String.valueOf(k)).multiply(new BigInteger(String.valueOf(k))).multiply(new BigInteger(String.valueOf(k))).multiply(C3_OVER_24));
            a_sum = a_sum.add(a_k);
            b_sum = b_sum.add(new BigInteger(String.valueOf(k)).multiply(a_k));
            k += 1;
            if ( Integer.valueOf(String.valueOf(a_k)) == 0)
                break;
        }

        BigInteger total = new BigInteger(String.valueOf(13591409)).multiply(a_sum).add(new BigInteger(String.valueOf(545140134)).multiply(b_sum));
        pi =  sqrt(new BigInteger(String.valueOf(10005)).multiply(val), val).multiply(new BigInteger(String.valueOf(426880))).multiply(val).divide(total);
        return pi;
    }

    public BigInteger sqrt(BigInteger number, BigInteger trial)
    {
        BigInteger result = BigInteger.ZERO;
        BigInteger a = result;
        BigInteger b = result;

        boolean first = true;

        while (result.compareTo(trial) != 0) {

            if (!first)
                trial = result;
            else
                first = false;

            result = number.divide(trial).add(trial).divide(BigInteger.valueOf(2));

            if (result.equals(b)) {
                return a;
            }

            b = a;
            a = result;
        }
        return result;
    }

    /*public long sqrt(int n, int val) {
        double point = Math.pow(10, 16);
        double n_point = (n * point) / val / point;
        long x = (long)((point * Math.sqrt(n_point)) * val / point);
        long n_val = n * val;
        long x_old;
        while (true) {
            x_old = x;
            x = (x + n_val / x) / 2;
            if (x == x_old)
                break;
        }
        return x;
    }*/

    /*public double pi_chudnovsky(int val) {
        int k = 0;
        double s;
        s = 0;

        while (k < val) {
           s = s + ((factorial(6 * k) * (13591409 + 545140134 * k)) / (factorial(3 * k) * Math.pow(factorial(k), 3) * Math.pow(-640320, (3*k))));
           k++;
        }
        s = s * (1 / (426880 / Math.sqrt(10005)));
        //total = 13591409 * a_sum + 545140134 * b_sum;
        return 1. / s;
    }*/

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

    /*public int factorial( int iNo ) {

        // Make sure that the input argument is positive

        if (iNo < 0) throw
                new IllegalArgumentException("iNo must be >= 0");

        // Use simple look to compute factorial....

        int factorial = 1;
        for(int i = 2; i <= iNo; i++)
            factorial *= i;

        return factorial;
    }*/
}
