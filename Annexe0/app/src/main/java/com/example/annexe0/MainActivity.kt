package com.example.annexe0

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ajouter: Button
    lateinit var afficher: Button
    lateinit var quitter: Button
    /*var memo: Button? = null
    var liste: ListView? = null*/

    var i: Intent = Intent(this@MainActivity, AjouterActivity::class.java)
    var j: Intent = Intent(this@MainActivity, ListeActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ajouter = findViewById(R.id.btnAjouter)
        afficher = findViewById(R.id.btnAfficher)
        quitter = findViewById(R.id.btnQuitter)

        val ec = Ecouteur() // Declaration du type facultatif

        val elementsEcouteurs = arrayOf<View?>(ajouter, afficher, quitter)

        for (view in elementsEcouteurs) {
            view!!.setOnClickListener(ec)
        }
    }

    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === ajouter) {
                startActivity(i)
            } else if (v === afficher) {
                startActivity(j)
            } else if (v === quitter) {
                finish();
            }
        }
    }
}