package com.thevarunshah.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.classes.TextNote;

public class TextNoteView extends AppCompatActivity {

    private Menu menu = null;
    private EditText et = null;
    private TextView tv = null;
    private TextNote tn = null;
    private boolean edit = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.textnote_view);

        int noteId = getIntent().getBundleExtra("bundle").getInt("noteId");
        tn = (TextNote) Backend.getNote(noteId);
        getSupportActionBar().setTitle(Html.fromHtml("<b>"+tn.getName()+"</b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et = (EditText) findViewById(R.id.textnote_edittext);
        tv = (TextView) findViewById(R.id.textnote_textview);
        if(tn.getNotes().equals("")){
            ((ViewGroup)tv.getParent()).removeView(tv);
            edit = false;
        }
        else{
            ((ViewGroup)et.getParent()).removeView(et);
            tv.setText(tn.getNotes());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.textnote_menu, menu);
        if(edit){
            menu.findItem(R.id.save_note).setVisible(false);
        }
        else{
            menu.findItem(R.id.edit_note).setVisible(false);
        }
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.edit_note:
                ((ViewGroup)tv.getParent()).addView(et);
                et.setText(tv.getText());
                ((ViewGroup)tv.getParent()).removeView(tv);
                menu.findItem(R.id.edit_note).setVisible(false);
                menu.findItem(R.id.save_note).setVisible(true);
                return true;
            case R.id.save_note:
                ((ViewGroup)et.getParent()).addView(tv);
                tv.setText(et.getText().toString());
                ((ViewGroup)et.getParent()).removeView(et);
                menu.findItem(R.id.save_note).setVisible(false);
                menu.findItem(R.id.edit_note).setVisible(true);
                return true;
            case android.R.id.home:
                if(menu.findItem(R.id.edit_note).isVisible()){
                    tn.setNotes(tv.getText().toString());
                }
                else{
                    tn.setNotes(et.getText().toString());
                }
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        super.onPause();
        if(menu.findItem(R.id.edit_note).isVisible()){
            tn.setNotes(tv.getText().toString());
        }
        else{
            tn.setNotes(et.getText().toString());
        }
    }
}
