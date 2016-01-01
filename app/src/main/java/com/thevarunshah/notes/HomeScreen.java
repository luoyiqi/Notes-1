package com.thevarunshah.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.thevarunshah.classes.Checklist;
import com.thevarunshah.backend.Backend;
import com.thevarunshah.classes.ListNote;
import com.thevarunshah.backend.NoteAdapter;
import com.thevarunshah.backend.RecyclerItemClickListener;
import com.thevarunshah.classes.Reminder;
import com.thevarunshah.classes.TextNote;

public class HomeScreen extends AppCompatActivity {

    final private String TAG = "HomeScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        RecyclerView notesList = (RecyclerView) findViewById(R.id.notes_list);
        RecyclerView.LayoutManager notesLayoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(notesLayoutManager);
        notesList.setItemAnimator(new DefaultItemAnimator());

        final NoteAdapter notesAdapter = new NoteAdapter(Backend.getNotes());
        notesList.setAdapter(notesAdapter);

        notesList.addOnItemTouchListener(
            new RecyclerItemClickListener(HomeScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Object note = notesAdapter.getItem(position);
                    if(note instanceof TextNote){
                        TextNote tn = (TextNote) note;
                        Log.i(TAG, tn.getName());
                    }
                    else if(note instanceof ListNote){
                        ListNote ln = (ListNote) note;
                        Log.i(TAG, ln.getName());
                    }
                    else if(note instanceof Checklist){
                        Checklist cl = (Checklist) note;
                        Log.i(TAG, cl.getName());
                    }
                    else if(note instanceof Reminder){
                        Reminder r = (Reminder) note;
                        Log.i(TAG, r.getName());
                    }
                }
            })
        );

        FloatingActionButton addNote = (FloatingActionButton) findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(HomeScreen.this);
                final View dialog = layoutInflater.inflate(R.layout.input_dialog, null);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this,
                        R.style.AppCompatAlertDialogStyle);
                alertDialog.setTitle("New Note");

                alertDialog.setView(dialog);

                final EditText input = (EditText) dialog.findViewById(R.id.input_dialog_text);
                input.requestFocus();
                input.setHint("Enter Title");

                alertDialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        String noteTitle = input.getText().toString();

                        RadioGroup noteType = (RadioGroup) dialog.findViewById(R.id.note_type);
                        int selectedTypeID = noteType.getCheckedRadioButtonId();
                        switch (selectedTypeID) {
                            case R.id.text_note:
                                TextNote tn = new TextNote(Backend.getNextID(), noteTitle);
                                notesAdapter.add(tn);
                                break;
                            case R.id.list_note:
                                ListNote ln = new ListNote(Backend.getNextID(), noteTitle);
                                notesAdapter.add(ln);
                                break;
                            case R.id.checklist:
                                Checklist cl = new Checklist(Backend.getNextID(), noteTitle);
                                notesAdapter.add(cl);
                                break;
                            case R.id.reminder:
                                Reminder r = new Reminder(Backend.getNextID(), noteTitle);
                                notesAdapter.add(r);
                                break;
                        }
                    }
                });
                alertDialog.setNegativeButton("CANCEL", null);

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
    }
}
