package com.example.annexe0

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.*
import java.util.Vector

class ListeActivity : AppCompatActivity() {
    lateinit var memos: Vector<String>
    lateinit var listeMemos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_liste)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        memos = Vector()


        listeMemos = findViewById(R.id.listeMemos)

        val fis : FileInputStream = openFileInput("fichier.txt")
        val isw = InputStreamReader(fis)
        val br = BufferedReader(isw)

        br.read()
        val adapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, lesMemos())
        listeMemos.setAdapter(adapter)
    }

    fun lesMemos(): Vector<String>
    {
        var vecteur = Vector<String>()

        val fis : FileInputStream = openFileInput("fichier.txt")
        val isw = InputStreamReader(fis) // traduit en caractères
        val br = BufferedReader(isw) // plus rapide
        br.forEachLine { s -> vecteur.add(s) }


        br.close() // ne pas l'oublier

        return vecteur

    }
}