package com.example.annexe1b

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.Buffer

class MainActivity : AppCompatActivity() {
    lateinit var nbLignes: TextView
    lateinit var nbChar: TextView
    lateinit var nbC: TextView
    lateinit var nbMots: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nbLignes = findViewById(R.id.textView)
        nbChar = findViewById(R.id.textView2)
        nbC = findViewById(R.id.textView3)
        nbMots = findViewById(R.id.textView4)


        nbLignes.append(lireNbLignes().toString())
        nbChar.append(lireNbChar().toString())
        nbC.append(lireNbC().toString())
        nbMots.append(ecrireText("Michael Turner").toString())
    }

    fun lireNbLignes(): Long{
        var nbLignes : Long = 0

        val fis : FileInputStream = openFileInput("blabla.txt")
        val isw = InputStreamReader(fis)
        val br = BufferedReader(isw)

        while(br.readLine() != null) nbLignes++
        br.close()
        return nbLignes
    }

    fun lireNbChar(): Int{
        var nbChar = 0

        val fis : FileInputStream = openFileInput("blabla.txt")
        val isw = InputStreamReader(fis)
        val br = BufferedReader(isw)
        br.use {
            while(br.readLine() != null){
            nbChar += br.readLine().length
        }
             }
        return nbChar
    }

    fun lireNbC(): Int{
        var nbC = 0
        val charC = 'c'
        val charCUpper = 'C'
        var lignes: String?

        val fis : FileInputStream = openFileInput("blabla.txt")
        val isw = InputStreamReader(fis)
        val br = BufferedReader(isw)

        while(true){
            lignes = br.readLine() ?: break

            if(lignes.contains(charC, ignoreCase = true)){
                nbC++
            }
        }
        br.close()
        return nbC
    }

    fun ecrireText(texte: String){
        val fis : FileOutputStream = openFileOutput("blabla.txt", MODE_APPEND)
        val isw = OutputStreamWriter(fis)
        val br = BufferedWriter(isw)

        br.newLine()
        br.write(texte)
        br.close()
    }
}