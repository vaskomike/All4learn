package com.example.all4learn.notesManagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.COLLECTION;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.DATE;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TEXT;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TITLE;

public class ActivityAddNote extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private static TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        dateNote.setText(endDate);
    }

    Calendar calendar = Calendar.getInstance();
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String endDate = format.format(calendar.getTime());

    public void saveNote() {
        Map<String, Object> addData = new HashMap<>();
        String title = titleInputEditText.getText().toString();
        String text = textInputEditText.getText().toString();
        addData.put(DATE, endDate);
        addData.put(TEXT, text);
        addData.put(TITLE, title);
        if (!title.equals("") || !text.equals("")) {
            fireStore.collection(COLLECTION).document().set(addData);
        } else Toast.makeText(ActivityAddNote.this, "Empty note", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveNote();
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //todo load note
    }

}
