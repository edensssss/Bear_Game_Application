package com.example.beargameapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GameManager extends AppCompatActivity {

    static final int ROWS = 7;
    static final int COLS = 3;
    static final int LIVES = 3;
    static final int LENGTH_LONG = 3;
    static final int GAME_SPEED = 1000; //mil sec
    static final int VIBRATE_LENGTH = 200; //mil sec

    Context context;
    private int score = 0;
    private int currentLives;
    private int life;
    private int[][] stonesArr;
    private int[] prevStonesArr;
    private int counter;

    private int bearCurrentLocation = 1; // 0 - right, 1 - middle, 2 - left

    final ImageView game_left_bear;
    final ImageView game_middle_bear;
    final ImageView game_right_bear;

    final ShapeableImageView[] game_IMG_hearts;
    ShapeableImageView[][] game_grid_IMG_stones;


    public GameManager(Context context) {

        this.context=context;
        this.life = life;
        this.bearCurrentLocation = 1;
        this.currentLives = LIVES;
        this.stonesArr = new int[ROWS+1][COLS]; // +1 using to simulate "crash" with the bear
        this.prevStonesArr = new int[ROWS];
        this.counter = 0;

        game_left_bear = (ImageView) ((Activity)context).findViewById(R.id.game_left_bear);
        game_middle_bear = (ImageView) ((Activity)context).findViewById(R.id.game_middle_bear);
        game_right_bear = (ImageView) ((Activity)context).findViewById(R.id.game_right_bear);

        game_IMG_hearts = new ShapeableImageView[]{
                (ShapeableImageView) ((Activity)context).findViewById(R.id.main_img_heart_1),
                (ShapeableImageView) ((Activity)context).findViewById(R.id.main_img_heart_2),
                (ShapeableImageView) ((Activity)context).findViewById(R.id.main_img_heart_3),
        };

        game_grid_IMG_stones = new ShapeableImageView[][]{
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_0_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_0_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_0_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_1_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_1_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_1_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_2_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_2_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_2_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_3_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_3_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_3_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_4_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_4_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_4_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_5_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_5_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_5_2)
                },
                {(ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_6_0),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_6_1),
                        (ShapeableImageView) ((Activity)context).findViewById(R.id.game_grid_6_2)
                }

        };



    }

    public void bearMoveLeft() {
        if(bearCurrentLocation == 2){
            return;
        }
        else if(bearCurrentLocation == 1){
            game_left_bear.setVisibility(View.INVISIBLE);
            game_middle_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.VISIBLE);
            bearCurrentLocation = 2;
        }
        else{
            game_middle_bear.setVisibility(View.VISIBLE);
            game_left_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.INVISIBLE);
            bearCurrentLocation = 1;
        }
    }


    public void bearMoveRight() {
        if(bearCurrentLocation == 0){
            return;
        }
        else if(bearCurrentLocation == 1){
            game_right_bear.setVisibility(View.INVISIBLE);
            game_middle_bear.setVisibility(View.INVISIBLE);
            game_left_bear.setVisibility(View.VISIBLE);
            bearCurrentLocation = 0;
        }
        else{
            game_middle_bear.setVisibility(View.VISIBLE);
            game_left_bear.setVisibility(View.INVISIBLE);
            game_right_bear.setVisibility(View.INVISIBLE);
            bearCurrentLocation = 1;
        }
    }

    public void startGame()
    {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (currentLives > 0 && currentLives <= LIVES)
                    {
                        updateStoneMatrix();
                        showStoneOnScreen();
                        checkExplosion();
                    }
                }
            }, 0, GAME_SPEED);
    }


    private void checkExplosion() {
        int location = bearCurrentLocation;
        if(stonesArr[ROWS][bearCurrentLocation] == 1){
            vibrateDevice(this.context);
            reduceLives();
        }
    }

    public static void vibrateDevice(Context mContext){
        Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VIBRATE_LENGTH);
    }

    private void reduceLives() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    game_IMG_hearts[currentLives-1].setVisibility(View.INVISIBLE);

                    if(currentLives == 1){
                        Toast.makeText(context,"GAME OVER", Toast.LENGTH_SHORT).show();
                    } else {
                        currentLives--;
                    }
                }
            });

    }


    private void showStoneOnScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        if(stonesArr[i][j] == 1){
                            game_grid_IMG_stones[i][j].setVisibility(View.VISIBLE);
                        }else{
                            game_grid_IMG_stones[i][j].setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });
    }




    private void updateStoneMatrix() {


        Random ran = new Random();
        int randomStoneLocation = ran.nextInt(3);

        if(randomStoneLocation == 2){
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 1;
        }else if(randomStoneLocation == 1){
            stonesArr[0][0] = 0;
            stonesArr[0][1] = 1;
            stonesArr[0][2] = 0;
        }else if(randomStoneLocation == 0){
            stonesArr[0][0] = 1;
            stonesArr[0][1] = 0;
            stonesArr[0][2] = 0;
        }

        for (int i = (ROWS); i > 0; i--) {
            for (int j = 0; j < COLS; j++) {
                    stonesArr[i][j] = stonesArr[i-1][j];
            }
        }
    }


}
