package com.jahnav.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jahnav.notes.entites.Note;


public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    NoteViewModel noteViewModel;
    public static final String TYPE ="NOTE_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.addfloatingActionButton);

        NoteRvAdapter noteRvAdapter = new NoteRvAdapter(this, new NoteRvAdapter.NoteClickInterface() {
            @Override
            public void onDeleteIconClick(Note note) {
                noteViewModel.delete(note);
            }

            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(TYPE, "edit");
                intent.putExtra("noteTitle", note.getTitle());
                intent.putExtra("noteDescription", note.getDescription());
                intent.putExtra("noteId", note.id);
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteRvAdapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, noteRvAdapter::updateList);

        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_menuitem:
                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}