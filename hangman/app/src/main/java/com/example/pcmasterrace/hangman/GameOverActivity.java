package com.example.pcmasterrace.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.SQLException;

public class GameOverActivity extends AppCompatActivity {

    TextView heading;
    TextView scoreText;
    Intent intent;
    String userName;
    int userScore;
    DataManager db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        heading = (TextView) findViewById(R.id.gameOverHeading);
        scoreText = (TextView) findViewById(R.id.totalScore);


        //Won = 1, Lose = 0
        int gameStatus= getIntent().getIntExtra("status",0);//0 is default value
        userScore = getIntent().getIntExtra("score",0);//0 is default value

        //Get the word to display if user lost
        Bundle extras = getIntent().getExtras();
        String userWord = "";
        if(extras !=null) {
            userWord = extras.getString("word", "");
            userName = extras.getString("userName", "");
        }

        String gameWon = "You won!";
        String gameLost = "Game Over!" + "\n The word was: " + userWord;
        String scoreDisplay = "Total Score: " + userScore;

        if(gameStatus == 0) {
            heading.setText(gameLost);
        }
        else {
            heading.setText(gameWon);
        }

        scoreText.setText(scoreDisplay);
    }

    /** Called when the user touches the button */
    public void startHangman(View view)
    {
        intent = new Intent(getBaseContext(), PlayingHangman.class);
        intent.putExtra("score", userScore);
        startActivity(intent);
    }

    /** Called when the user touches the button */
    public void addScore(View view)
    {
        db = new DataManager(this);

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        long val = db.insertScore(userName, Integer.toString(userScore));
    }

    /** Called when the user touches the button */
    public void viewScoreboard(View view)
    {

        intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
        startActivity(intent);
    }

    /** Called when the user touches the button */
    public void takePicture(View view)
    {
        //setContentView(R.layout.activity_playing_hangman);
        // Do something in response to button click
    }

}
