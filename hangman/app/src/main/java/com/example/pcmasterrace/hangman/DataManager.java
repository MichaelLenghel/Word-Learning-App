package com.example.pcmasterrace.hangman;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class DataManager
{
    // database columns
    private static final String ROWID 	    = "_id";
    private static final String TASK_NAME 	= "task_name";
    private static final String TASK_DESCRIPTION = "task_desc";
    private static final String DATABASE_NAME 	= "TaskList";
    private static final String DATABASE_TABLE 	= "Tasks";
    private static final int DATABASE_VERSION 	= 1;

    // SQL statement to create the database
    private static final String DATABASE_CREATE =
            "create table Tasks (_id integer primary key autoincrement, " +
                    "task_name text not null," +
                    "task_desc text not null);";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    // Constructor
    public DataManager(Context ctx)
    {
        //
        this.context 	= ctx;
        DBHelper 		= new DatabaseHelper(context);
    }

    public DataManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertTask(String taskName, String taskDesc)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(TASK_NAME, taskName);
        initialValues.put(TASK_DESCRIPTION, taskDesc);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTask(long rowId)
    {
        //
        return db.delete(DATABASE_TABLE, ROWID +
                "=" + rowId, null) > 0;
    }

    public Cursor getAllTasks()
    {
        return db.query(DATABASE_TABLE, new String[]
                        {
                                ROWID,
                                TASK_NAME,
                                TASK_DESCRIPTION,
                        },
                null, null, null, null, null);
    }

    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]
                                {
                                        ROWID,
                                        TASK_NAME,
                                        TASK_DESCRIPTION,
                                },
                        ROWID + "=" + rowId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public boolean updateTask(long rowId, String firstName, String surname)
    {
        ContentValues args = new ContentValues();
        args.put(TASK_NAME, firstName);
        args.put(TASK_DESCRIPTION, surname);
        return db.update(DATABASE_TABLE, args,
                ROWID + "=" + rowId, null) > 0;
    }

    // ///////////////////////nested dB helper class ///////////////////////////////////////
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        //
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db)
        {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            // dB structure change..

        }
    }
    //////////////////////////// end nested dB helper class //////////////////////////////////////

}