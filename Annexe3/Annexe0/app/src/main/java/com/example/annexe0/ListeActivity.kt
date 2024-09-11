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



        listeMemos = findViewById(R.id.listeMemos)

        val adapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, convertir())
        listeMemos.setAdapter(adapter)
    }

    fun convertir():Vector<String>
    {
        var listeTexteMemos = Vector<String>()
        var listeMemos = GestionMemos.getInstance().listeMemos
        for (memo in listeMemos)
            listeTexteMemos.add(memo.memoTexte)
        return listeTexteMemos

    }
}