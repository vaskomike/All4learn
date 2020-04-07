package com.example.all4learn.notesManagement;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.DATE;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TEXT;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TITLE;

public class LoadNoteActivity extends AppCompatActivity {

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();
    DocumentReference ref = fireStore.collection("notes").document(uid);

    private TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        loadNote();
    }

    public void loadNote() {
        ref.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            titleInputEditText.setText(documentSnapshot.getString(TITLE));
                            textInputEditText.setText(documentSnapshot.getString(TEXT));
                            dateNote.setText(DATE);

                        } else
                            Toast.makeText(LoadNoteActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoadNoteActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
