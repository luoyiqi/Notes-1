package com.thevarunshah.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.backend.NoteAdapter;
import com.thevarunshah.backend.RecyclerItemClickListener;
import com.thevarunshah.classes.Checklist;
import com.thevarunshah.classes.ListNote;
import com.thevarunshah.classes.Reminder;
import com.thevarunshah.classes.TextNote;

public class HomeScreen extends AppCompatActivity {

    final private String TAG = "HomeScreen";
    private NoteAdapter notesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        getSupportActionBar().setTitle(Html.fromHtml("<b>"+getSupportActionBar().getTitle()+"</b>"));

        RecyclerView notesList = (RecyclerView) findViewById(R.id.notes_list);
        RecyclerView.LayoutManager notesLayoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(notesLayoutManager);
        notesList.setItemAnimator(new DefaultItemAnimator());

        notesAdapter = new NoteAdapter(Backend.getNotes());
        notesList.setAdapter(notesAdapter);

        notesList.addOnItemTouchListener(
                new RecyclerItemClickListener(HomeScreen.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Object note = notesAdapter.getItem(position);
                        if(note instanceof TextNote){
                            TextNote tn = (TextNote) note;
                            Intent i = new Intent(HomeScreen.this, TextNoteView.class);
                            Bundle extra = new Bundle();
                            extra.putInt("noteId", tn.getId());
                            i.putExtra("bundle", extra);
                            startActivity(i);
                        }
                        else if(note instanceof ListNote){
                            ListNote ln = (ListNote) note;
                            Snackbar infoBar = Snackbar.make(findViewById(R.id.coordLayout), ln.getName(),
                                    Snackbar.LENGTH_SHORT);
                            infoBar.show();
                        }
                        else if(note instanceof Checklist){
                            Checklist cl = (Checklist) note;
                            Snackbar infoBar = Snackbar.make(findViewById(R.id.coordLayout), cl.getName(),
                                    Snackbar.LENGTH_SHORT);
                            infoBar.show();
                        }
                        else if(note instanceof Reminder){
                            Reminder r = (Reminder) note;
                            Snackbar infoBar = Snackbar.make(findViewById(R.id.coordLayout), r.getName(),
                                    Snackbar.LENGTH_SHORT);
                            infoBar.show();
                        }
                    }
                })
        );

        FloatingActionButton addNote = (FloatingActionButton) findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(HomeScreen.this);
                final View dialog = layoutInflater.inflate(R.layout.new_note_dialog, null);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this,
                        R.style.AppCompatAlertDialogStyle);
                alertDialog.setTitle("New Note");

                final RadioButton tnButton = (RadioButton) dialog.findViewById(R.id.text_note);
                final RadioButton lnButton = (RadioButton) dialog.findViewById(R.id.list_note);
                final RadioButton clButton = (RadioButton) dialog.findViewById(R.id.checklist);
                final RadioButton rButton = (RadioButton) dialog.findViewById(R.id.reminder);
                tnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(!isChecked){
                            return;
                        }
                        lnButton.setChecked(false);
                        clButton.setChecked(false);
                        rButton.setChecked(false);
                    }
                });
                lnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(!isChecked){
                            return;
                        }
                        tnButton.setChecked(false);
                        clButton.setChecked(false);
                        rButton.setChecked(false);
                    }
                });
                clButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(!isChecked){
                            return;
                        }
                        tnButton.setChecked(false);
                        lnButton.setChecked(false);
                        rButton.setChecked(false);
                    }
                });
                rButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (!isChecked) {
                            return;
                        }
                        tnButton.setChecked(false);
                        lnButton.setChecked(false);
                        clButton.setChecked(false);
                    }
                });

                alertDialog.setView(dialog);

                final EditText input = (EditText) dialog.findViewById(R.id.input_dialog_text);
                input.setHint("Enter Title");
                input.setFocusableInTouchMode(true);
                input.requestFocus();

                alertDialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        String noteTitle = input.getText().toString();

                        if(tnButton.isChecked()){
                            TextNote tn = new TextNote(Backend.getNextID(), noteTitle);
                            notesAdapter.add(tn);
                            Intent i = new Intent(HomeScreen.this, TextNoteView.class);
                            Bundle extra = new Bundle();
                            extra.putInt("noteId", tn.getId());
                            i.putExtra("bundle", extra);
                            startActivity(i);
                        }
                        else if(lnButton.isChecked()){
                            ListNote ln = new ListNote(Backend.getNextID(), noteTitle);
                            notesAdapter.add(ln);
                        }
                        else if(clButton.isChecked()){
                            Checklist cl = new Checklist(Backend.getNextID(), noteTitle);
                            notesAdapter.add(cl);
                        }
                        else if(rButton.isChecked()){
                            Reminder r = new Reminder(Backend.getNextID(), noteTitle);
                            notesAdapter.add(r);
                        }
                    }
                });
                alertDialog.setNegativeButton("CANCEL", null);

                AlertDialog alert = alertDialog.create();
                alert.show();

                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
    }

    @Override
    protected void onPause() {

        super.onPause();
        Backend.writeData(this.getApplicationContext()); //backup data
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(Backend.getNotes().isEmpty()){
            Backend.readData(this.getApplicationContext()); //read data from backup
        }
        notesAdapter.notifyDataSetChanged();
    }
}
