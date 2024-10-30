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


public class AccessServeur implements Sujet {

    private final String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";
    private final Gson gson = new Gson();

    private final RequestQueue requestQueue;
    private ListeChansons lp;
    private ObservateurChangement obs;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public AccessServeur(Context context){
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchChansons(String url) {

        handler.postDelayed(() -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                lp = gson.fromJson(response, ListeChansons.class);

                final Handler handler = new Handler();

                handler.postDelayed(new Thread() {
                    @Override
                    public void run(){
                        avertirObservateurs();
                    }
                }, 1000);

            }, error -> {
                error.printStackTrace();
            });

            requestQueue.add(stringRequest);
        }, 1000);

    }


    @Override
    public void ajouterObservateur(ObservateurChangement obs) {
        this.obs = obs;
    }

    @Override
    public void enleverObservateur(ObservateurChangement obs) {
        obs = null;

    }

    @Override
    public void avertirObservateurs() {
        if (obs != null){
            obs.changement(lp);
        }


    }
}
