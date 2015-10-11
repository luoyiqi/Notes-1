package com.thevarunshah.notes;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thevarunshah.backend.NotesAdapter;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        RecyclerView notesList = (RecyclerView) findViewById(R.id.notes_list);
        RecyclerView.LayoutManager notesLayoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(notesLayoutManager);

        String[] sampleData = new String[20];
        for(int i = 0; i < sampleData.length; i++){
            sampleData[i] = "testing view #" + i;
        }
        RecyclerView.Adapter notesAdapter = new NotesAdapter(sampleData);
        notesList.setAdapter(notesAdapter);

        FloatingActionButton addNote = (FloatingActionButton) findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(HomeScreen.this);
                final View dialog = layoutInflater.inflate(R.layout.input_dialog, null);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreen.this, R.style.AppCompatAlertDialogStyle);
                alertDialog.setTitle("Note Details");

                alertDialog.setView(dialog);

                final EditText input = (EditText) dialog.findViewById(R.id.input_dialog_text);
                input.requestFocus();
                input.setHint("Enter Title");

                alertDialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                        String noteTitle = input.getText().toString();

                        RadioGroup noteType = (RadioGroup) dialog.findViewById(R.id.note_type);
                        int selectedTypeID = noteType.getCheckedRadioButtonId();
                        RadioButton selectedType = (RadioButton) dialog.findViewById(selectedTypeID);
                        
                        Toast.makeText(getApplicationContext(), noteTitle + " - " + selectedType.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("CANCEL", null);

                AlertDialog alert = alertDialog.create();
                alert.show();
            }
        });
    }
}
