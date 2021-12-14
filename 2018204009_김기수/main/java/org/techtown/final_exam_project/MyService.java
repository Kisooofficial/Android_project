package org.techtown.final_exam_project;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

//백그라운드 음악 실행
public class MyService extends Service {
    MediaPlayer mediaplayer;



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        //음악 실행 위한 MediaPlater 객체 실행
        mediaplayer = MediaPlayer.create(this, R.raw.old_pop);
        mediaplayer.setLooping(false);
        super.onCreate();
    }

    //음악 켜기 버튼 누를 떄
    public int onStartCommand(Intent intent, int flags, int startId){
        mediaplayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    //음악 끄기 버튼 누를 때
    public void onDestroy(){
        mediaplayer.stop();
        super.onDestroy();
    }

}