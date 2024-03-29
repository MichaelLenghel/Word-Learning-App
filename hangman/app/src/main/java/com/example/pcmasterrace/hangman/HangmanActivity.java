package com.example.pcmasterrace.hangman;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HangmanActivity extends AppCompatActivity {
    Context context;
    CharSequence addText;
    int duration;
    Intent intent;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        context = getApplicationContext();
        addText = "Please input a username";
        duration = Toast.LENGTH_SHORT;

        //Anon fxn. to go to score
        Button btn = (Button)findViewById(R.id.viewScores);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ScoreBoardActivity.class);
                startActivity(intent);
            }
        });
    }

    /** Called when the user touches the button */
    public void startHangman(View view)
    {
        EditText inputText = (EditText)findViewById(R.id.inputName);
        userName = inputText.getText().toString(); //gets you the contents of edit text
        if (userName.matches("")) {
            Toast noNameAlert = Toast.makeText(context, addText, duration);
            //Change position of toast message
            noNameAlert.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 1000);
            noNameAlert.show();
        }
        else {
            intent = new Intent(getBaseContext(), PlayingHangman.class);
            intent.putExtra("userName", userName);

            startActivity(intent);
        }
    }
}
