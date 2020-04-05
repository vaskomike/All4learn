package com.example.all4learn.notesManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.all4learn.R;

import java.util.List;


public class ActivityNotes extends AppCompatActivity implements ActivityLoadNotes.Listener, OnItemClickListener<Note> {


    private static final int REQUEST_CODE_ADD_NOTE = 1;

    private ActivityLoadNotes loadNotes = new ActivityLoadNotes(this);

    private SwipeRefreshLayout refreshLayout;

    private NotesAdapter adapter;

    public static Intent createIntent(Context context) {
        return new Intent(context, ActivityNotes.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        adapter = new NotesAdapter(getLayoutInflater(), this);

        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(loadNotes::loadNotes);

        RecyclerView recyclerView = findViewById(R.id.notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        );

        findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddNote.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                loadNotes.loadNotes();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        refreshLayout.setRefreshing(false);
        adapter.submitList(notes);
    }

    @Override
    public void onItemClicked(Note item) {

    }

}
