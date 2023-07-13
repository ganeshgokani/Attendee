package com.example.attendeeapk

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.icu.util.Calendar


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class SecondActivity : AppCompatActivity() {
    private var presentCount: Int = 0
    private var lastTappedButtonId: Int = -1
    private var i:Int = 1
    private var absentCount: Int = 0

    private var totalStrength: Int = 0
    private val presentRollNumbers = mutableListOf<Int>()
    private val absentRollNumbers = mutableListOf<Int>()
    var on:Int = 1
    private lateinit var rollView: TextView
    private lateinit var presentView: TextView
    private lateinit var absentView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page2)

        totalStrength = intent.getIntExtra("enteredNumberKey", 0)
         val sub = intent.getStringExtra("EnterSubject")
        val year=intent.getStringExtra("EnterYear")
        rollView = findViewById(R.id.rollview)
        presentView = findViewById(R.id.presnt)
        val goBackButton = findViewById<ImageView>(R.id.backbutton)
        absentView = findViewById(R.id.absen)

        val buttonP: Button = findViewById(R.id.buttonp)
        val buttonA: Button = findViewById(R.id.buttona)

        val refreshButton: ImageView = findViewById(R.id.refresh)
        val copyPresentButton: Button = findViewById(R.id.cp)
        val copyAbsentButton: Button = findViewById(R.id.ca)

        val currentDateTime = Date()
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDateTime = formatter.format(currentDateTime)

        val currentTime = Calendar.getInstance()
        val formatterr = SimpleDateFormat("HHmm", Locale.getDefault())
        val formattedTime = formatterr.format(currentTime.time).toInt()
        val num:Int
        when(formattedTime){
            in 920..1010 ->  num = 1
            in 1010..1100 ->  num = 2
            in 1110..1200 ->  num = 3
            in 1200..1250 ->  num = 4
            in 1330..1420 ->  num = 5
            in 1420..1510 ->  num = 6
            in 1510..1600 ->  num = 7
            else
                -> num =0

        }

        buttonP.setOnClickListener {    //present button
            if (i <= totalStrength) {
                presentCount++ //increasing value
                lastTappedButtonId = R.id.buttonp
                presentRollNumbers.add(i)
                i++
                on=1
                updateTextViews()
            }
            else run{
                Toast.makeText(this,
                    "Completed",Toast.LENGTH_LONG).show()
            }
        }

        buttonA.setOnClickListener {
            if (i <= totalStrength) {
                absentCount++
                on=1
                lastTappedButtonId = R.id.buttona
                absentRollNumbers.add(i)
                i++
                updateTextViews()
            }
            else run{
                Toast.makeText(this,
                    "Completed",Toast.LENGTH_LONG).show()
            }
        }


        goBackButton.setOnClickListener {

            if (lastTappedButtonId == R.id.buttonp && on==1) {
                val lastIndex = presentRollNumbers.lastIndex
                i--
                on=0
                presentRollNumbers.removeAt(lastIndex)
                updateTextViews()
            } else if (lastTappedButtonId == R.id.buttona && on==1) {
                val lastIndex = absentRollNumbers.lastIndex
                i--
                on=0
                absentRollNumbers.removeAt(lastIndex)
                updateTextViews()
            }
        }
        refreshButton.setOnClickListener {
            resetAttendance()
        }

        val shareButtonPresent = findViewById<Button>(R.id.cp)
        val shareButtonAbsent= findViewById<Button>(R.id.ca)


        shareButtonPresent.setOnClickListener {
            val shareString = presentRollNumbers.joinToString(", ")
            if(num==0)
            {
                shareText("$formattedDateTime   $sub  $year \n\nPresenters:\n$shareString")
            }else
            {
                shareText(formattedDateTime+"  Period :${num}  "+sub+"  $year  "+"\n\n"+"Presenters:\n"+ shareString)
            }


        }

        shareButtonAbsent.setOnClickListener {
            val shareString = absentRollNumbers.joinToString(", ")
            if(num==0)
            {
                shareText("$formattedDateTime  $sub  $year  \n\nAbsentees:\n$shareString")
            }else
            {
                shareText(formattedDateTime+"  Period :${num}   "+sub+"  $year  " +"\n\n"+ "Absentees: \n"+shareString)
            }


        }




    }

    fun shareText(text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(shareIntent, "Sharevia")) }

    private fun updateTextViews() {


            rollView.text = "Present Count: $i"
            presentView.text = "Present Roll Numbers: ${presentRollNumbers.joinToString(", ")}"
            absentView.text = "Absent Roll Numbers: ${absentRollNumbers.joinToString(", ")}"


    }


    private fun resetAttendance() {
        presentCount = 0
        absentCount = 0
        i=1
        rollView.text ="Present Count: 1"
        presentRollNumbers.clear()
        absentRollNumbers.clear()
        updateTextViews()
        Toast.makeText(this, "Attendance reset", Toast.LENGTH_SHORT).show()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Attendance", text)
        clipboard.setPrimaryClip(clip)
    }
}