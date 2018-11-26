package com.example.pcmasterrace.hangman;

import java.sql.Blob;

public class Player {
    String username;
    int score;
    Blob picture;

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setPicture(Blob picture)
    {
        this.picture = picture;
    }

    public String getUsername()
    {
        if (username != null)
        {
            return username;
        }
        else {
            return "";
        }
    }

    public int getScore()
    {
        return score;
    }

    public Blob getPicture()
    {
        return picture;
    }




}
