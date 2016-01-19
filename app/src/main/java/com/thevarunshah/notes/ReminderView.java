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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.thevarunshah.backend.Backend;
import com.thevarunshah.classes.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReminderView extends AppCompatActivity {

    final private String TAG = "ReminderView";
    public static Reminder r = null;
    private TextView dateTV;
    private TextView timeTV;
    private TextView reminderTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_view);

        int noteId = getIntent().getBundleExtra("bundle").getInt("noteId");
        r = (Reminder) Backend.getNote(noteId);
        getSupportActionBar().setTitle(Html.fromHtml("<b>" + r.getName() + "</b>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTV = (TextView) findViewById(R.id.reminder_date);
        dateTV.setText((new SimpleDateFormat("MM/dd/yy")).format(r.getDue()));
        timeTV = (TextView) findViewById(R.id.reminder_time);
        timeTV.setText((new SimpleDateFormat("h:mm a")).format(r.getDue()));
        reminderTV = (TextView) findViewById(R.id.reminder_reminder);
        reminderTV.setText(r.getReminder() + " minutes before");

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(ReminderView.this);
                View dialog = null;
                //inflate layout with customized alert dialog view
                dialog = layoutInflater.inflate(R.layout.date_dialog, null);
                final AlertDialog.Builder dateDialogBuilder = new AlertDialog.Builder(ReminderView.this,
                        R.style.AppCompatAlertDialogStyle);
                dateDialogBuilder.setView(dialog);

                //fetch and set up date
                final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(Calendar.getInstance().getTimeInMillis()-1000);
                datePicker.updateDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(r.getDue())),
                        Integer.parseInt((new SimpleDateFormat("MM")).format(r.getDue())),
                        Integer.parseInt((new SimpleDateFormat("dd")).format(r.getDue())));
                datePicker.setCalendarViewShown(false);

                //set up actions for dialog buttons
                dateDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        r.setDue(new Date(datePicker.getCalendarView().getDate()));
                        dateTV.setText((new SimpleDateFormat("MM/dd/yy")).format(r.getDue()));
                    }
                });
                dateDialogBuilder.setNegativeButton("CANCEL", null);

                //create and show the dialog
                AlertDialog dateDialog = dateDialogBuilder.create();
                dateDialog.show();
            }
        });
        timeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(ReminderView.this);
                View dialog = null;
                //inflate layout with customized alert dialog view
                dialog = layoutInflater.inflate(R.layout.time_dialog, null);
                final AlertDialog.Builder timeDialogBuilder = new AlertDialog.Builder(ReminderView.this,
                        R.style.AppCompatAlertDialogStyle);
                timeDialogBuilder.setView(dialog);

                //fetch and set up time
                final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.time_picker);

                //set up actions for dialog buttons
                timeDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {

                    }
                });
                timeDialogBuilder.setNegativeButton("CANCEL", null);

                //create and show the dialog
                AlertDialog timeDialog = timeDialogBuilder.create();
                timeDialog.show();
            }
        });
        reminderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reminder_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View dialog = null;

        switch(item.getItemId()){
            case R.id.save_reminder:
                r.setDue(r.getDue());
                r.setReminder(Integer.parseInt(reminderTV.getText().toString().substring(0, reminderTV.getText().toString().indexOf(' '))));
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
                input.setText(r.getName());
                input.setFocusableInTouchMode(true);
                input.requestFocus();

                //set up actions for dialog buttons
                editTitleDialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        r.setName(input.getText().toString());
                        r.updateDate();
                        getSupportActionBar().setTitle(Html.fromHtml("<b>" + r.getName() + "</b>"));
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
            case R.id.delete_reminder:
                //inflate layout with customized alert dialog view
                dialog = layoutInflater.inflate(R.layout.info_dialog, null);
                final AlertDialog.Builder deleteNoteDialogBuilder = new AlertDialog.Builder(this,
                        R.style.AppCompatAlertDialogStyle);

                //customize alert dialog and set its view
                deleteNoteDialogBuilder.setTitle("Confirm Delete");
                deleteNoteDialogBuilder.setIcon(R.drawable.ic_delete_black_48dp);
                deleteNoteDialogBuilder.setView(dialog);

                //fetch textview and set its text
                final TextView message2 = (TextView) dialog.findViewById(R.id.infodialog_text);
                message2.setText("Are you sure you want to delete this reminder?");

                deleteNoteDialogBuilder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                        Backend.getNotes().remove(r);
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

    @Override
    protected void onPause() {

        super.onPause();
        Backend.writeData(this.getApplicationContext()); //backup data
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(r == null){
            this.finish();
        }
    }
}
