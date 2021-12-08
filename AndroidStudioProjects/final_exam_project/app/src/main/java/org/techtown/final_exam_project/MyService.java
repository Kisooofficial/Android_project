package org.techtown.final_exam_project;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mediaplayer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){

        mediaplayer = MediaPlayer.create(this, R.raw.old_pop);
        mediaplayer.setLooping(false);
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        mediaplayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy(){
        mediaplayer.stop();
        super.onDestroy();
    }

}