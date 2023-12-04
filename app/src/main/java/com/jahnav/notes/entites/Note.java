package com.jahnav.notes.entites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "updatedTime")
    private String updatedTime;
    public Note(@NonNull String title, String description, String updatedTime) {
        this.title = title;
        this.description = description;
        this.updatedTime = updatedTime;
    }
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
