package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

//TODO: Make objects for other modes
class ScaleCreatingExercise : AppCompatActivity() {

    lateinit var taskTextView:TextView
    private val selectedNotes = mutableListOf<String>()
    lateinit var randomScale:ScaleObject

    var scaleObjectsList = mutableListOf<ScaleObject>()

    lateinit var accuracyTv: TextView
    lateinit var resultsTv:TextView

    var guessCounter = 0
    var correctGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_creating_exercise)

        val checkAnsBtn = findViewById<Button>(R.id.scaleCreatorCheckAnsBtn)

        val exerciseTV = findViewById<TextView>(R.id.scaleCreatorExerciseTV)
        taskTextView = findViewById<TextView>(R.id.taskTextView)

        accuracyTv = findViewById(R.id.accuracyScaleCreatorTv)
        resultsTv = findViewById(R.id.resultsScaleCreatorTv)

        var selectedScalesStringList = mutableListOf<String>()

        val selectedScaleInts = intent.getIntegerArrayListExtra("selected_scales")
        //println(selectedScaleInts)
        selectedScalesStringList = getScalesString(selectedScaleInts)

        exerciseTV.text ="Scales Selected: "+ selectedScalesStringList.joinToString(", ")

        scaleObjectsList = getSelectedScales(selectedScalesStringList)

        getRandomScale()

        initializeCheckBoxes()

        checkAnsBtn.setOnClickListener {
            checkAnswer()
        }

    }

    private fun checkAnswer() {
        if(selectedNotes.size == randomScale.notes.size){
            checkScaleNotes()
        }else if (selectedNotes.size > randomScale.notes.size){
            Toast.makeText(this, "You have too many notes", Toast.LENGTH_SHORT).show()
            incorrectAns()
        }else{
            Toast.makeText(this, "You don't have enough notes", Toast.LENGTH_SHORT).show()
            incorrectAns()
        }
    }

    // Checks to make sure each selected note is in the scale
    private fun checkScaleNotes() {
        var isCorrect = true
        println("Selected Scale: " + randomScale.root + " "+randomScale.scaleType)
        println(randomScale.notes)
        for (element in selectedNotes) {
            println("In selected notes: $element")
            if (element !in randomScale.notes) {
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
        getRandomScale()
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
            R.id.cBtn, R.id.cSharpScaleBtn, R.id.dBtn, R.id.dSharpBtn, R.id.eBtn, R.id.fBtn,
            R.id.fSharpBtn, R.id.gBtn, R.id.gSharpBtn, R.id.aBtn, R.id.aSharpBtn, R.id.bBtn,
            R.id.cFlatBtn, R.id.dFlatBtn, R.id.eFlatBtn, R.id.fFlatBtn, R.id.gFlatBtn,
            R.id.aFlatBtn, R.id.bFlatBtn, R.id.eSharpBtn, R.id.bSharpBtn
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
                //println("Selected notes:$selectedNotes")
            }
        }
    }

    private fun getRandomScale() {
        randomScale = scaleObjectsList.shuffled().random()
        taskTextView.text = "Create: " +randomScale.root +" "+randomScale.scaleType
    }

    private fun getSelectedScales(selectedScalesStringList: MutableList<String>): MutableList<ScaleObject> {
        scaleObjectsList = mutableListOf()

        // Iterates through each selected scales's subdirectory and gets appropriate
        // audio files
        //println(selectedScalesStringList)
        for (element in selectedScalesStringList) {
            when (element) {
                "Ionian" -> getIonianScales()
                "Dorian" -> getDorianScales()
                "Phrygian" -> getPhrygianScales()
                "Lydian" -> getLydianScales()
                "Mixolydian" -> getMixolydianScales()
                "Aeolian" -> getAeolianScales()
                "Locrian" -> getLocrianScales()
            }
        }
        return scaleObjectsList
    }

    private fun getLocrianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var locrianScale = ScaleObject(scale.notes[6],"Locrian",scale.notes)
            scaleObjectsList.add(locrianScale)
        }
    }

    private fun getAeolianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var aeolianScale = ScaleObject(scale.notes[5],"Aeolian",scale.notes)
            scaleObjectsList.add(aeolianScale)
        }
    }

    private fun getMixolydianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var mixolydianScale = ScaleObject(scale.notes[4],"Mixolydian",scale.notes)
            scaleObjectsList.add(mixolydianScale)
        }
    }

    private fun getLydianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var lydianScale = ScaleObject(scale.notes[3],"Lydian",scale.notes)
            scaleObjectsList.add(lydianScale)
        }

    }

    private fun getPhrygianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var phrygianScale = ScaleObject(scale.notes[2],"Phrygian",scale.notes)
            scaleObjectsList.add(phrygianScale)
        }

    }

    private fun getDorianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var dorianScale = ScaleObject(scale.notes[1],"Dorian",scale.notes)
            scaleObjectsList.add(dorianScale)
        }
    }
    private fun getIonianScales() {
        var majorScales: List<ScaleObject> = getMajorScales() as List<ScaleObject>

        for(scale in majorScales){
            var ionianScale = ScaleObject(scale.notes[0],"Ionian",scale.notes)
            scaleObjectsList.add(ionianScale)
        }
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

    private fun getScalesString(selectedScaleInts: ArrayList<Int>?): MutableList<String> {
        var scalesArray = arrayOf(
            "Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian"
        )
        val selectedScaleList = mutableListOf<String>()
        // Gets selected index with matching scale
        if (selectedScaleInts != null) {
            for (j in selectedScaleInts.indices) {
                //Concat array val
                selectedScaleList.add(scalesArray[selectedScaleInts[j]])
            }
        }
        return selectedScaleList
    }


}


