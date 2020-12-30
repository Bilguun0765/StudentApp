package com.example.studentapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class RingtonePlayingservice extends Service {
    MediaPlayer media_song;
    int startId;
    Boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand (Intent intent, int  flags , int startId) {

        Log.e("In the servie", "start command" + startId + ":" + intent);

        String state = intent.getExtras().getString("extra");
        Integer sound_choice = intent.getExtras().getInt("sound_choice");

        Log.e("Ringtone state:extra is", state);
        Log.e("Sound choice is", sound_choice.toString());

        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);

        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0, intent_main_activity, 0);

        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Clickit")
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();

        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("start id is", state);
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1) {
            Log.e("There is no music", "and you want start");


            this.isRunning = true;
            this.startId = 0;

            notify_manager.notify(0, notification_popup);


            if (sound_choice == 0) {

                int minumum_number = 1;
                int maximum_number = 5;

                Random random_number = new Random();
                int sound_number = random_number.nextInt(maximum_number + minumum_number);
                Log.e("Random number is", String.valueOf(sound_number));

                if (sound_choice ==1 ) {
                    media_song = MediaPlayer.create(this, R.raw.bird);
                    media_song.start();}
                else if (sound_number == 2) {
                    media_song = MediaPlayer.create(this, R.raw.clock);
                    media_song.start();}
                else if (sound_number == 3) {
                    media_song = MediaPlayer.create(this, R.raw.danger);
                    media_song.start();}
                else if (sound_number == 4) {
                    media_song = MediaPlayer.create(this, R.raw.irritating);
                    media_song.start();}
                else if (sound_number == 5) {
                    media_song = MediaPlayer.create(this, R.raw.song);
                    media_song.start();}
                else {
                    media_song = MediaPlayer.create(this, R.raw.song);
                    media_song.start();}

            } else if (sound_choice == 1) {
                media_song = MediaPlayer.create(this, R.raw.bird);
                media_song.start();
            } else if (sound_choice == 2) {
                media_song = MediaPlayer.create(this, R.raw.clock);
                media_song.start();
            } else if (sound_choice == 3) {
                media_song = MediaPlayer.create(this, R.raw.danger);
                media_song.start();
            } else if (sound_choice == 4) {
                media_song = MediaPlayer.create(this, R.raw.irritating);
                media_song.start();
            } else if (sound_choice == 5) {
                media_song = MediaPlayer.create(this, R.raw.song);
                media_song.start();
            } else {
                media_song = MediaPlayer.create(this, R.raw.song);
                media_song.start();
            }
            } else if (!this.isRunning && startId == 0) {
                Log.e("There is music", "and you want end");

                media_song.stop();
                media_song.reset();

                this.isRunning = false;
                this.startId = 0;
            } else if (!this.isRunning && startId == 0) {
                Log.e("There is no music", "and you want end");

                this.isRunning = false;
                this.startId = 0;
            } else if (!this.isRunning && startId == 1) {
                Log.e("There is music", "and you want start");

                this.isRunning = true;
                this.startId = 1;
            } else {
                Log.e("else", "impossible");
            }

            return START_NOT_STICKY;
        }

        public void onDestroy() {
            Log.e("Destroy called", "closed");

            super.onDestroy();
            this.isRunning = false;
        }

}
