package com.example.beargameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;


public class GameActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();

        final Button game_btn_right = findViewById(R.id.game_btn_right);
        final Button game_btn_left = findViewById(R.id.game_btn_left);
        //final TextView main_TV_score = findViewById(R.id.game_TV_score);

        //final int delay = 1000; // milisec

        GameManager gameManager = new GameManager(this);

        gameManager.startGame();

        game_btn_right.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //main_TV_score.setText("123");
                gameManager.bearMoveRight();
            }
        });

        game_btn_left.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gameManager.bearMoveLeft();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}