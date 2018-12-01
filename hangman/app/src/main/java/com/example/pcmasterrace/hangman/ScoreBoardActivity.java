package com.example.pcmasterrace.hangman;


import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.sql.SQLException;

//If use ListActivity will crash when set content
public class ScoreBoardActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        DataManager db = new DataManager(this);

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        long val = db.insertScore("Michael", "300");
//        long val2 = db.insertScore("David", "100");
//        long val3 = db.insertScore("John", "0");
//        long val4 = db.insertScore("Jimmy", "700");
//        long val5 = db.insertScore("Lacoste", "500");

        Cursor dbCursor = db.getAllScores();
        dbCursor.moveToFirst();

        String[] columns = {"username", "score"};
        int[] rowIDs = {R.id.username, R.id.score};


        //Note I use row layout here
        SimpleCursorAdapter mAdapter = (new SimpleCursorAdapter(this,
                R.layout.row, dbCursor, columns, rowIDs, 0));

        setListAdapter(mAdapter);

        db.close();
    }

    public void prevScreen()
    {
        finish();
    }
}