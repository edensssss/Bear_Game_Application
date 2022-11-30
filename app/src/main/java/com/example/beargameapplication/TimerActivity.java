package com.example.beargameapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.TimerTask;

public class TimerActivity extends AppCompatActivity {

    private MaterialTextView timer_LBL_info;

    final int DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        timer_LBL_info = findViewById(R.id.game_TV_score);
    }

    private void updateTimeUI() {
        timer_LBL_info.setText("" + System.currentTimeMillis());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }


    private CountDownTimer countDownTimer;

    private void startTimer() {
        countDownTimer = new CountDownTimer(99999999, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("pttt", "A: " + Thread.currentThread().getName() + " - " + System.currentTimeMillis());
                updateTimeUI();

            }

            public void onFinish() {
            }

        }.start();
    }

    private void stopTimer() {
        countDownTimer.cancel();
    }
}