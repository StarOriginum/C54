package com.example.tp1_musique;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import android.os.Handler;




public class AccessServeur implements Sujet {

    String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";
    Gson gson = new Gson();

    private RequestQueue requestQueue;

    public AccessServeur(Context context){
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void fetchChansons(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            ListeChansons lc = gson.fromJson(response, ListeChansons.class);

            final Handler handler = new Handler();

            handler.postDelayed(new Thread() {
                @Override
                public void run(){
                    avertirObservateurs();
                }
            }, 1000);

            /*setChanged();
            notifyObservers(lc);*/
        }, error -> {
            error.printStackTrace();
        });

        requestQueue.add(stringRequest);
    }


    @Override
    public void ajouterObservateur(ObservateurChangement obs) {

    }

    @Override
    public void enleverObservateur(ObservateurChangement obs) {

    }

    @Override
    public void avertirObservateurs() {

    }
}
