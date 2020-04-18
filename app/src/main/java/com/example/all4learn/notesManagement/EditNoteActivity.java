package com.example.all4learn.notesManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.COLLECTION;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.DATE;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TEXT;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TITLE;


public class EditNoteActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "note";

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    Calendar calendar = Calendar.getInstance();
    static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
    private String noteDate = format.format(calendar.getTime());

    public static Intent createIntent(Context context, Note item) {
        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra(EXTRA_NOTE, item);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);

        Note note = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (note == null) {
            finish();
            return;
        }
        titleInputEditText.setText(note.getTitle());
        textInputEditText.setText(note.getText());
        dateNote.setText(note.getDate());

        DocumentReference edit = fireStore.collection(COLLECTION).document(note.getId());
        Map<String, Object> data = new HashMap<>();
        data.put(TITLE, titleInputEditText.getText().toString());
        data.put(TEXT, textInputEditText.getText().toString());
        data.put(DATE, dateNote.getText());
        edit.set(
                data
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
