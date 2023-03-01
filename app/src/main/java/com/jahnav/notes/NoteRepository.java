package com.jahnav.notes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.jahnav.notes.database.NoteRoomDatabase;
import com.jahnav.notes.entites.Note;
import com.jahnav.notes.entites.NoteDao;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;


    private LiveData<List<Note>> allNotes;
    NoteRepository(Application application){
        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(application);

        //initialize Dao
        noteDao = db.getNoteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });

    }

    public void deleteAll(){
        NoteRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAll();
            }
        });
    }

    public void delete(Note note){
        NoteRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }


    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
