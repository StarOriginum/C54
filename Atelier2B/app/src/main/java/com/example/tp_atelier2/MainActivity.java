package com.example.tp_atelier2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Vector<Hashtable<String, Object>> vect = new Vector<>();
    ListView liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        liste = findViewById(R.id.liste);

        RequestQueue rQ = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/637056232b3499323bfe110a?meta=false";

        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url, s -> Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show(), Throwable::printStackTrace);

        rQ.add(stringRequest);*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

                Log.d("Response", response);
                Gson gson = new Gson();

                ListeProduit listeProduit = gson.fromJson(response, ListeProduit.class);

                for (Produit produit : listeProduit.articles){
                    Hashtable<String, Object> temp = new Hashtable<>();
                    temp.put("nom", produit.getNom());
                    temp.put("prix", String.valueOf(produit.getPrix()));
                    vect.add(temp);
                }

                for (Hashtable<String, Object> item: vect){
                    System.out.println(item.get("nom") + " " + item.get("prix"));
                }

                SimpleAdapter adapter = new SimpleAdapter(
                        this, vect, R.layout.un_item,
                        new String[]{"nom","prix"},
                        new int[]{R.id.textView, R.id.textView2}
                );
                liste.setAdapter(adapter);
            }, error -> {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Error fetching data!", Toast.LENGTH_SHORT).show();
            }

        );
        rQ.add(stringRequest);



    }
}