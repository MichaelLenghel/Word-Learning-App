package com.example.pcmasterrace.hangman;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

public class GameOverActivity extends AppCompatActivity {

    TextView heading;
    TextView scoreText;
    TextView definition;
    Intent intent;
    String userName;
    String userWord;
    int userScore;
    int gameStatus;
    DataManager db;
    String DEBUG_TAG = "find_error";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        heading = (TextView) findViewById(R.id.gameOverHeading);
        scoreText = (TextView) findViewById(R.id.totalScore);
        definition = (TextView) findViewById(R.id.definition);


                //Won = 1, Lose = 0
        gameStatus= getIntent().getIntExtra("status",0);//0 is default value
        userScore = getIntent().getIntExtra("score",0);//0 is default value

        //Get the word to display if user lost
        Bundle extras = getIntent().getExtras();
        userWord = "";
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


        //Display definition of word on screen
        getDef();

    }

    public void getDef()
    {
        String defUrl = "https://www.dictionary.com/browse/" + userWord;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo     = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            new DownloadWebpageTask().execute(defUrl);
        }
        else
        {
            definition.setText("No definitions available");
        }
    }

    /* Use AsyncTask to create a task away from the main UI thread. This task takes a URL string and uses it to create an HttpUrlConnection. Once the connection has been established, the AsyncTask downloads the contents of the webpage as an InputStream. Finally, the InputStream is converted into a string, which is displayed in the UI by the AsyncTask's onPostExecute method*/
    private class DownloadWebpageTask extends AsyncTask<String, Void, String>
    {
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try
            {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(String result)
        {

            definition.setText(result);
        }
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the web page content as a InputStream, which it returns as
    // a string.

    private String downloadUrl(String myurl) throws IOException
    {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }
        finally
        {
            if (is != null) {
                is.close();
            }
        }
    }
    // Reads an InputStream and converts it to a String.
    // Reads an InputStream and converts it to a String.
    private String readIt(InputStream is)
    {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append('\n');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


    /** Called when the user touches the button */
    public void startHangman(View view)
    {
        intent = new Intent(getBaseContext(), PlayingHangman.class);


        if (gameStatus == 1)
        {
            // If user has won they can keep incrementing their score
            intent.putExtra("score", userScore);
        }
        else
        {
            // If user has lost we reset the score back to zero
            intent.putExtra("score", 0);
        }
        intent.putExtra("userName", userName);
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
