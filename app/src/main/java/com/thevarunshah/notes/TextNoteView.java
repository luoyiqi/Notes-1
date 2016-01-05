package com.thevarunshah.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.classes.TextNote;

public class TextNoteView extends AppCompatActivity {

    Menu menu = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.textnote_view);

        int noteId = getIntent().getBundleExtra("bundle").getInt("noteId");
        TextNote tn = (TextNote) Backend.getNote(noteId);
        getSupportActionBar().setTitle(Html.fromHtml("<b>"+tn.getName()+"</b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText input = (EditText) findViewById(R.id.textnote_text);
        input.setHint("Enter notes...");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.textnote_menu, menu);
        menu.findItem(R.id.save_note).setVisible(false);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.edit_note:
                menu.findItem(R.id.edit_note).setVisible(false);
                menu.findItem(R.id.save_note).setVisible(true);
                return true;
            case R.id.save_note:
                menu.findItem(R.id.save_note).setVisible(false);
                menu.findItem(R.id.edit_note).setVisible(true);
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
