package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
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