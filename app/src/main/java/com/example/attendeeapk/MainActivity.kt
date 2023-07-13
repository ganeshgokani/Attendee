package com.example.attendeeapk

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val summit = findViewById<Button>(R.id.button2)

        summit.setOnClickListener {  // button
            val clasStrength = findViewById<EditText>(R.id.editTextPhone)
            val editTextVal = clasStrength?.text?.toString()?.toIntOrNull()
            val sub= findViewById<EditText>(R.id.editTexttext)
            val year =findViewById<EditText>(R.id.editTextYear)
            val subjectName=sub?.text?.toString()

            //checking entered value
            if (editTextVal != null) {
                if(editTextVal>0){ //moving to page 20
                    val intent = Intent(this, SecondActivity::class.java).also {
                        it.putExtra("enteredNumberKey", editTextVal)
                        it.putExtra("EnterSubject",subjectName.toString())
                        it.putExtra("EnterYear",year.toString())
                        startActivity(it)

                    }}
                    else run { //if entered less than 0
                        Toast.makeText(this, "Enter a valid class strength", Toast.LENGTH_LONG) //displaying message
                            .show()

                    }
                }}}
}



