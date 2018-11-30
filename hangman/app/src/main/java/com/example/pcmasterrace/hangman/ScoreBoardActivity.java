package com.example.pcmasterrace.hangman;


import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

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
//        long val2 = db.insertScore("David", "40");
//        long val3 = db.insertScore("John", "20");

        Cursor dbCursor = db.getAllScores();
        dbCursor.moveToFirst();

        String[] columns = {"_id", "username", "score"};
        int[] rowIDs = {R.id.scoreid, R.id.username};

        //Note I use row layout here
        SimpleCursorAdapter mAdapter = (new SimpleCursorAdapter(this,
                R.layout.row, dbCursor, columns, rowIDs));
        setListAdapter(mAdapter);
        db.close();
    }

    public void prevScreen()
    {
        finish();
    }
}