package com.example.tp1_musique;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    SeekBar seekBar, volumeBar;
    PlayerView vue;
    Button btnShuffle, btnRepeat;
    ImageView playPauseButton, nextButton, prevButton, quitButton, coverAlbums, rewindButton, forwardButton;
    TextView titreChanson, titreAlbum, titreArtiste;
    Handler handler = new Handler();
    Runnable miseAJourSeekBar;
    SerialiserExoPlayer serialiserExoPlayer;

    ListeChansons listeChansons;
    int indexChansons = 0;
    long savedPosition = 0;
    Boolean isShuffleOn, isRepeatOn;
    AccessServeur accessServeur;
    private static final int REQUEST_CODE_SELECT_SONG = 1;
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
        isShuffleOn = false;
        isRepeatOn = false;
        accessServeur = new AccessServeur(this);
        serialiserExoPlayer = new SerialiserExoPlayer();
        listeChansons = new ListeChansons();

        vue = new PlayerView(this);

        vue.setUseController(false);


        seekBar = findViewById(R.id.seekBar);
        volumeBar = findViewById(R.id.volumeBar);

        playPauseButton = findViewById(R.id.PlayPauseButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        quitButton = findViewById(R.id.quitButton);
        rewindButton = findViewById(R.id.rewindButton);
        forwardButton = findViewById(R.id.forwardButton);
        btnShuffle = findViewById(R.id.btnShuffle);
        btnRepeat = findViewById(R.id.btnRepeat);

        titreChanson = findViewById(R.id.titreChanson);
        titreAlbum = findViewById(R.id.titreAlbum);
        titreArtiste = findViewById(R.id.titreArtiste);

        exoPlayer = new ExoPlayer.Builder(this).build();


        accessServeur.fetchChansons();
        accessServeur.ajouterObservateur(this);

        setUpSeekBar();
        setUpSong(indexChansons);

        volumeBar.setMax(100);
        volumeBar.setProgress((int) (exoPlayer.getVolume() * 100));

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float volume = progress / 100f;
                    exoPlayer.setVolume(volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        playPauseButton.setOnClickListener(v -> togglePlayPause());
        nextButton.setOnClickListener(v -> skipToNextSong());
        prevButton.setOnClickListener(v -> skipToPreviousSong());

        rewindButton.setOnClickListener(v -> {
            long currentPosition = exoPlayer.getCurrentPosition();
            long newPosition = Math.max(0, currentPosition - 10000);
            exoPlayer.seekTo(newPosition);
        });

        forwardButton.setOnClickListener(v -> {
            long currentPosition = exoPlayer.getCurrentPosition();
            long newPosition = Math.min(exoPlayer.getDuration(), currentPosition + 10000);
            exoPlayer.seekTo(newPosition);
        });

        btnShuffle.setOnClickListener(v -> {
            isShuffleOn = !isShuffleOn;
            exoPlayer.setShuffleModeEnabled(isShuffleOn);

            String shuffleText = isShuffleOn ? "Shuffle: On" : "Shuffle: Off";
            btnShuffle.setText(shuffleText);
        });

        btnRepeat.setOnClickListener(v -> {
            isRepeatOn = !isRepeatOn;
            exoPlayer.setRepeatMode(isRepeatOn ? Player.REPEAT_MODE_ALL : Player.REPEAT_MODE_OFF);

            String repeatText = isRepeatOn ? "Repeat: On" : "Repeat: Off";
            btnRepeat.setText(repeatText);
        });

        // Cliquer sur ce bouton envoi l'activité vers ListViewActivity
        // et arrête le exoplayer et attend la réponse de ListViewActivity
        quitButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            exoPlayer.stop();
            startActivityForResult(intent, REQUEST_CODE_SELECT_SONG);
        });

    }

    @Override
    public void changement(ListeChansons music) {
        this.listeChansons = music;
        setUpSong(indexChansons);
    }

    //
    public void setUpSong(int index){
        if (listeChansons.getMusic() != null && index >= 0 && index < listeChansons.getMusic().size()){
            Chansons song = listeChansons.getMusic().get(index);

            coverAlbums = findViewById(R.id.coverAlbums);
            Glide.with(this)
                    .load((song.getImage()))
                    .into(coverAlbums);

            titreChanson.setText(song.getTitle());
            titreAlbum.setText((song.getAlbum()));
            titreArtiste.setText(song.getArtist());

            MediaItem mediaItem = MediaItem.fromUri(song.getSource());
            exoPlayer.setMediaItem(mediaItem);

            exoPlayer.prepare();
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
            

            exoPlayer.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_READY) {

                        seekBar.setMax((int) exoPlayer.getDuration());
                    }
                    else if (playbackState == Player.STATE_ENDED) {
                        skipToNextSong();
                    }
                }
            });

            exoPlayer.play();
            Log.d("MainActivity", "setUpSong: index: " + index + ", seekBar progress: " + seekBar.getProgress());

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
            playPauseButton.setImageResource(android.R.drawable.ic_media_pause);
            stopSeekBarUpdate();
        }
        else{
            exoPlayer.play();
            playPauseButton.setImageResource(android.R.drawable.ic_media_play);
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
            serialiserExoPlayer.savePlayer(this, savedPosition);
            handler.removeCallbacks(miseAJourSeekBar);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        long savedPosition = serialiserExoPlayer.loadPlayer(this);
        if (exoPlayer != null && savedPosition > 0){
            exoPlayer.seekTo(savedPosition);
            exoPlayer.play();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_SONG && resultCode == RESULT_OK){
            int index = data.getIntExtra("indexChanson", 0);
            boolean resetSeekbar = data.getBooleanExtra("resetSeekbar", false);

            if (resetSeekbar){
                seekBar.setProgress(0);
            }

            setUpSong(index);
            seekBar.setProgress(0);
            exoPlayer.seekTo(0);

            Log.d("MainActivity", "onActivityResult: resetSeekbar: " + data.getBooleanExtra("resetSeekbar", false));
        }
    }
}