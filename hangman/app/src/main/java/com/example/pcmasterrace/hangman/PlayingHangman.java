package com.example.pcmasterrace.hangman;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlayingHangman extends AppCompatActivity {
    private static Context context;
    private List<String> words;
    String TAG = "SpacesNotAdded";
    String wordToGuess;
    String userName;
    TextView currentWord;
    TextView livesLeft;
    Context textContext;
    Intent intent;
    boolean[] guessWordSpaces;
    int user_lives = 6;
    int score = 0;

    public static String userGuessContent;
    //wordLetters

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_hangman);
        currentWord = (TextView) findViewById(R.id.wordLetters);
        livesLeft = (TextView) findViewById(R.id.livesLeft);
        textContext = getApplicationContext();

        //Get the users name that we will add to the score
        Bundle extras = getIntent().getExtras();
        userName = "";
        if(extras !=null) {
            userName = extras.getString("userName", "");
        }

        //Return a list holding all of the words
        words = readWords("words.txt");
//        for (String string : words)
//            Log.d(TAG, string);

        //Get a random number which we will use to recover a random number from he arraylist
        int ran_num = (int) (Math.random()*words.size());

        //Assign a random word
        wordToGuess = words.get(ran_num);
        guessWordSpaces = new boolean[wordToGuess.length()];
        Log.d(TAG, wordToGuess);

        updateScreen();
    }

    public void submitGuess(View view)
    {
        EditText inputText = (EditText)findViewById(R.id.userGuess);
        userGuessContent = inputText.getText().toString(); //gets usersguess

        if (userGuessContent.matches(wordToGuess)) {
            score += 100;
            intent = new Intent(getBaseContext(), GameOverActivity.class);
            intent.putExtra("status", 1);
            intent.putExtra("score", score);
            startActivity(intent);
        }
        // Accounts for a letter guess
        else if(userGuessContent.length() == 1) {
            checkLetter(userGuessContent);

        }
        //Accounts for a bad guess (more than one letter)
        else {
            user_lives --;
            updateScreen();
        }
    }//end submitGuess

    public void checkLetter(String userGuess)
    {
        boolean lifeLost = true;
        for(int i = 0; i < guessWordSpaces.length; i++)
        {
            Log.d(TAG, wordToGuess);
            Log.d(TAG, "Comparing " + userGuess + " and " + wordToGuess.charAt(i));
            if ( userGuess.toLowerCase().matches( Character.toString( wordToGuess.toLowerCase().charAt(i) ) ) && guessWordSpaces[i] == false )
            {
                lifeLost = false;
                guessWordSpaces[i] = true;
            }
        }
        if (lifeLost)
        {
            user_lives--;
        }

        updateScreen();
    }

    String printableString;
    public void updateScreen()
    {
        printableString = "";
        boolean allPrinted = true;
        for(int i = 0; i < guessWordSpaces.length; i++)
        {
            if (guessWordSpaces[i] == false)
            {
                printableString += "_";
                allPrinted = false;
                if (guessWordSpaces.length != i + 1)
                {
                    printableString += " ";
                }
            }
            else
            {
                printableString += wordToGuess.charAt(i);
            }
        }

        // User won game as all letters have been individually guessed
        if (allPrinted == true)
        {
            score += 100;
            intent = new Intent(getBaseContext(), GameOverActivity.class);
            intent.putExtra("status", 1);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        //User is dead
        if (user_lives == 0)
        {
            intent = new Intent(getBaseContext(), GameOverActivity.class);
            intent.putExtra("status", 0);
            intent.putExtra("score", score);
            intent.putExtra("userName", userName);
            intent.putExtra("word", wordToGuess);
            startActivity(intent);
        }


        //Log.d(TAG, printableString);
        currentWord.setText(printableString);
        livesLeft.setText("Lives Left: " + user_lives);
    }

    //Get words from text file and return an array list of all the words
    public List<String> readWords(String path)
    {
        //Asset manager to open file
        AssetManager am = this.getAssets();
        //List to hold words
        List<String> words_list = new ArrayList<>();
        //Buffered reader when converting from input stream
        BufferedReader reader = null;

        //Open file and add it to a string
        try {
            InputStream words_is = am.open(path);
            reader = new BufferedReader(new InputStreamReader(words_is));
            String line;

            //Get each word from buffer into a string and add it to the array list
            while ((line = reader.readLine()) != null)
                words_list.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words_list;
    }
}
