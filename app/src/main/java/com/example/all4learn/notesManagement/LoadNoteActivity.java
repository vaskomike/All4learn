package com.example.all4learn.notesManagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoadNoteActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    DocumentReference ref = fireStore.collection("notes").document();
    private TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
    }
}
