package com.example.all4learn.notesManagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.example.all4learn.firebaseManagement.FireStoreNoteMapper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityAddNote extends AppCompatActivity {


    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    private static TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        dateNote.setText(noteDate);

    }

    private String getUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    Calendar calendar = Calendar.getInstance();
    static final SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:yy");

    private String noteDate = format.format(calendar.getTime());

    public Note saveNote(String title, String text, String date) {
        //addNote
        title = titleInputEditText.getText().toString();
        text = textInputEditText.getText().toString();
//        addData.put(DATE, endDate);
//        addData.put(TEXT, text);
//        addData.put(TITLE, title);
        if (!title.equals("") || !text.equals("")) {
            DocumentReference reference = fireStore.collection(FireStoreNoteMapper.COLLECTION).document();
            reference.set(FireStoreNoteMapper.newNote(getUid(), title, text, noteDate));
            return new Note(reference.getId(), getUid(), title, text, date);
        } else Toast.makeText(ActivityAddNote.this, "Empty note", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveNote(titleInputEditText.toString(), textInputEditText.toString(), noteDate);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
