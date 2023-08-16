package com.example.musictheory

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.IOException

class earTrainingScalesExercise : AppCompatActivity() {

    var scalesAudioList = mutableListOf<Scale>()

    lateinit var ionianBtn:Button
    lateinit var dorianBtn:Button
    lateinit var phrygianBtn:Button
    lateinit var lydianBtn:Button
    lateinit var mixolydianBtn:Button
    lateinit var aeolianBtn:Button
    lateinit var locrianBtn:Button
    lateinit var harmMinBtn:Button
    lateinit var melMinBtn:Button
    lateinit var minPentBtn:Button
    lateinit var majPentBtn:Button
    lateinit var bluesBtn:Button
    lateinit var wholeToneBtn:Button
    lateinit var persianBtn:Button
    lateinit var ukrDorianBtn:Button
    lateinit var repeatBtn:Button

    lateinit var accuracyTv: TextView
    lateinit var resultsTv:TextView

    var guessCounter = 0
    var correctGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_scales_exercise)

        ionianBtn = findViewById(R.id.ionianBtn)
        dorianBtn = findViewById(R.id.dorianBtn)
        phrygianBtn = findViewById(R.id.phrygianBtn)
        lydianBtn = findViewById(R.id.lydianBtn)
        mixolydianBtn = findViewById(R.id.mixolydianBtn)
        aeolianBtn = findViewById(R.id.aeolianBtn)
        locrianBtn = findViewById(R.id.locrianBtn)
        harmMinBtn = findViewById(R.id.harmonicMinorBtn)
        melMinBtn = findViewById(R.id.melodicMinorBtn)
        minPentBtn = findViewById(R.id.minorPentBtn)
        majPentBtn = findViewById(R.id.majorPentatonicBtn)
        bluesBtn = findViewById(R.id.bluesBtn)
        wholeToneBtn = findViewById(R.id.wholeToneBtn)
        persianBtn = findViewById(R.id.persianBtn)
        ukrDorianBtn = findViewById(R.id.ukrainianDorianBtn)
        accuracyTv = findViewById(R.id.accuracyTv)
        resultsTv = findViewById(R.id.resultsTv)
        repeatBtn = findViewById(R.id.repeatEarTrainingScalesBtn)

        val exerciseTV = findViewById<TextView>(R.id.scaleExerciseTV)
        var selectedScalesStringList = mutableListOf<String>()

        val selectedScaleInts = intent.getIntegerArrayListExtra("selected_scales")
        //println(selectedScaleInts)

        selectedScalesStringList = getScalesString(selectedScaleInts)

        exerciseTV.text ="Scales Selected: "+ selectedScalesStringList.joinToString(", ")

        scalesAudioList = getAudioFiles(selectedScalesStringList)
        selectSound(scalesAudioList)
    }

    private fun selectSound(scalesAudioList: MutableList<Scale>) {
        val randomAudio = scalesAudioList.shuffled().random()
        playSound(randomAudio)

    }

    private fun getAudioFiles(selectedScalesStringList: MutableList<String>): MutableList<Scale> {
        var audioObjectsList = mutableListOf<Scale>()

        // Iterates through each selected scales's subdirectory and gets appropriate
        // audio files
        //println(selectedScalesStringList)
        for(element in selectedScalesStringList){
            when(element) {
                "Ionian" -> assets.list("scales/maj_scales")?.forEach {
                    val majScale = Scale("Ionian", "scales/maj_scales/$it")
                    audioObjectsList.add(majScale)
                }
                "Dorian" -> assets.list("scales/dorian_scales")?.forEach {
                    val dorianScale = Scale("Dorian", "scales/dorian_scales/$it")
                    audioObjectsList.add(dorianScale)
                }
                "Phrygian" -> assets.list("scales/phrygian_scales")?.forEach {
                    val phrygianScale = Scale("Phrygian", "scales/phrygian_scales/$it")
                    audioObjectsList.add(phrygianScale)
                }
                "Lydian" -> assets.list("scales/lydian_scales")?.forEach {
                    val lydianScale = Scale("Lydian", "scales/lydian_scales/$it")
                    audioObjectsList.add(lydianScale)
                }
                "Mixolydian"-> assets.list("scales/mixolydian_scales")?.forEach {
                    val mixolydianScale = Scale("Mixolydian", "scales/mixolydian_scales/$it")
                    audioObjectsList.add(mixolydianScale)
                }
                "Aeolian" -> assets.list("scales/min_scales")?.forEach {
                    val minScale = Scale("Aeolian", "scales/min_scales/$it")
                    audioObjectsList.add(minScale)
                }
                "Locrian" -> assets.list("scales/locrian_scales")?.forEach {
                    val locrianScale = Scale("Locrian", "scales/locrian_scales/$it")
                    audioObjectsList.add(locrianScale)
                }
                "Harmonic Minor" -> assets.list("scales/harm_minor_scales")?.forEach {
                    val harmMinScale = Scale("Harmonic Minor", "scales/harm_minor_scales/$it")
                    audioObjectsList.add(harmMinScale)
                }
                "Melodic Minor" -> assets.list("scales/mel_minor_scales")?.forEach {
                    val melMinScale = Scale("Melodic Minor", "scales/mel_minor_scales/$it")
                    audioObjectsList.add(melMinScale)
                }
                "Minor Pentatonic" -> assets.list("scales/min_pent_scales")?.forEach {
                    val minPentScale = Scale("Minor Pentatonic", "scales/min_pent_scales/$it")
                    audioObjectsList.add(minPentScale)
                }
                "Major Pentatonic" -> assets.list("scales/maj_pent_scales")?.forEach {
                    val majPentScale = Scale("Major Pentatonic", "scales/maj_pent_scales/$it")
                    audioObjectsList.add(majPentScale)
                }
                "Blues" -> assets.list("scales/blues_scales")?.forEach {
                    val bluesScale = Scale("Blues", "scales/blues_scales/$it")
                    audioObjectsList.add(bluesScale)
                }
                "Whole Tone" -> assets.list("scales/whole_tone_scales")?.forEach {
                    val wholeToneScale = Scale("Whole Tone", "scales/whole_tone_scales/$it")
                    audioObjectsList.add(wholeToneScale)
                }
                "Persian"-> assets.list("scales/persian_scales")?.forEach {
                    val persianScale = Scale("Persian", "scales/persian_scales/$it")
                    audioObjectsList.add(persianScale)
                }
                "Ukrainian Dorian"-> assets.list("scales/ukr_dor_scales")?.forEach {
                    val ukrDorScale = Scale("Ukrainian Dorian", "scales/ukr_dor_scales/$it")
                    audioObjectsList.add(ukrDorScale)
                }
            }
        }
        return audioObjectsList
    }

    private fun getScalesString(selectedScaleInts: ArrayList<Int>?): MutableList<String> {
        var scalesArray = arrayOf(
            "Ionian", "Dorian", "Phrygian", "Lydian", "Mixolydian", "Aeolian", "Locrian",
            "Harmonic Minor", "Melodic Minor", "Minor Pentatonic", "Major Pentatonic",
            "Blues", "Whole Tone", "Persian", "Ukrainian Dorian"
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

    fun playSound(selectedSound: Scale) {
        var mediaPlayer: MediaPlayer? = null
        val assetFilePath = selectedSound.path


        mediaPlayer = MediaPlayer().apply {
            try {
                val assetManager: AssetManager = assets
                val assetFileDescriptor: AssetFileDescriptor = assetManager.openFd(assetFilePath)

                setDataSource(
                    assetFileDescriptor.fileDescriptor,
                    assetFileDescriptor.startOffset,
                    assetFileDescriptor.length
                )

                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                prepare()
                start()
            } catch (e: IOException) {
                e.printStackTrace()
                playSound(selectedSound)
            } finally {
                mediaPlayer?.release()
            }

        }

        ukrDorianBtn.setOnClickListener {
            checkUkrDor(selectedSound)
        }
        persianBtn.setOnClickListener {
            checkPersian(selectedSound)
        }
        wholeToneBtn.setOnClickListener {
            checkWholeTone(selectedSound)
        }
        bluesBtn.setOnClickListener {
            checkBlues(selectedSound)
        }
        majPentBtn.setOnClickListener {
            checkMajPent(selectedSound)
        }
        minPentBtn.setOnClickListener {
            checkMinPent(selectedSound)
        }
        melMinBtn.setOnClickListener {
            checkMelMinor(selectedSound)
        }
        harmMinBtn.setOnClickListener {
            checkHarmMin(selectedSound)
        }
        locrianBtn.setOnClickListener {
            checkLocrian(selectedSound)
        }
        aeolianBtn.setOnClickListener {
            checkAeolian(selectedSound)
        }
        mixolydianBtn.setOnClickListener {
            checkMixolydian(selectedSound)
        }
        lydianBtn.setOnClickListener {
            checkLydian(selectedSound)
        }
        phrygianBtn.setOnClickListener {
            checkPhrygian(selectedSound)
        }
        dorianBtn.setOnClickListener {
            checkDorian(selectedSound)
        }
        ionianBtn.setOnClickListener {
            checkIonian(selectedSound)
        }
        repeatBtn.setOnClickListener {
            playSound(selectedSound)
        }

    }

    private fun checkUkrDor(selectedSound: Scale) {
        if (selectedSound.scaleType == "Ukrainian Dorian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkPersian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Persian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkWholeTone(selectedSound: Scale) {
        if (selectedSound.scaleType == "Whole Tone"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkBlues(selectedSound: Scale) {
        if (selectedSound.scaleType == "Blues"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMajPent(selectedSound: Scale) {
        if (selectedSound.scaleType == "Major Pentatonic"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMinPent(selectedSound: Scale) {
        if (selectedSound.scaleType == "Minor Pentatonic"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMelMinor(selectedSound: Scale) {
        if (selectedSound.scaleType == "Melodic Minor"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkHarmMin(selectedSound: Scale) {
        if (selectedSound.scaleType == "Harmonic Minor"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkLocrian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Locrian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkAeolian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Aeolian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }
    private fun checkMixolydian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Mixolydian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }
    private fun checkLydian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Lydian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }

    }
    private fun checkPhrygian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Phrygian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkDorian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Dorian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkIonian(selectedSound: Scale) {
        if (selectedSound.scaleType == "Ionian"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun correctAns() {
        guessCounter++
        correctGuesses++
        resultsTv.text = "Correct!"
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        selectSound(scalesAudioList)
    }

    private fun incorrectAns(selectedSound: Scale) {
        guessCounter++
        resultsTv.text = "Try again."
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        //playSound(selectedSound)

    }

}