package com.jahnav.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jahnav.notes.entites.Note;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NoteRvAdapter noteRvAdapter;
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

        noteRvAdapter = new NoteRvAdapter(this, new NoteRvAdapter.NoteClickInterface() {
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

        MenuItem searchItem = menu.findItem(R.id.search_menu_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query, true);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, false);
                return false;
            }
        });

        return true;
    }


    public void filter(String text, Boolean onSubmit){
        ArrayList<Note> filteredList = new ArrayList<>();
        noteViewModel.getAllNotes().observe(this, notes -> {
            for(Note note: notes){
                if(note.getTitle().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(note);
                }
            }
        });
        if(filteredList.isEmpty() && onSubmit){
            Toast.makeText(this, "Not found", Toast.LENGTH_SHORT).show();
        }
        else{
            noteRvAdapter.updateList(filteredList);
        }

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