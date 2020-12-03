package com.example.timer.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.example.timer.R;
import com.example.timer.SequencePage.ActionType;
import com.example.timer.SequencePage.SequenceActivity;

public class TimerService extends Service {
    CountDownTimer countDownTimer;
    private static int position = 0;
    private static int[] durations;
    private SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    private int startMusicId;
    private int finishMusicId;

    @Override
    public IBinder onBind(Intent intent){
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
        startMusicId = soundPool.load(this, R.raw.start, 1);
        finishMusicId = soundPool.load(this, R.raw.finish, 1);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        durations = intent.getIntArrayExtra(SequenceActivity.PARAM_INPUT);
        ActionType action = intent.getParcelableExtra(SequenceActivity.TYPE_ACTION);
        switch(action){
            case START :
                startTimer();
                break;
            case STOP:
                stopTimer();
                break;
            case NEXT:
                nextTimer();
                break;
            case PREV:
                prevTimer();

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopSelf();
    }

    public void startTimer(){
        if(durations != null && durations.length > 0){
            countDownTimer = new CountDownTimer(durations[position] * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime(millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    position++;
                    if(position < durations.length){
                        countDownTimer.cancel();
                        soundPool.play(startMusicId, 1, 1, 0, 0, 1);
                        startTimer();
                    }
                    else{
                        soundPool.play(finishMusicId, 1, 1, 0, 0, 1);
                        position = 0;
                        updateTime(0);
                        stopSelf();
                    }
                }
            };
        }
        countDownTimer.start();
    }

    public void stopTimer(){
        countDownTimer.cancel();
    }

    public void updateTime(long time){
        Intent intent = new Intent(SequenceActivity.BROADCAST_ACTION);
        intent.putExtra(SequenceActivity.PARAM_RESULT, time);
        intent.putExtra(SequenceActivity.POSITION, position);
        sendBroadcast(intent);
    }

    public void nextTimer(){
        if(position < durations.length - 1){
            position++;
            countDownTimer.cancel();
            startTimer();
        }
    }

    public void prevTimer(){
        if(position > 0){
            position--;
            countDownTimer.cancel();
            startTimer();
        }
    }
}
