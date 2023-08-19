//TODO: started on the scale building module.
// Need to make different chords and add the checkboxes for notes

package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

import com.example.musictheory.R.id.chordCreatorCheckAnsBtn


class ChordCreatingExercise : AppCompatActivity() {

    lateinit var taskTextView: TextView
    private val selectedNotes = mutableListOf<String>()
    var chordObjectsList = mutableListOf<ChordObject>()
    lateinit var randomChord:ChordObject

    lateinit var accuracyTv: TextView
    lateinit var resultsTv:TextView

    var guessCounter = 0
    var correctGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chord_creating_exercise)

        val selectedChordInts = intent.getIntegerArrayListExtra("selected_chords")


        val checkAnsBtn = findViewById<Button>(R.id.chordCreatorCheckAnsBtn)


        var selectedChordsStringList = mutableListOf<String>()

        selectedChordsStringList = getChordsString(selectedChordInts)

        val exerciseTV = findViewById<TextView>(R.id.chordCreatorExerciseTV)

        taskTextView = findViewById<TextView>(R.id.chordCreatorTaskTextView)

        accuracyTv = findViewById(R.id.accuracyChordCreatorTv)
        resultsTv = findViewById(R.id.resultsChordCreatorTv)

        exerciseTV.text ="Scales Selected: "+ selectedChordsStringList.joinToString(", ")

        chordObjectsList = getSelectedChords(selectedChordsStringList)

        getRandomChord()

        initializeCheckBoxes()

        checkAnsBtn.setOnClickListener {
            checkAnswer()
        }

    }

    private fun checkAnswer() {
        if(selectedNotes.size == randomChord.notes.size){
            checkChordNotes()
        }else if (selectedNotes.size > randomChord.notes.size){
            Toast.makeText(this, "You have too many notes", Toast.LENGTH_SHORT).show()
            incorrectAns()
        }else{
            Toast.makeText(this, "You don't have enough notes", Toast.LENGTH_SHORT).show()
            incorrectAns()
        }
    }

    private fun checkChordNotes() {
        var isCorrect = true
        println("Selected Scale: " + randomChord.root + " "+randomChord.chordType)
        println(randomChord.notes)
        for (element in selectedNotes) {
            println("In selected notes: $element")
            if (element !in randomChord.notes) {
                println("incorrect note: $element")
                isCorrect = false
                break
            }
        }
        if(isCorrect){
            correctAns()
        } else {
            incorrectAns()
        }

    }

    private fun correctAns() {
        guessCounter++
        correctGuesses++
        resultsTv.text = "Correct!"
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        initializeCheckBoxes()
        getRandomChord()
    }

    private fun incorrectAns() {
        guessCounter++
        resultsTv.text = "Try again."
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"

    }

    // Chat GPT Function
    private fun initializeCheckBoxes() {
        //Get all ID of checkboxes
        val checkBoxIds = arrayOf(
            R.id.cChordCreatorBtn, R.id.cChordCreatorBtn, R.id.dChordCreatorBtn, R.id.dSharpChordCreatorBtn,
            R.id.eChordCreatorBtn, R.id.fChordCreatorBtn, R.id.fSharpChordCreatorBtn, R.id.gChordCreatorBtn,
            R.id.gSharpChordCreatorBtn, R.id.aChordCreatorBtn, R.id.aSharpChordCreatorBtn, R.id.bChordCreatorBtn,
            R.id.cFlatChordCreatorBtn, R.id.dFlatChordCreatorBtn, R.id.eFlatChordCreatorBtn, R.id.fFlatChordCreatorBtn,
            R.id.gFlatChordCreatorBtn, R.id.aFlatChordCreatorBtn, R.id.bFlatChordCreatorBtn, R.id.eSharpChordCreatorBtn,
            R.id.bSharpChordCreatorBtn,R.id.cSharpChordCreatorBtn
        )

        //iterate through each ID and add an onClickListener
        for (checkBoxId in checkBoxIds) {
            val checkBox = findViewById<CheckBox>(checkBoxId)
            checkBox.isChecked = false
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val note = checkBox.text.toString()
                // Add/remove selected note to array as appropriate
                if (isChecked) {
                    selectedNotes.add(note)
                } else {
                    selectedNotes.remove(note)
                }
                println("Selected notes:$selectedNotes")
            }
        }
    }


    private fun getRandomChord() {
        randomChord = chordObjectsList.shuffled().random()
        taskTextView.text = "Create: " +randomChord.root +" "+randomChord.chordType
        println("Minor Chord Info : "+ randomChord.getChordInfo())
    }

    private fun getSelectedChords(selectedChordsStringList: MutableList<String>): MutableList<ChordObject> {
        chordObjectsList = mutableListOf()

        // Iterates through each selected scales's subdirectory and gets appropriate
        // audio files
        //println(selectedScalesStringList)
        for (element in selectedChordsStringList) {
            when (element) {
                "Major" -> getMajorChords()
                "Minor" -> getMinorChords()
                // TODO: Diminished Needs harmonic minor scale
                //"Diminished" -> getDiminishedChords()
                "Major 7" -> getMajor7Chords()
                "Dominant 7" -> getDom7Chords()
                "Minor 7" -> getMinor7Chords()
                // TODO: MinMaj 7 needs harmonic minor
                //"Minor Major 7" -> getMinorMajor7Chords()
                "Dominant 9" -> getDom9Chords()
                // TODO: DomMin 9 needs harmonic minor
                //"Dominant Minor 9" -> getDomMin9Chords()
                "Minor 9" -> getMinor9Chords()
                "Major 9" -> getMajor9Chords()


            }
        }
        return chordObjectsList
    }

    private fun getMajor9Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        // takes appropriate scale degrees from each scale, assigns it to chordObject,
        // also makes the appropriate root note.
        for(scale in majorScales){
            var maj9Chord = ChordObject(scale.notes[0],"Major 9", listOf(
                scale.notes[0], scale.notes[2],scale.notes[4],scale.notes[6], scale.notes[1]))

            chordObjectsList.add(maj9Chord)
        }
    }

    private fun getMinor9Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>
        for(scale in majorScales){
            var minor9Chord = ChordObject(scale.notes[5],"Minor 9", listOf(
                scale.notes[5], scale.notes[0],scale.notes[2],scale.notes[4],scale.notes[6]))

            chordObjectsList.add(minor9Chord)
        }
    }

    // TODO: Base this chord off of the harmonic minor scale
    private fun getDomMin9Chords() {
        TODO("Not yet implemented")
    }

    private fun getDom9Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>
        for(scale in majorScales){
            var dom9Chord = ChordObject(scale.notes[4],"Dominant 9", listOf(
                scale.notes[4], scale.notes[6],scale.notes[1],scale.notes[3],scale.notes[5]))

            chordObjectsList.add(dom9Chord)
        }
    }

    // TODO: Base this chord off of the harmonic minor scale
    private fun getMinorMajor7Chords() {
        TODO("Not yet implemented")
    }

    private fun getMinor7Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>
        for(scale in majorScales){
            var minor7Chord = ChordObject(scale.notes[5],"Minor 7", listOf(
                scale.notes[5], scale.notes[0],scale.notes[2],scale.notes[4]))

            chordObjectsList.add(minor7Chord)
        }
    }

    private fun getDom7Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>
        for(scale in majorScales){
            var dom7Chord = ChordObject(scale.notes[4],"Dominant 7", listOf(
                scale.notes[4], scale.notes[6],scale.notes[1],scale.notes[3]))

            chordObjectsList.add(dom7Chord)
        }
    }

    private fun getMajor7Chords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        // takes appropriate scale degrees from each scale, assigns it to chordObject,
        // also makes the appropriate root note.
        for(scale in majorScales){
            var maj7Chord = ChordObject(scale.notes[0],"Major 7", listOf(
                scale.notes[0], scale.notes[2],scale.notes[4],scale.notes[6]))

            chordObjectsList.add(maj7Chord)
        }
    }

    // TODO: Base this chord off of the harmonic minor scale
    private fun getDiminishedChords() {

    }

    private fun getMinorChords() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        // takes appropriate scale degrees from each scale, assigns it to chordObject,
        // also makes the appropriate root note.
        for(scale in majorScales){
            var minorChord = ChordObject(scale.notes[5],"Minor", listOf(
                scale.notes[5], scale.notes[0],scale.notes[2]))

            chordObjectsList.add(minorChord)
        }
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
            "Minor Major 7","Dominant 9","Dominant Minor 9","Minor 9", "Major 9"
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

    private fun getHarmMinorScales(): Any{

        var aHarmMinor = ScaleObject("A","Harmonic Minor", listOf("A","B","C","D","E","F","G#"))

        return listOf(aHarmMinor)



    }

    private fun getMajorScales(): Any {
        var cIonian = ScaleObject("C", "Ionian", listOf("C", "D", "E", "F", "G", "A", "B"))
        var cSharpIonian =
            ScaleObject("C#", "Ionian", listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#"))
        var dFlatIonian =
            ScaleObject("D♭", "Ionian", listOf("D♭", "E♭", "F", "G♭", "A♭", "B♭","C"))
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