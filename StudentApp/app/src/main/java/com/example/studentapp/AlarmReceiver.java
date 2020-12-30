package com.example.studentapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {


    public void onReceive(Context context, Intent intent) {

        Log.e("This is receiver", "works");

        String get_your_string = intent.getExtras().getString("extra");
        Log.e("What is key", get_your_string);
        Integer get_your_song_choice = intent.getExtras().getInt("song_choice");
        Log.e("what song choice", get_your_song_choice.toString());

        Intent service_intent = new Intent(context,RingtonePlayingservice.class);

        service_intent.putExtra("extra", get_your_string);
        service_intent.putExtra("Sound_choice", get_your_song_choice);

        context.startService(service_intent);
    }
}
