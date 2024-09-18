package com.factures.applicationpaiementfactures

import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import java.io.Serializable

import android.widget.LinearLayout

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout
    lateinit var noDent1: EditText
    lateinit var noDent2: EditText
    lateinit var note1: EditText
    lateinit var note2: EditText
    lateinit var check1: CheckBox
    lateinit var check2: CheckBox






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        noDent1 = findViewById(R.id.no_Dents1)
        noDent2 = findViewById(R.id.no_Dents2)

        note1 = findViewById(R.id.notes_Dents1)
        note2 = findViewById(R.id.notes_Dent2)

        check1 = findViewById(R.id.checkbox1)
        check2 = findViewById(R.id.checkbox2)



    }

    data class infoDent1(val noDent1: String, val note1: String, val check1: Boolean) : Serializable
    data class infoDent2(val noDent2: String, val note2: String, val check2: Boolean) : Serializable




    override fun onStop() {
        super.onStop()

        val fos = openFileOutput("data.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(noDent1)
            it.writeObject(noDent2)
            it.writeObject(note1)
            it.writeObject(note2)
            it.writeObject(check1)
            it.writeObject(check2)
        }

    }

    override fun onResume() {
        super.onResume()

        val fis = openFileInput("data.ser")
        val ois = ObjectInputStream(fis)
        ois.use{
            noDent1 = it.readObject() as EditText
            noDent2 = it.readObject() as EditText
            note1 = it.readObject() as EditText
            note2 = it.readObject() as EditText
            check1 = it.readObject() as CheckBox
            check2 = it.readObject() as CheckBox
        }

    }

    override fun onPause() {
        super.onPause()
        val fos = openFileOutput("data.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(dent1)
            it.writeObject(dent2)
            it.writeObject(note1)
            it.writeObject(note2)
            it.writeObject(check1)
            it.writeObject(check2)
        }
    }

    fun saveDent1(){
        val infoDent1 = infoDent1(
        noDent1.text.toString(),
        note1.text.toString(),
        check1.isChecked)

        val fos = openFileOutput("data.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(infoDent1)
        }


    }

    fun saveDent2(){
        val infoDent2 = infoDent2(
        noDent2.text.toString(),
        note2.text.toString(),
        check2.isChecked
        )

        val fos = openFileOutput("data.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(infoDent2)
        }




    }


}