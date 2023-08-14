package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class earTrainingScalesExercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_scales_exercise)

        val exerciseTV = findViewById<TextView>(R.id.scaleExerciseTV)
        var selectedScalesStringList = mutableListOf<String>()

        val selectedScaleInts = intent.getIntegerArrayListExtra("selected_intervals")
        println(selectedScaleInts)

        selectedScalesStringList = getScalesString(selectedScaleInts)

        exerciseTV.text ="Scales Selected: "+ selectedScalesStringList.joinToString(", ")

    }

    private fun getScalesString(selectedScaleInts: ArrayList<Int>?): MutableList<String> {
        var scalesArray = arrayOf(
            "Ionian", "Dorian", "Phrygian", "Lydian",
            "Mixolydian", "Aeolian", "Locrian"
        )
        val selectedScaleList = mutableListOf<String>()

        // Gets selected index with matching interval
        if (selectedScaleInts != null) {
            for (j in selectedScaleInts.indices) {
                //Concat array val
                selectedScaleList.add(scalesArray[selectedScaleInts[j]])
            }
        }
        return selectedScaleList
    }
}