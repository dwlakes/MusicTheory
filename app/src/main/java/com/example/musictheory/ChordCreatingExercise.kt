package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ChordCreatingExercise : AppCompatActivity() {

    lateinit var taskTextView: TextView
    var chordObjectsList = mutableListOf<ChordObject>()
    lateinit var randomChord:ChordObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chord_creating_exercise)

        val selectedChordInts = intent.getIntegerArrayListExtra("selected_chords")

        var selectedChordsStringList = mutableListOf<String>()

        selectedChordsStringList = getChordsString(selectedChordInts)

        val exerciseTV = findViewById<TextView>(R.id.chordCreatorExerciseTV)

        taskTextView = findViewById<TextView>(R.id.chordCreatorTaskTextView)

        exerciseTV.text ="Scales Selected: "+ selectedChordsStringList.joinToString(", ")

        chordObjectsList = getSelectedChords(selectedChordsStringList)

        getRandomChord()
    }

    private fun getRandomChord() {
        randomChord = chordObjectsList.shuffled().random()
        taskTextView.text = "Create: " +randomChord.root +" "+randomChord.chordType
        println(randomChord.getChordInfo())
    }

    private fun getSelectedChords(selectedChordsStringList: MutableList<String>): MutableList<ChordObject> {
        chordObjectsList = mutableListOf()

        // Iterates through each selected scales's subdirectory and gets appropriate
        // audio files
        //println(selectedScalesStringList)
        for (element in selectedChordsStringList) {
            when (element) {
                "Major" -> getMajorChords()
            }
        }
        return chordObjectsList
    }

    private fun getMajorChords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        // takes appropriate scale degrees from each scale, assigns it to chordObject,
        // also makes the appropriate root note.
        for(scale in majorScales){
            var majorChord = ChordObject(scale.notes[0],"Major", listOf(
                scale.notes[0], scale.notes[2],scale.notes[4]))

            chordObjectsList.add(majorChord)
        }
    }

    private fun getChordsString(selectedChordInts: ArrayList<Int>?): MutableList<String> {

        var scalesArray = arrayOf(
            "Major","Minor","Diminished","Augmented","Major 7","Dominant 7","Minor 7",
            "Minor Major 7","Dominant 9","Dominant Minor 9", "Major 9"
        )
        val selectedScaleList = mutableListOf<String>()
        // Gets selected index with matching scale
        if (selectedChordInts != null) {
            for (j in selectedChordInts.indices) {
                //Concat array val
                selectedScaleList.add(scalesArray[selectedChordInts[j]])
            }
        }
        return selectedScaleList

    }

    private fun getMajorScales(): Any {
        var cIonian = ScaleObject("C", "Ionian", listOf("C", "D", "E", "F", "G", "A", "B"))
        var cSharpIonian =
            ScaleObject("C#", "Ionian", listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#"))
        var dFlatIonian =
            ScaleObject("D♭", "Ionian", listOf("D♭", "E♭", "F♭", "G♭", "A♭", "B♭","C"))
        var dIonian = ScaleObject("D", "Ionian", listOf("D", "E", "F#", "G", "A", "B", "C#"))
        var eFlatIonian = ScaleObject("E♭", "Ionian", listOf("E♭", "F", "G", "A♭", "B♭", "C", "D"))
        var eIonian = ScaleObject("E", "Ionian", listOf("E", "F#", "G#", "A", "B", "C#", "D#"))
        var fIonian = ScaleObject("F", "Ionian", listOf("F", "G", "A", "B♭", "C", "D", "E",))
        var fSharpIonian =
            ScaleObject("F#", "Ionian", listOf("F#", "G#", "A#", "B", "C#", "D#", "E#",))
        var gFlatIonian =
            ScaleObject("G♭", "Ionian", listOf("G♭", "A♭", "B♭", "C♭", "D♭", "E♭", "F"))
        var gIonian = ScaleObject("G", "Ionian", listOf("G", "A", "B", "C", "D", "E", "F#"))
        var aIonian = ScaleObject("A", "Ionian", listOf("A", "B", "C#", "D", "E", "F#", "G#"))
        var bFlatIonian = ScaleObject("B♭", "Ionian", listOf("B♭", "C", "D", "E♭", "F", "G", "A"))
        var bIonian = ScaleObject("B", "Ionian", listOf("B", "C#", "D#", "E", "F#", "G#", "A#"))
        var cFlatIonian =
            ScaleObject("C♭", "Ionian", listOf("C♭", "D♭", "E♭", "F♭", "G♭", "A♭", "B♭"))

        return listOf(
            cIonian, cSharpIonian, dIonian, eFlatIonian, eIonian, dFlatIonian,
            fIonian, fSharpIonian, gFlatIonian, gIonian, aIonian, bFlatIonian, bIonian, cFlatIonian
        )
    }
}