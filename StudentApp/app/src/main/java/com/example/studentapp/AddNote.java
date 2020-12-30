package com.example.studentapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle;
    EditText noteDetails;
    Calendar c;
    String todaysDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Note");

        noteTitle = findViewById(R.id.noteTitle);
        noteDetails = findViewById(R.id.noteDetails);

        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() !=0) {
                    getSupportActionBar().setTitle(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        c = Calendar.getInstance();
        todaysDate = c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
        currentTime = pad(c.get(Calendar.HOUR))+":"+pad(c.get(Calendar.MINUTE));

        Log.d("calendar", "date a time" + todaysDate + "and" + currentTime);
    }

    private String pad(int i) {
        if(i < 10)
            return "0"+i;
        return String.valueOf(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            Toast.makeText(this, "Note not seved", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.save) {
            Note note = new Note(noteTitle.getText().toString(),noteDetails,getText().toString(),todaysDate,currentTime);
            NoteDatabase db = new NoteDatabase(this);
            db.addNote(note);
            onBackPressed();
            Toast.makeText(this, "save btn", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}