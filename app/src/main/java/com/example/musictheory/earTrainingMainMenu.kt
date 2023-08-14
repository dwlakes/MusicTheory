package com.example.musictheory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList


class earTrainingMainMenu : AppCompatActivity() {

    lateinit var tvIntervals:TextView
    lateinit var tvScales:TextView

    lateinit var selectedIntervals: BooleanArray
    var intervalList : MutableList<Int> = mutableListOf()
    var intervalsArray = arrayOf(
        "Minor Second", "Major Second", "Minor Third", "Major Third",
        "Perfect Fourth", "Tritone", "Perfect Fifth", "Minor Sixth",
        "Major Sixth", "Minor Seventh", "Major Seventh", "Octave"
    )

    lateinit var selectedScales: BooleanArray
    var scalesList : MutableList<Int> = mutableListOf()
    var scalesArray = arrayOf(
        "Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_main_menu)

        createIntervalsDropdown()
        createScalesDropdown()
        val startExerciseBtn = findViewById<Button>(R.id.startExerciseBtn)
        startExerciseBtn.setOnClickListener{

            if(intervalList.size > 0){
                startEarTrainingInterval()

            } else if(scalesList.size>0) {
                startEarTrainingScales()
            }else{
                Toast.makeText(this, "You need to select something.", Toast.LENGTH_SHORT).show()
            }

            
        }
    }

    private fun startEarTrainingScales() {
        // Singletonlist cannot be cast to arraylist
        if (scalesList.size == 1){
            println("Selected only 1 interval")
            // Converts singleton to single variable
            val singleScale = scalesList[0]
            // Creates arraylist for single selection
            val selectedScales = arrayListOf<Int>(singleScale)
            println("Selected Scales"+selectedScales)
            val intent = Intent(this, earTrainingScalesExercise::class.java)
            intent.putIntegerArrayListExtra("selected_scales",selectedScales)
            startActivity(intent)
        } else {
            //Copies mutable list to immutable arraylist
            //Kotlin can't pass a mutable list through activities
            val selectedScales: ArrayList<Int> = scalesList.toList() as ArrayList<Int>
            println("Selected scales"+selectedScales)
            val intent = Intent(this, earTrainingScalesExercise::class.java)
            intent.putIntegerArrayListExtra("selected_intervals",selectedScales)
            startActivity(intent)
        }
    }

    private fun startEarTrainingInterval() {
        // Singletonlist cannot be cast to arraylist
        if (intervalList.size == 1){
            println("Selected only 1 interval")
            // Converts singleton to single variable
            val singleInterval = intervalList[0]
            // Creates arraylist for single selection
            val selectedIntervals = arrayListOf<Int>(singleInterval)
            println("Selected intervals"+selectedIntervals)
            val intent = Intent(this, earTrainingIntervalExercise::class.java)
            intent.putIntegerArrayListExtra("selected_intervals",selectedIntervals)
            startActivity(intent)
        } else {
            //Copies mutable list to immutable arraylist
            //Kotlin can't pass a mutable list through activities
            val selectedIntervals: ArrayList<Int> = intervalList.toList() as ArrayList<Int>
            println("Selected intervals"+selectedIntervals)
            val intent = Intent(this, earTrainingIntervalExercise::class.java)
            intent.putIntegerArrayListExtra("selected_intervals",selectedIntervals)
            startActivity(intent)
        }
    }

    // Interval selection dropdown
    private fun createIntervalsDropdown() {
        // assign variable
        tvIntervals = findViewById<TextView>(R.id.IntervalsTextView)
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
                checkIntervalListSize()
                //Set text on text vie
                if (intervalList.size != 0) {
                    tvIntervals.text = stringBuilder.toString()
                } else {
                    tvIntervals.text = "Intervals"
                }

            }
            builder.setNegativeButton("Cancel") { dialogInterface, i -> //Dismiss dialog
                checkIntervalListSize()
                dialogInterface.dismiss()
            }
            // Clear selected intervals
            builder.setNeutralButton("Clear All") { dialogInterface, i ->
                //Use for loop
                for (j in selectedIntervals.indices) {
                    //remove all selection
                    selectedIntervals[j] = false
                    intervalList.clear()
                    tvIntervals.text = "Intervals"
                }
                checkIntervalListSize()
            }
            //Show dialog
            builder.show()

        })
    }

    private fun checkIntervalListSize() {
        // Disables other dropdowns if intervals is selected
        if(intervalList.size>0){
            createScalesDropdown()
            tvScales.isEnabled = false
        // Re-enables other dropdowns if intervals list is 0
        } else{
            tvScales.isEnabled = true
        }
    }

    private fun createScalesDropdown(){
        // assign variable
        tvScales = findViewById<TextView>(R.id.scalesTextViewEarTraining)
        // initialize selected scale
        selectedScales = BooleanArray(scalesArray.size)
        tvScales.setOnClickListener(View.OnClickListener { //Initialize alert dialog                            //nead to change to earTrainingMainMenu
            val builder = AlertDialog.Builder(this@earTrainingMainMenu)

            //Set title
            builder.setTitle("Selected Scale")
            //Set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                scalesArray,
                selectedScales
            ) { dialogInterface, i, b ->
                //Check condition
                if (b) {
                    //When checkbox selected
                    //Add position in scales list
                    scalesList.add(i)
                    //Sort
                    Collections.sort(scalesList)
                } else {
                    //When checkbox unselected
                    //Remove position from scales list
                    scalesList.remove(i)
                }
                println(scalesList.toString())
            }
            builder.setPositiveButton("Ok") { dialogInterface, i -> //Initalize string builder
                val stringBuilder = StringBuilder()

                // Gets selected index with matching scale
                for (j in scalesList.indices) {
                    //Concat array val
                    stringBuilder.append(scalesArray[scalesList[j]])
                    //Check condition
                    if (j != scalesList.size - 1) {
                        //When ja value not equal to scale list size -1
                        //Add comma
                        stringBuilder.append(", ")
                    }
                }
                checkScalesListSize()
                //Set text on text vie
                if (scalesList.size != 0) {
                    tvScales.text = stringBuilder.toString()
                } else {
                    tvScales.text = "Scales"
                }
            }
            builder.setNegativeButton("Cancel") { dialogInterface, i -> //Dismiss dialog
                checkScalesListSize()
                dialogInterface.dismiss()
            }
            // Clear selected scale
            builder.setNeutralButton("Clear All") { dialogInterface, i ->
                //Use for loop
                for (j in selectedScales.indices) {
                    //remove all selection
                    selectedScales[j] = false
                    scalesList.clear()
                    tvScales.text = "Scales"
                }
                checkScalesListSize()
            }
            //Show dialog
            builder.show()
        })
    }

    private fun checkScalesListSize() {
        // Disables other dropdowns if scales is selected
        if(scalesList.size>0){
            createIntervalsDropdown()
            tvIntervals.isEnabled = false
            // Re-enables other dropdowns if scales list is 0
        } else{
            tvIntervals.isEnabled = true
        }
    }
}