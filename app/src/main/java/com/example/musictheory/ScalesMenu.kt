package com.example.musictheory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.lang.StringBuilder
import java.util.*

class ScalesMenu : AppCompatActivity() {
    lateinit var selectedScales: BooleanArray


    var scalesList : MutableList<Int> = mutableListOf()
    var scalesArray = arrayOf(
        "Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scales_menu)

        createScalesDropdown()
        val startExerciseBtn = findViewById<Button>(R.id.startScalesExerciseBtn)
        startExerciseBtn.setOnClickListener{
            if(scalesList.size < 1){
                Toast.makeText(this, "You need to select something", Toast.LENGTH_SHORT).show()
                // Singletonlist cannot be cast to arraylist
            }else if (scalesList.size == 1){
                    // Converts singleton to single variable
                    val singleScale = scalesList[0]
                    // Creates arraylist for single selection
                    val selectedScales = arrayListOf<Int>(singleScale)
                    val intent = Intent(this, ScaleCreatingExercise::class.java)
                    intent.putIntegerArrayListExtra("selected_scales",selectedScales)
                    startActivity(intent)
            } else {
                    //Copies mutable list to immutable arraylist
                    //Kotlin can't pass a mutable list through activities
                    val selectedScales: ArrayList<Int> = scalesList.toList() as ArrayList<Int>
                    val intent = Intent(this, ScaleCreatingExercise::class.java)
                    intent.putIntegerArrayListExtra("selected_scales",selectedScales)
                    startActivity(intent)
            }


        }
    }

    private fun createScalesDropdown() {
        // assign variable
        val tvScales = findViewById<TextView>(R.id.scalesTextView)
        // initialize selected scale
        selectedScales = BooleanArray(scalesArray.size)
        tvScales.setOnClickListener(View.OnClickListener { //Initialize alert dialog                            //nead to change to earTrainingMainMenu
            val builder = AlertDialog.Builder(this@ScalesMenu)

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
                //Set text on text vie
                tvScales.text = stringBuilder.toString()

            }
            builder.setNegativeButton("Cancel") { dialogInterface, i -> //Dismiss dialog
                dialogInterface.dismiss()
            }
            // Clear selected scale
            builder.setNeutralButton("Clear All") { dialogInterface, i ->
                //Use for loop
                for (j in selectedScales.indices) {
                    //remove all selection
                    selectedScales[j] = false
                    scalesList.clear()
                    tvScales.text = ""
                }
            }
            //Show dialog
            builder.show()
        })
    }
}