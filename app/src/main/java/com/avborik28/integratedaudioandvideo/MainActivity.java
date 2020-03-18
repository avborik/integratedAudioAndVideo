package com.avborik28.integratedaudioandvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private VideoView myVideoView;
    private Button btnPlayVideo;
    private Button btnPlayMusic;
    private Button btnPauseMusic;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBarVolume;
    private AudioManager audioManger;
    private SeekBar moveBackAndForthSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVideoView = findViewById(R.id.myVideoView);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        moveBackAndForthSeekBar = findViewById(R.id.seekBarMove);

        btnPlayVideo.setOnClickListener(MainActivity.this);
        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);

        mediaController = new MediaController(MainActivity.this);
        mediaPlayer = MediaPlayer.create(this, R.raw.android_music);
        audioManger = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maximumVolumeOfUserDevice = audioManger.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolumeOfUserDevice = audioManger.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBarVolume.setMax(maximumVolumeOfUserDevice);
        seekBarVolume.setProgress(currentVolumeOfUserDevice);

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Toast.makeText(MainActivity.this, Integer.toString(progress),Toast.LENGTH_LONG).show();
                    audioManger.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        moveBackAndForthSeekBar.setOnSeekBarChangeListener(this);

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            Toast.makeText(this, Integer.toString(progress), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View buttonView) {

        switch (buttonView.getId()){
            case R.id.btnPlayVideo:
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.android_video);

                myVideoView.setVideoURI(videoUri);

                myVideoView.setMediaController(mediaController);
                mediaController.setAnchorView(myVideoView);

                myVideoView.start();
            break;
            case R.id.btnPlayMusic:
                mediaPlayer.start();
            break;
            case R.id.btnPauseMusic:
                mediaPlayer.pause();
            break;
        }

    }
}

