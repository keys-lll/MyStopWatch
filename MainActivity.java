package com.example.myapplicationexercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    long startTimes=0;
    boolean  isRunnableRemoved=true;
    public TextView timerTextView;
    private Handler handler;
    private Runnable updateRunnable;


    private Button startButton, stopButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }
    private void startTimer() {
        startTimes = System.currentTimeMillis();


        updateRunnable = new Runnable() {
            @Override
            public void run() {
                updateTimerText();
                // 每隔一毫秒更新一次
                handler.postDelayed(this, 1);
            }
        };
        // 開始更新時間
        handler.post(updateRunnable);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    private void stopTimer() {
        setRunnableRemoved();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);
    }

    private void resetTimer() {
        setRunnableRemoved();
        startTimes = System.currentTimeMillis();
        updateTimerText();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        resetButton.setEnabled(false);
    }
    protected void setRunnableRemoved() {
        // 移除Runnable
        handler.removeCallbacks(updateRunnable);
    }

    public void updateTimerText() {

        long currentTime = System.currentTimeMillis()-startTimes;
        int minseconds = (int) (currentTime);
        int seconds = minseconds / 1000;
        int minutes = seconds / 60;
        int centiseconds = minseconds % 100;
        seconds %= 60;
        minutes %= 60;

        String time = String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
        timerTextView.setText(time);

    }

}


