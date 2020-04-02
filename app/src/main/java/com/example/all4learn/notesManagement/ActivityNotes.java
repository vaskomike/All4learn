package com.example.all4learn.notesManagement;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;


public class ActivityNotes extends AppCompatActivity {

    public static Intent createIntent(Context context) {
        return new Intent(context, ActivityNotes.class);
    }


}
