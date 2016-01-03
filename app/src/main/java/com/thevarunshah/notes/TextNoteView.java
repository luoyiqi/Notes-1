package com.thevarunshah.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class TextNoteView extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.textnote_view);

        EditText input = (EditText) findViewById(R.id.textnote_text);
        input.setHint("Enter notes...");
    }
}
