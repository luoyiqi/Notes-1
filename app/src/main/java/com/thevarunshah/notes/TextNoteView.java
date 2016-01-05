package com.thevarunshah.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.classes.TextNote;

public class TextNoteView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.textnote_view);

        int noteId = getIntent().getBundleExtra("bundle").getInt("noteId");
        TextNote tn = (TextNote) Backend.getNote(noteId);
        String actionBarTitle = tn.getName();
        if(tn.getName().length() > 25){
            actionBarTitle = tn.getName().substring(0, 25) + "...";
        }
        getSupportActionBar().setTitle(actionBarTitle);

        EditText input = (EditText) findViewById(R.id.textnote_text);
        input.setHint("Enter notes...");
    }
}
