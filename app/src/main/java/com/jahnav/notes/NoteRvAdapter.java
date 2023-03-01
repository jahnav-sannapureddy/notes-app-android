package com.jahnav.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jahnav.notes.entites.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRvAdapter extends RecyclerView.Adapter<NoteRvAdapter.ViewHolder> {

    private ArrayList<Note> allNotes = new ArrayList<>();

    public NoteRvAdapter(Context context){

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            description = itemView.findViewById(R.id.description_tv);
            delete = itemView.findViewById(R.id.imageView);
        }
    }




    @NonNull
    @Override
    public NoteRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRvAdapter.ViewHolder holder, int position) {
        holder.title.setText(allNotes.get(position).getTitle());
        holder.description.setText(allNotes.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void updateList(List<Note> newList){
        allNotes.clear();
        allNotes.addAll(newList);
        notifyDataSetChanged();
    }
}
