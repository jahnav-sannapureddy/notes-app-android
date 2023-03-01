package com.jahnav.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jahnav.notes.entites.Note;

public class AddNoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button save;
    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        title = findViewById(R.id.title_edit_text);
        description = findViewById(R.id.description_edit_text);
        save = findViewById(R.id.save_button);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteTitle = title.getText().toString();
                String noteDescription = description.getText().toString();
                if(!TextUtils.isEmpty(noteTitle) && !TextUtils.isEmpty(noteDescription)){
                    noteViewModel.insert(new Note(noteTitle, noteDescription));
                    Toast.makeText(AddNoteActivity.this, "Added", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}