package com.example.tp1_musique;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity implements ObservateurChangement {

    ExoPlayer exoPlayer;
    SeekBar seekBar;
    PlayerView vue;
    ImageView playPauseButton, nextButton, prevButton, coverAlbums;
    TextView titreChanson;
    Handler handler = new Handler();
    Runnable miseAJourSeekBar;

    ListeChansons listeChansons;
    int indexChansons = 0;
    long savedPosition = 0;
    AccessServeur accessServeur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.parent), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        listeChansons = new ListeChansons();

        vue = new PlayerView(this);

        vue.setUseController(false);


        seekBar = findViewById(R.id.seekBar);
        playPauseButton = findViewById(R.id.PlayPauseButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        titreChanson = findViewById(R.id.titreChanson);

        exoPlayer = new ExoPlayer.Builder(this).build();
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    skipToNextSong();
                }
            }

        });

        accessServeur = new AccessServeur(this);
        accessServeur.fetchChansons();
        accessServeur.ajouterObservateur(this);

        setUpSeekBar();
        setUpSong(indexChansons);

        playPauseButton.setOnClickListener(v -> togglePlayPause());
        nextButton.setOnClickListener(v -> skipToNextSong());
        prevButton.setOnClickListener(v -> skipToPreviousSong());
    }

    @Override
    public void changement(ListeChansons music) {
        this.listeChansons = music;
        setUpSong(indexChansons);
    }

    public void setUpSong(int index){
        if (listeChansons.getMusic() != null && index >= 0 && index < listeChansons.getMusic().size()){
            Chansons song = listeChansons.getMusic().get(index);

            coverAlbums = findViewById(R.id.coverAlbums);
            Glide.with(this)
                    .load((song.getImage()))
                    .into(coverAlbums);

            titreChanson.setText(song.getTitle());

            MediaItem mediaItem = MediaItem.fromUri(song.getSource());
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();

            titreChanson.setText(song.getTitle());
            seekBar.setMax((int) song.getDuration());

            seekBar.setProgress(0);

            startSeekBarUpdate();
        }
    }

    public void startSeekBarUpdate(){
        miseAJourSeekBar = new Runnable() {
            @Override
            public void run() {
                if (exoPlayer != null && exoPlayer.isPlaying()){
                    seekBar.setProgress((int) exoPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(miseAJourSeekBar);
    }

    public void stopSeekBarUpdate(){
        handler.removeCallbacks(miseAJourSeekBar);
    }

    public void togglePlayPause(){
        if (exoPlayer.isPlaying()){
            exoPlayer.pause();
            playPauseButton.setImageResource(R.drawable.video_pause_button);
            stopSeekBarUpdate();
        }
        else{
            exoPlayer.play();
            startSeekBarUpdate();
        }
    }

    public void skipToNextSong(){
        stopSeekBarUpdate();
        indexChansons = (indexChansons +1) % listeChansons.getMusic().size();
        setUpSong(indexChansons);
    }

    public void skipToPreviousSong(){
        stopSeekBarUpdate();
        indexChansons = (indexChansons -1 + listeChansons.getMusic().size()) % listeChansons.getMusic().size();
        setUpSong(indexChansons);
    }

    public void setUpSeekBar(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser){
                if(fromUser && exoPlayer != null){
                    exoPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopSeekBarUpdate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                startSeekBarUpdate();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSeekBarUpdate();
        exoPlayer.release();
        accessServeur.enleverObservateur(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (exoPlayer != null){
            long currentPosition = exoPlayer.getCurrentPosition();
            outState.putLong("currentPosition", currentPosition);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && exoPlayer != null){
            long currentPosition = savedInstanceState.getLong("currentPosition", 0);
            exoPlayer.seekTo(currentPosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null && exoPlayer.isPlaying()){
            exoPlayer.pause();
            savedPosition = exoPlayer.getCurrentPosition();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (exoPlayer != null && savedPosition > 0){
            exoPlayer.seekTo(savedPosition);
            exoPlayer.play();
        }
    }
}