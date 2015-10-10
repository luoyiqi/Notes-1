package com.thevarunshah.notes;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.thevarunshah.backend.NotesAdapter;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_red_light)));

        RecyclerView notesList = (RecyclerView) findViewById(R.id.notes_list);
        RecyclerView.LayoutManager notesLayoutManager = new LinearLayoutManager(this);
        notesList.setLayoutManager(notesLayoutManager);

        String[] sampleData = new String[20];
        for(int i = 0; i < sampleData.length; i++){
            sampleData[i] = "testing view #" + i;
        }
        RecyclerView.Adapter notesAdapter = new NotesAdapter(sampleData);
        notesList.setAdapter(notesAdapter);
    }
}
