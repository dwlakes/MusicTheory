package com.example.musictheory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList


class earTrainingMainMenu : AppCompatActivity() {
    lateinit var selectedIntervals: BooleanArray

    private var soundPool: SoundPool? = null
    var soundId = 1

    var intervalList : MutableList<Int> = mutableListOf()
    var intervalsArray = arrayOf(
        "Minor Second", "Major Second", "Minor Third", "Major Third",
        "Perfect Fourth", "Tritone", "Perfect Fifth", "Minor Sixth",
        "Major Sixth", "Minor Seventh", "Major Seventh", "Octave"
    )

    private lateinit var playAudioBtn: Button
    var mediaPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_main_menu)

        //soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        //soundPool!!.load(baseContext, R.assets.major2_ascending01, 1)

        createIntervalsDropdown()
        val startExerciseBtn = findViewById<Button>(R.id.startExerciseBtn)
        startExerciseBtn.setOnClickListener{
            
            // Singletonlist cannot be cast to arraylist 
            if (intervalList.size == 1){
                println("Selected only 1 interval")
                // Converts singleton to single variable
                val singleInterval = intervalList[0]
                // Creates arraylist for single selection
                val selectedIntervals = arrayListOf<Int>(singleInterval)
                println("Selected intervals"+selectedIntervals)
                val intent = Intent(this, earTrainingExercise::class.java)
                intent.putIntegerArrayListExtra("selected_intervals",selectedIntervals)
                startActivity(intent)
            } else {
                //Copies mutable list to immutable arraylist
                //Kotlin can't pass a mutable list through activities
                val selectedIntervals: ArrayList<Int> = intervalList.toList() as ArrayList<Int>
                println("Selected intervals"+selectedIntervals)
                val intent = Intent(this, earTrainingExercise::class.java)
                intent.putIntegerArrayListExtra("selected_intervals",selectedIntervals)
                startActivity(intent) 
            }
            
        }
    }

    // Interval selection dropdown
    private fun createIntervalsDropdown() {
        // assign variable
        val tvIntervals = findViewById<TextView>(R.id.IntervalsTextView)
        // initialize selected interval
        selectedIntervals = BooleanArray(intervalsArray.size)
        tvIntervals.setOnClickListener(View.OnClickListener { //Initialize alert dialog                            //nead to change to earTrainingMainMenu
            val builder = AlertDialog.Builder(this@earTrainingMainMenu)

            //Set title
            builder.setTitle("Selected Interval")
            //Set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                intervalsArray,
                selectedIntervals
            ) { dialogInterface, i, b ->
                //Check condition
                if (b) {
                    //When checkbox selected
                    //Add position in interval list
                    intervalList.add(i)
                    //Sort
                    Collections.sort(intervalList)
                } else {
                    //When checkbox unselected
                    //Remove position from interval list
                    intervalList.remove(i)
                }
            }
            builder.setPositiveButton("Ok") { dialogInterface, i -> //Initalize string builder
                val stringBuilder = StringBuilder()

                // Gets selected index with matching interval
                for (j in intervalList.indices) {
                    //Concat array val
                    stringBuilder.append(intervalsArray[intervalList[j]])
                    //Check condition
                    if (j != intervalList.size - 1) {
                        //When ja value not equal to interval list size -1
                        //Add comma
                        stringBuilder.append(", ")
                    }
                }
                //Set text on text vie
                tvIntervals.text = stringBuilder.toString()

            }
            builder.setNegativeButton("Cancel") { dialogInterface, i -> //Dismiss dialog
                dialogInterface.dismiss()
            }
            // Clear selected intervals
            builder.setNeutralButton("Clear All") { dialogInterface, i ->
                //Use for loop
                for (j in selectedIntervals.indices) {
                    //remove all selection
                    selectedIntervals[j] = false
                    intervalList.clear()
                    tvIntervals.text = ""
                }
            }
            //Show dialog
            builder.show()
        })
    }

}