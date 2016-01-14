package com.thevarunshah.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.backend.ListNoteAdapter;
import com.thevarunshah.classes.ListNote;

import java.util.ArrayList;

public class ListNoteView extends AppCompatActivity {

    final private String TAG = "ListNoteView";
    public static ListNote ln = null;

    private ListView listView = null;
    private ListNoteAdapter listAdapter = null;
    public static boolean exiting = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.listnote_view);

        int noteId = getIntent().getBundleExtra("bundle").getInt("noteId");
        ln = (ListNote) Backend.getNote(noteId);
        getSupportActionBar().setTitle(Html.fromHtml("<b>" + ln.getName() + "</b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //obtain list view and create new list custom adapter
        listView = (ListView) findViewById(R.id.listnote_listview);
        listAdapter = new ListNoteAdapter(this, ln.getList());
        listView.setAdapter(listAdapter); //attach adapter to list view
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listnote_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = null;

        switch(item.getItemId()){
            case R.id.add_to_list:
                ln.getList().add("");
                listAdapter.notifyDataSetChanged();
                ln.updateDate();
                return true;
            case R.id.edit_title:
                //inflate layout with customized alert dialog view
                dialog = layoutInflater.inflate(R.layout.input_dialog, null);
                final AlertDialog.Builder editTitleDialogBuilder = new AlertDialog.Builder(this,
                        R.style.AppCompatAlertDialogStyle);

                //customize alert dialog and set its view
                editTitleDialogBuilder.setTitle("Edit Title");
                editTitleDialogBuilder.setIcon(R.drawable.ic_edit_black_48dp);
                editTitleDialogBuilder.setView(dialog);

                //fetch and set up edittext
                final EditText input = (EditText) dialog.findViewById(R.id.input_dialog_text);
                input.setText(ln.getName());
                input.setFocusableInTouchMode(true);
                input.requestFocus();

                //set up actions for dialog buttons
                editTitleDialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        ln.setName(input.getText().toString());
                        ln.updateDate();
                        getSupportActionBar().setTitle(Html.fromHtml("<b>" + ln.getName() + "</b>"));
                    }
                });
                editTitleDialogBuilder.setNegativeButton("CANCEL", null);

                //create and show the dialog
                AlertDialog editTitleDialog = editTitleDialogBuilder.create();
                editTitleDialog.show();

                //show keyboard
                editTitleDialog.getWindow()
                        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                return true;
            case R.id.delete_note:
                //inflate layout with customized alert dialog view
                dialog = layoutInflater.inflate(R.layout.info_dialog, null);
                final AlertDialog.Builder deleteNoteDialogBuilder = new AlertDialog.Builder(this,
                        R.style.AppCompatAlertDialogStyle);

                //customize alert dialog and set its view
                deleteNoteDialogBuilder.setTitle("Confirm Delete");
                deleteNoteDialogBuilder.setIcon(R.drawable.ic_delete_black_48dp);
                deleteNoteDialogBuilder.setView(dialog);

                //fetch textview and set its text
                final TextView message = (TextView) dialog.findViewById(R.id.infodialog_text);
                message.setText("Are you sure you want to delete this list?");

                deleteNoteDialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        Backend.getNotes().remove(ln);
                        finish();
                    }
                });
                deleteNoteDialogBuilder.setNegativeButton("CANCEL", null);

                //create and show the dialog
                AlertDialog deleteNoteDialog = deleteNoteDialogBuilder.create();
                deleteNoteDialog.show();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteEmpty(){

        View v = listView.getChildAt(ListNoteAdapter.currActive);
        if(v == null){
            return;
        }
        EditText et = (EditText) v.findViewById(R.id.listnote_bullet);
        ln.getList().set(ListNoteAdapter.currActive, et.getText().toString());

        ArrayList<Integer> removeIndices = new ArrayList<Integer>();
        for(int i = 0; i < ln.getList().size(); i++){
            if(ln.getList().get(i).equals("")){
                removeIndices.add(i);
            }
        }

        for(int i = 0; i < removeIndices.size(); i++){
            ln.getList().remove(removeIndices.get(i)-i);
        }

        if(removeIndices.size() > 0){
            ln.updateDate();
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        exiting = true;
        deleteEmpty();
        Backend.writeData(this.getApplicationContext()); //backup data
    }

    @Override
    protected void onResume() {

        super.onResume();
        exiting = false;
        if(ln == null){
            this.finish();
        }
    }
}
