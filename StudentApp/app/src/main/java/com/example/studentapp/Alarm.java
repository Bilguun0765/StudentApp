package com.example.studentapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pendingIntent;
    int choose_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context=(this);

        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);
        update_text = (TextView) findViewById(R.id.update_text);
        final Calendar calendar = Calendar.getInstance();
        final Intent my_intent = new Intent(this.context, AlarmReceiver.class);
        Spinner spinner = (Spinner) findViewById(R.id.song_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.bilguun_arry, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }
                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarm set to:  " + hour_string + ":" + minute_string);

                my_intent.putExtra("extra", "on");

                my_intent.putExtra("song_choice", choose_sound);

                Log.e("song id is", String.valueOf(choose_sound));

                pendingIntent = pendingIntent.getBroadcast(Alarm.this, 0 ,my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
            }




        });

        Button alarm_off = (Button) findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm off");

                alarm_manager.cancel(pendingIntent);

                my_intent.putExtra("extra", "off");

                my_intent.putExtra("song_choise", choose_sound);

                sendBroadcast(my_intent);
            }
        });



    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choose_sound = (int) id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}