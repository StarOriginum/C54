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
import java.io.FileNotFoundException
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

    data class infoDents(val noDent1: String, val note1: String, val check1: Boolean,
                         val noDent2: String, val note2: String, val check2: Boolean) : Serializable





    override fun onStop() {
        super.onStop()

        saveDents()


    }

    override fun onResume() {
        super.onResume()

        loadDents()

    }

    override fun onPause() {
        super.onPause()
        saveDents()

    }


    fun saveDents(){
        val infoDents = infoDents(
            noDent1.text.toString(),
            note1.text.toString(),
            check1.isChecked,
            noDent2.text.toString(),
            note2.text.toString(),
            check2.isChecked
        )

        val fos = openFileOutput("data.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.use {
            it.writeObject(infoDents)
        }
    }

    fun loadDents(){
        try {
            val fis = openFileInput("data.ser")
            val ois = ObjectInputStream(fis)
            val infoDents = ois.readObject() as infoDents

            ois.use{
                noDent1.setText(infoDents.noDent1)
                note1.setText(infoDents.note1)
                check1.isChecked = infoDents.check1

                noDent2.setText(infoDents.noDent2)
                note2.setText(infoDents.note2)
                check2.isChecked = infoDents.check2
            }
        } catch(fnfe: FileNotFoundException)
        {
            fnfe.printStackTrace()
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }


    }


}