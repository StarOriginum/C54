package com.example.tp1_musique;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.os.Handler;
import android.os.Looper;
import android.service.carrier.CarrierMessagingService;

import java.util.Hashtable;
import java.util.Vector;


public class AccessServeur implements Sujet {


    private final String url = "https://api.jsonbin.io/v3/b/6723b430e41b4d34e44bfa92?meta=false";
    private final Gson gson = new Gson();

    private final RequestQueue requestQueue;
    private ListeChansons lp;
    private ObservateurChangement obs;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public AccessServeur(Context context){
        this.requestQueue = Volley.newRequestQueue(context);

    }

    public void fetchChansons() {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                lp = gson.fromJson(response, ListeChansons.class);

                final Handler handler = new Handler();

                handler.post(new Thread() {
                    @Override
                    public void run(){
                        avertirObservateurs();
                    }
                });

            }, error -> {
                error.printStackTrace();
            });

            requestQueue.add(stringRequest);

    }

    public Vector<Hashtable<String, Object>> getChansonsHashtable() {
        Vector<Hashtable<String, Object>> songList = new Vector<>();

        if (lp == null){
            fetchChansons();
        }

        if (lp != null){
            for (Chansons song: lp.getMusic()) {
                Hashtable<String, Object> data = new Hashtable<>();

                data.put("title", song.getTitle());
                data.put("album", song.getAlbum());
                data.put("artist", song.getArtist());
                data.put("image", song.getImage());
                songList.add(data);
            }
        }
        return songList;
    }


    @Override
    public void ajouterObservateur(ObservateurChangement obs) {
        this.obs = obs;
    }

    @Override
    public void enleverObservateur(ObservateurChangement obs) {
        this.obs = null;

    }

    @Override
    public void avertirObservateurs() {
        if (obs != null){
            obs.changement(lp);
        }
    }
}
