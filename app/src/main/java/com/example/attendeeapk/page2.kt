package com.example.attendeeapk


import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Page2 : AppCompatActivity() {
    val receivedNumber = intent.getIntExtra("enteredNumberKey", 0)
    lateinit var PrenArry : ArrayList<Int>
    lateinit var absentArry : ArrayList<Int>

    val pressa =findViewById<Button>(R.id.buttonp)
    val refreshh =findViewById<Button>(R.id.refresh)
    val pressb = findViewById<Button>(R.id.buttona)
    val view1 = findViewById<TextView>(R.id.presnt)
    val view2 = findViewById<TextView>(R.id.absen)
     override fun onCreate(savedInstanceState: Bundle?) {


         super.onCreate(savedInstanceState)
        setContentView(R.layout.page2)

     start()
         refreshh.setOnClickListener(){
             start()
         }



    }

fun start(){
    var count:Int=1
  var p:Int=0
    var a:Int=0
var i:Int=1

        pressa.setOnClickListener(){

            PrenArry.add(i)
            count+= 1
            p+=1
        }
        pressb.setOnClickListener(){

            absentArry.add(i)
            count+= 1
            a+=1
        }
    }

    val arrayAsString = PrenArry.joinToString(", ")
   val arrayofab = absentArry.joinToString { " , " }

}


