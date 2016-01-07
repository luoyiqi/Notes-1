package com.thevarunshah.backend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thevarunshah.classes.Checklist;
import com.thevarunshah.classes.ListNote;
import com.thevarunshah.classes.Note;
import com.thevarunshah.classes.Reminder;
import com.thevarunshah.classes.TextNote;
import com.thevarunshah.notes.HomeScreen;
import com.thevarunshah.notes.R;
import com.thevarunshah.notes.TextNoteView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{

    private List<Note> items;

    public NoteAdapter(List<Note> items) {
        this.items = items;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_textview, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        viewHolder.txtViewTitle.setText(items.get(position).getName());
        Object note = items.get(position);
        if(note instanceof TextNote){
            viewHolder.txtViewType.setText("Text");
        }
        else if(note instanceof ListNote){
            viewHolder.txtViewType.setText("List");
        }
        else if(note instanceof Checklist){
            viewHolder.txtViewType.setText("Checklist");
        }
        else if(note instanceof Reminder){
            viewHolder.txtViewType.setText("Reminder");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;
        public TextView txtViewType;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.notes_list_textview);
            txtViewType = (TextView) itemLayoutView.findViewById(R.id.notes_list_type_textview);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Note getItem(int position){
        return items.get(position);
    }

    public void add(Note item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void remove(Note item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }
}
