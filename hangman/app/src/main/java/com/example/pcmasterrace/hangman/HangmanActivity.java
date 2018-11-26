package com.example.pcmasterrace.hangman;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

public class HangmanActivity extends AppCompatActivity {
    Context context;
    CharSequence addText;
    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        context = getApplicationContext();
        addText = "Please input a username";
        duration = Toast.LENGTH_SHORT;

        //Anon fxn.
        Button btn = (Button)findViewById(R.id.viewScores);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Called when the user touches the button */
    public void startHangman(View view)
    {
        EditText inputText = (EditText)findViewById(R.id.inputName);
        String content = inputText.getText().toString(); //gets you the contents of edit text
        if (content.matches("")) {
            Toast noNameAlert = Toast.makeText(context, addText, duration);
            //Change position of toast message
            noNameAlert.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1000);
            noNameAlert.show();
        }
        else {
            setContentView(R.layout.activity_playing_hangman);
        }
    }
}
