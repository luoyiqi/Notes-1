package com.thevarunshah.backend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thevarunshah.classes.Note;
import com.thevarunshah.notes.R;

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
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTitle;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.notes_list_textview);
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
