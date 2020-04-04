package com.example.all4learn.notesManagement;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.DATE;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TEXT;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TITLE;

public class ActivityAddNote extends AppCompatActivity {
    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    private TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        saveNote();
        setResult(Activity.RESULT_OK);
        finish();
    }

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public Date getDateFromString(String dateToSaved) {

        try {
            Date date = format.parse(dateToSaved);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    private void saveNote() {
        Map<String, Object> addData = new HashMap<>();
        String title = titleInputEditText.getText().toString();
        String text = textInputEditText.getText().toString();
        addData.put(TITLE, title);
        addData.put(TEXT, text);
        addData.put(DATE, getDateFromString("2017-10-15T09:27:37Z"));

        fireStore.collection("notes").document().set(addData);

//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//           @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    String title = documentSnapshot.getString("title");
//                    String text = documentSnapshot.getString("text");
//                } else {
//
//
//                }
//            }
//
//        });
    }
}
