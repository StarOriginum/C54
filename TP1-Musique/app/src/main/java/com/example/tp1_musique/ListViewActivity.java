package com.example.tp1_musique;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;

public class ListViewActivity extends AppCompatActivity {

    ListView liste;
    ListeChansons listeChansons;

    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        liste = findViewById(R.id.liste);
        context = this;

        listeChansons = new ListeChansons();

        AccessServeur accessServeur = new AccessServeur(this);
        accessServeur.fetchChansons();
        accessServeur.ajouterObservateur(listeChansons -> {
            Vector<Hashtable<String, Object>> songData = accessServeur.getChansonsHashtable();
            setupListView(songData);
        });
    }

    public void setupListView(Vector<Hashtable<String, Object>> songData){


        SimpleAdapter adapter = new SimpleAdapter(
                this,
                songData,
                R.layout.un_item,
                new String[]{"title", "album", "artist", "image"},
                new int[]{R.id.textChanson, R.id.textAlbum, R.id.textArtiste, R.id.albumCover}
        ) {
            @Override
            public void setViewImage(ImageView v, String value) {

                Glide.with(ListViewActivity.this)
                        .load(value)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(v);
            }

        };

        liste.setAdapter(adapter);

        liste.setOnItemClickListener(((parent, view, position, id) -> {


            Intent resultIntent = new Intent();
            resultIntent.putExtra("indexChanson", position);
            setResult(ListViewActivity.RESULT_OK, resultIntent);
            finish();
        }));
    }
}