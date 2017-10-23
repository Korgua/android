package com.example.korgua.musicplayer;

import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button playMusic;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.game_field);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(getApplicationContext(),"Duration: "+mediaPlayer.getDuration()/1000+" s",Toast.LENGTH_SHORT).show();
            }
        });

        playMusic = (Button)findViewById(R.id.buttonPlayPause);
        playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    pauseMusic();
                }
                else {
                    startMusic();
                }
            }
        });
    }

    public void pauseMusic(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            playMusic.setText(R.string.play);
        }
    }
    public void startMusic(){
        if(mediaPlayer != null){
            mediaPlayer.start();
            playMusic.setText(R.string.pause);
        }
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.start();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }


}
