package com.example.all4learn.notesManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.all4learn.R;

import java.util.List;


public class ActivityNotes extends AppCompatActivity implements ActivityLoadNotes.Listener, OnItemClickListener<Note> {

    private NotesAdapter adapter;


    public static Intent createIntent(Context context) {
        return new Intent(context, ActivityNotes.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new NotesAdapter(getLayoutInflater(), this);

        RecyclerView recyclerView = findViewById(R.id.notes);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(callback).attachToRecyclerView(recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddNote.class);
            startActivity(intent);
        });
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        adapter.submitList(notes);
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onItemClicked(Note item) {
        startActivity(EditNoteActivity.createIntent(this, item));
    }

}
