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

class ChordCreatingMainMenu : AppCompatActivity() {

    lateinit var tvChordTypes: TextView
    lateinit var chordTypeDropDown:TextView

    lateinit var selectedChordTypes: BooleanArray
    var chordTypesList : MutableList<Int> = mutableListOf()
    var chordTypesArray = arrayOf(
        "Major","Minor","Diminished","Augmented","Major 7","Dominant 7","Minor 7",
        "Minor Major 7","Dominant 9","Dominant Minor 9", "Major 9"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chord_creating_main_menu)

        createChordTypesDropdown()

        val startExerciseBtn = findViewById<Button>(R.id.startChordCreatingExerciseBtn)
        startExerciseBtn.setOnClickListener{
            if(chordTypesList.size < 1){
                Toast.makeText(this, "You need to select something", Toast.LENGTH_SHORT).show()
                // Singletonlist cannot be cast to arraylist
            }else if (chordTypesList.size == 1){
                // Converts singleton to single variable
                val singleChord = chordTypesList[0]
                // Creates arraylist for single selection
                val selectedChordType = arrayListOf<Int>(singleChord)
                val intent = Intent(this, ChordCreatingExercise::class.java)
                intent.putIntegerArrayListExtra("selected_chords",selectedChordType)
                startActivity(intent)
            } else {
                //Copies mutable list to immutable arraylist
                //Kotlin can't pass a mutable list through activities
                val selectedChordType: ArrayList<Int> = chordTypesList.toList() as ArrayList<Int>
                val intent = Intent(this, ChordCreatingExercise::class.java)
                intent.putIntegerArrayListExtra("selected_chords",selectedChordType)
                startActivity(intent)
            }


        }
    }

    private fun createChordTypesDropdown() {
        // assign variable
        chordTypeDropDown = findViewById<TextView>(R.id.chordTypeDropdown)
        // initialize selected chord progression
        selectedChordTypes = BooleanArray(chordTypesArray.size)
        chordTypeDropDown.setOnClickListener(View.OnClickListener { //Initialize alert dialog                            //nead to change to earTrainingMainMenu
            val builder = AlertDialog.Builder(this@ChordCreatingMainMenu)

            //Set title
            builder.setTitle("Selected Chord Progressions")
            //Set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                chordTypesArray,
                selectedChordTypes
            ) { dialogInterface, i, b ->
                //Check condition
                if (b) {
                    //When checkbox selected
                    //Add position in chord progression list
                    chordTypesList.add(i)
                    //Sort
                    chordTypesList.sort()
                } else {
                    //When checkbox unselected
                    //Remove position from chord progression list
                    chordTypesList.remove(i)
                }
            }
            builder.setPositiveButton("Ok") { dialogInterface, i -> //Initalize string builder
                val stringBuilder = StringBuilder()

                // Gets selected index with matching scale
                for (j in chordTypesList.indices) {
                    //Concat array val
                    stringBuilder.append(chordTypesArray[chordTypesList[j]])
                    //Check condition
                    if (j != chordTypesList.size - 1) {
                        //When ja value not equal to chordProgressionsList list size -1
                        //Add comma
                        stringBuilder.append(", ")
                    }
                }
                //Set text on text vie
                if (chordTypesList.size != 0) {
                    chordTypeDropDown.text = stringBuilder.toString()
                } else {
                    chordTypeDropDown.text = "Chords"
                }
            }
            builder.setNegativeButton("Cancel") { dialogInterface, i -> //Dismiss dialog
                dialogInterface.dismiss()
            }
            // Clear selected Chord Progressions
            builder.setNeutralButton("Clear All") { dialogInterface, i ->
                //Use for loop
                for (j in selectedChordTypes.indices) {
                    //remove all selection
                    selectedChordTypes[j] = false
                    chordTypesList.clear()
                    chordTypeDropDown.text = "Chords"
                }
            }
            //Show dialog
            builder.show()
        })
    }

}