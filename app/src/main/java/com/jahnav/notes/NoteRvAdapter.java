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

    private Context context;
    private ArrayList<Note> allNotes = new ArrayList<>();
    NoteClickInterface noteClickInterface;

    public NoteRvAdapter(Context context, NoteClickInterface noteClickInterface ){
        this.context = context;
        this.noteClickInterface = noteClickInterface;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView delete;
        TextView updateTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            description = itemView.findViewById(R.id.description_tv);
            delete = itemView.findViewById(R.id.imageView);
            updateTime = itemView.findViewById(R.id.updatedTime);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteClickInterface.onItemClick(allNotes.get(holder.getAdapterPosition()));
            }
        });
        holder.title.setText(allNotes.get(position).getTitle());
        holder.description.setText(allNotes.get(position).getDescription());
        holder.updateTime.setText(allNotes.get(position).getUpdatedTime());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteClickInterface.onDeleteIconClick(allNotes.get(holder.getAdapterPosition()));
            }
        });
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


    interface NoteClickInterface{
        void onDeleteIconClick(Note note);
        void onItemClick(Note note);
    }
}
