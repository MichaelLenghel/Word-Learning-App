package com.example.pcmasterrace.hangman;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /** Called when the user touches the button */
    public void startHangman(View view)
    {
        setContentView(R.layout.activity_playing_hangman);
        // Do something in response to button click
    }

    /** Called when the user touches the button */
    public void addScore(View view)
    {
        //DB code code to add score
    }

    /** Called when the user touches the button */
    public void viewScoreboard(View view)
    {
        setContentView(R.layout.activity_score_board);
    }

    /** Called when the user touches the button */
    public void takePicture(View view)
    {
        //setContentView(R.layout.activity_playing_hangman);
        // Do something in response to button click
    }

}
