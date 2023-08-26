package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int time;
    TextView tv,tv1;
    SeekBar timer;
    Button b;
    MediaPlayer media;
    CountDownTimer cn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (SeekBar) findViewById(R.id.seekBar);
        b = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);
        tv1 = (TextView) findViewById(R.id.textView2);
        media= MediaPlayer.create(this,R.raw.alarm);
        AudioManager audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol =audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,maxVol,0);

        timer.setMax(120);
        timer.setProgress(30);

        timer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minutes = i / 60;
                int seconds = i % 60;

                String s2;
                if (seconds<10&&seconds>=0) {
                    s2 = "0"+Integer.toString(seconds);
                }else{
                    s2 = Integer.toString(seconds);
                }

                tv.setText(Integer.toString(minutes) + ":" + s2);
                time = i;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void goAction(View view){

        if(b.getText().equals("GO!")){
            b.setText("STOP");
            timer.setEnabled(false);
            new CountDownTimer(time * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Long minutes = (millisUntilFinished / 1000) / 60;
                    Long seconds = (millisUntilFinished / 1000) % 60;

                    String s2=Long.toString(seconds);
                    if (seconds>=0 && seconds<10) {
                        s2 = "0"+Long.toString(seconds);
                    }
                    else {
                        s2=Long.toString(seconds);
                    }

                    tv.setText(Long.toString(minutes) + ":" + s2);


                }

                @Override
                public void onFinish() {
                    media.start();
                    b.setText("STOP ALARM");
                    tv1.setText("HEY, YOUR TIMER HAS COMPLETED..");



                }
            }.start();



        } else if (b.getText().equals("STOP ALARM")) {
            tv.setText("0.30");
            timer.setProgress(30);
            timer.setEnabled(true);
            media.stop();
            b.setText("GO!");
            tv1.setText("");

        } else {
            //cn.cancel();
            b.setText("GO!");
            tv.setText("0:30");
            timer.setProgress(30);
            timer.setEnabled(true);
        }

    }
}

