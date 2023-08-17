package com.example.musictheory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import org.w3c.dom.Text

//TODO: Make objects for other modes
class ScaleCreatingExercise : AppCompatActivity() {

    lateinit var taskTextView:TextView
    private val selectedNotes = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_creating_exercise)

        val exerciseTV = findViewById<TextView>(R.id.scaleCreatorExerciseTV)
        taskTextView = findViewById<TextView>(R.id.taskTextView)

        var selectedScalesStringList = mutableListOf<String>()

        val selectedScaleInts = intent.getIntegerArrayListExtra("selected_scales")
        //println(selectedScaleInts)
        selectedScalesStringList = getScalesString(selectedScaleInts)

        exerciseTV.text ="Scales Selected: "+ selectedScalesStringList.joinToString(", ")

        var scaleObjectList = getSelectedScales(selectedScalesStringList)

        getRandomScale(scaleObjectList)

        initializeCheckBoxes()


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

    private fun getRandomScale(scaleObjectList: MutableList<ScaleObject>) {
        val randomScale = scaleObjectList.random()
        taskTextView.text = "Create: " +randomScale.root +" "+randomScale.scaleType
    }

    private fun getSelectedScales(selectedScalesStringList: MutableList<String>): MutableList<ScaleObject> {
        var scaleObjectsList = mutableListOf<ScaleObject>()

        // Iterates through each selected scales's subdirectory and gets appropriate
        // audio files
        //println(selectedScalesStringList)
        for (element in selectedScalesStringList) {
            when (element) {
                "Ionian" -> getIonianScales(scaleObjectsList)
                "Dorian" -> getDorianScales(scaleObjectsList)
            }
        }
        return scaleObjectsList
    }

    private fun getDorianScales(scaleObjectsList: MutableList<ScaleObject>) {
        var dDorian = ScaleObject("D","Dorian", listOf("C","D","E","F","G","A","B"))
    }
    private fun getIonianScales(scaleObjectsList: MutableList<ScaleObject>) {
        var cIonian = ScaleObject("C","Ionian", listOf("C","D","E","F","G","A","B"))
        var cSharpIonian = ScaleObject("C#","Ionian", listOf("C#","D#","E#","F#","G#","A#","B#"))
        var dIonian = ScaleObject("D","Ionian", listOf("D","E","F#","G","A","B","C#"))
        var eFlatIonian = ScaleObject("E♭","Ionian", listOf("E♭","F","G","A♭","B♭","C","D"))
        var eIonian = ScaleObject("E","Ionian", listOf("E","F#","G#","A","B","C#","D#"))
        var fIonian = ScaleObject("F","Ionian", listOf("F","G","A","B♭","C","D","E",))
        var fSharpIonian = ScaleObject("F#","Ionian", listOf("F#","G#","A#","B","C#","D#","E#",))
        var gFlatIonian = ScaleObject("G♭","Ionian", listOf("G♭","A♭","B♭","C♭","D♭","E♭","F"))
        var gIonian = ScaleObject("G","Ionian", listOf("G","A","B","C","D","E","F#"))
        var aIonian = ScaleObject("A","Ionian", listOf("A","B","C#","D","E","F#","G#"))
        var bFlatIonian = ScaleObject("B♭","Ionian", listOf("B♭","C","D","E♭","F","G","A"))
        var bIonian = ScaleObject("B","Ionian", listOf("B","C#","D#","E","F#","G#","A#"))
        var cFlatIonian = ScaleObject("C♭","Ionian", listOf("C♭","D♭","E♭","F♭","G♭","A♭","B♭"))

        scaleObjectsList.addAll(listOf(cIonian,cSharpIonian,dIonian,eFlatIonian,eIonian,fIonian,
        fSharpIonian,gFlatIonian,gIonian,aIonian,bFlatIonian,bIonian,cFlatIonian))
        //println("List Size: "+scaleObjectsList.size)
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


