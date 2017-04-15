package com.example.tanat.express_test;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import java.util.HashMap;




public class SoundActivity extends AppCompatActivity {

    MediaPlayer m =null;
    MediaRecorder myAudioRecoder = null;
    String outputFile=null;
    boolean switchState;
    static HashMap<String, Boolean> in_report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputFile += "/audiorecorder.3gpp";

        final Switch soundsw = (Switch) findViewById(R.id.soundswitch);

        soundsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                switchState = isChecked;

            }


        });
    }

    public void start (View v) throws Exception {
      ditchRecord();

        try {
            myAudioRecoder = new MediaRecorder();
            myAudioRecoder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecoder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myAudioRecoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            myAudioRecoder.setOutputFile(outputFile);
            myAudioRecoder.setMaxDuration(5000); //Время записи
            myAudioRecoder.prepare(); //подготовка рекордера
            myAudioRecoder.start();
            Toast.makeText(this, "Запись началась. Время записи: 5 сек.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "При старте записи возникли проблемы.", Toast.LENGTH_SHORT ).show();
        }
    }

    public void ditchRecord ()
    {
        if (myAudioRecoder!=null)
                myAudioRecoder.release();


    }


    public void stop (View v)
    {
        try {
            myAudioRecoder.stop();
            myAudioRecoder.release();
            myAudioRecoder=null;
            Toast.makeText(this, "Запись завершена", Toast.LENGTH_SHORT ).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка остановки записи", Toast.LENGTH_SHORT ).show();
        }
    }

    public void play (View v) throws Exception {
        ditchPlay();
        try {
            MediaPlayer m =  new MediaPlayer();

            if(switchState) m.setAudioStreamType(AudioManager.STREAM_VOICE_CALL); //разговорный динамик
            else m.setAudioStreamType(AudioManager.STREAM_MUSIC); //основной динамик
            m.setDataSource(outputFile);
            m.prepare();
            m.start();
            Toast.makeText(this, "Воспроизведение записи", Toast.LENGTH_SHORT ).show();
        }
          catch (Exception e) {
              e.printStackTrace();
              Toast.makeText(this, "Ошибка воспроизведения записи", Toast.LENGTH_SHORT ).show();
          }

        Thread.sleep(5500); //остонавливаем основной поток на 5,5 сикунд, чтобы юзер успел послушать


        DialogFragment newFragment = new FireMissilesDialogFragment();
        newFragment.show(getSupportFragmentManager(), "missiles");

    }

    public void ditchPlay ()
    {
        if (m!=null)
            try {
                myAudioRecoder.release();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


    }




    public static class FireMissilesDialogFragment extends DialogFragment {



        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Вы слышали ваш голос из динамиков?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                in_report = new HashMap<>();
                                in_report.put("Динамик разговорный", true);
                                in_report.put("Динамик основной", true);
                                in_report.put("Микрофон", true);
                                GeneralActivity.recordReport(in_report);
                                Toast.makeText(getActivity(), "Данные внесены в отчёт", Toast.LENGTH_SHORT ).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Ошибка записи в отчёт", Toast.LENGTH_SHORT ).show();

                            }

                        }





                    })
                    .setNegativeButton("Hет", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                in_report = new HashMap<>();
                                in_report.put("Динамик разговорный", false);
                                in_report.put("Динамик основной", false);
                                in_report.put("Микрофон", false);
                                GeneralActivity.recordReport(in_report);
                                Toast.makeText(getActivity(), "Данные внесены в отчёт", Toast.LENGTH_SHORT ).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Ошибка записи в отчёт", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    })

                    .setNeutralButton("Заново", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onPause();
                        }
                    });
            return builder.create();
        }
    }


}

