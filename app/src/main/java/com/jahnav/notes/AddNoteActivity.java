package com.jahnav.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jahnav.notes.entites.Note;

public class AddNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button save;
    NoteViewModel noteViewModel;

    int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        title = findViewById(R.id.title_edit_text);
        description = findViewById(R.id.description_edit_text);
        save = findViewById(R.id.save_button);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        Intent intent = getIntent();
        if(intent!=null && intent.getExtras()!=null){
            if(intent.getStringExtra(MainActivity.TYPE).equals("edit")){
                title.setText(intent.getStringExtra("noteTitle"));
                description.setText(intent.getStringExtra("noteDescription"));
                noteId = intent.getIntExtra("noteId", -1);
            }
        }

        save.setOnClickListener(view -> {
            String noteTitle = title.getText().toString();
            String noteDescription = description.getText().toString();
            if(intent!=null && intent.getExtras()!=null &&  intent.getStringExtra(MainActivity.TYPE).equals("edit")){
                if(!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteDescription)){
                    Note updatedNote = new Note(noteTitle, noteDescription);
                    updatedNote.id = noteId;
                    noteViewModel.update(updatedNote);
                }
            }
            else{
                if(!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteDescription)){
                    noteViewModel.insert(new Note(noteTitle, noteDescription));
                    Toast.makeText(AddNoteActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        });

    }
}