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

class earTrainingChordProgessionExercise : AppCompatActivity() {

    var chordProgressionAudioList = mutableListOf<ChordProgression>()

    lateinit var I_IV_V_Btn: Button
    lateinit var I_VI_vi_IV_Btn: Button
    lateinit var ii_V_I_Btn: Button
    lateinit var vi_IV_I_V_Btn: Button
    lateinit var I_vi_IV_V_Btn: Button
    lateinit var I_III_IV_iv_Btn: Button

    lateinit var repeatBtn: Button

    lateinit var accuracyTv: TextView
    lateinit var resultsTv:TextView

    var guessCounter = 0
    var correctGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_chord_progession_exercise)

        I_IV_V_Btn = findViewById(R.id.I_IV_VBtn)
        I_VI_vi_IV_Btn = findViewById(R.id.I_VI_vi_IVBtn)
        ii_V_I_Btn = findViewById(R.id.ii_V_IBtn)
        vi_IV_I_V_Btn = findViewById(R.id.vi_IV_I_VBtn)
        I_vi_IV_V_Btn = findViewById(R.id.I_vi_IV_VBtn)
        I_III_IV_iv_Btn = findViewById(R.id.I_III_IV_ivBtn)

        repeatBtn = findViewById(R.id.repeatChordProgExerciseBtn)

        accuracyTv = findViewById(R.id.accuracyChordProgressionTv)
        resultsTv = findViewById(R.id.resultsChordProgressionTv)

        val exerciseTV = findViewById<TextView>(R.id.chordProgressionExerciseTV)
        var selectedChordProgressionStringList = mutableListOf<String>()

        val selectedChordProgressionInts = intent.getIntegerArrayListExtra("selected_chord_progressions")

        selectedChordProgressionStringList = getChordProgressionString(selectedChordProgressionInts)

        exerciseTV.text ="Chord Progressions Selected: "+ selectedChordProgressionStringList.joinToString(", ")
        chordProgressionAudioList = getAudioFiles(selectedChordProgressionStringList)

        selectSound(chordProgressionAudioList)

    }

    private fun selectSound(chordProgressionAudioList: MutableList<ChordProgression>) {
        val randomAudio = chordProgressionAudioList.shuffled().random()
        playSound(randomAudio)
    }

    private fun getAudioFiles(selectedChordProgressionStringList: MutableList<String>): MutableList<ChordProgression> {
        var audioObjectsList = mutableListOf<ChordProgression>()

        // Iterates through each selected chord progression's subdirectory and gets appropriate
        // audio files
        for (element in selectedChordProgressionStringList) {
            when (element) {
                "I-IV-V" -> assets.list("chord_progressions/I_IV_V")?.forEach {
                    val I_IV_V_Chord = ChordProgression("I-IV-V", "chord_progressions/I_IV_V/$it")
                    audioObjectsList.add(I_IV_V_Chord)
                }
                "I-VI-vi-IV" -> assets.list("chord_progressions/I_VI_vi_IV")?.forEach {
                    val I_VI_vi_IV_Chord =
                        ChordProgression("I-VI-vi-IV", "chord_progressions/I_VI_vi_IV/$it")
                    audioObjectsList.add(I_VI_vi_IV_Chord)
                }
                "ii-V-I" -> assets.list("chord_progressions/ii_V_I")?.forEach {
                    val ii_V_I_Chord = ChordProgression("ii-V-I", "chord_progressions/ii_V_I/$it")
                    audioObjectsList.add(ii_V_I_Chord)
                }
                "vi-IV-I-V" -> assets.list("chord_progressions/vi_IV_I_V")?.forEach {
                    val vi_IV_I_V_Chord = ChordProgression("vi-IV-I-V", "chord_progressions/vi_IV_I_V/$it")
                    audioObjectsList.add(vi_IV_I_V_Chord)
                }
                "I-vi-IV-V" -> assets.list("chord_progressions/I_vi_IV_V")?.forEach {
                    val I_vi_IV_V_Chord = ChordProgression("I-vi-IV-V", "chord_progressions/I_vi_IV_V/$it")
                    audioObjectsList.add(I_vi_IV_V_Chord)
                }
                "I-III-IV-iv"-> assets.list("chord_progressions/I_III_IV_iv")?.forEach {
                    val I_III_IV_iv_Chord = ChordProgression("I-III-IV-iv", "chord_progressions/I_III_IV_iv/$it")
                    audioObjectsList.add(I_III_IV_iv_Chord)
                }
            }
        }
        return audioObjectsList
    }

    private fun getChordProgressionString(selectedChordProgressionInts: ArrayList<Int>?): MutableList<String> {
        var chordProgressionsArray = arrayOf(
            "ii-V-I","vi-IV-I-V","I-IV-V","I-VI-vi-IV","I-vi-IV-V","I-III-IV-iv"
        )
        val selectedChordProgressionList = mutableListOf<String>()
        // Gets selected index with matching chord progression
        if (selectedChordProgressionInts != null) {
            for (j in selectedChordProgressionInts.indices) {
                //Concat array val
                selectedChordProgressionList.add(chordProgressionsArray[selectedChordProgressionInts[j]])
            }
        }
        return selectedChordProgressionList
    }
    private fun playSound(selectedSound: ChordProgression) {
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
        ii_V_I_Btn.setOnClickListener {
            var selectedAnswer = ii_V_I_Btn.text.toString()
            checkAnswer(selectedAnswer,selectedSound)
        }
        vi_IV_I_V_Btn.setOnClickListener {
            var selectedAnswer = vi_IV_I_V_Btn.text.toString()
            checkAnswer(selectedAnswer,selectedSound)
        }
        I_IV_V_Btn.setOnClickListener {
            var selectedAnswer = I_IV_V_Btn.text.toString()
            checkAnswer(selectedAnswer,selectedSound)
        }
        I_VI_vi_IV_Btn.setOnClickListener {
            var selectedAnswer = I_VI_vi_IV_Btn.text.toString()
            checkAnswer(selectedAnswer,selectedSound)
        }
        I_vi_IV_V_Btn.setOnClickListener {
            var selectedAnswer = I_vi_IV_V_Btn.text.toString()
            checkAnswer(selectedAnswer,selectedSound)
        }
        I_III_IV_iv_Btn.setOnClickListener {
            var selectedAnswer = I_III_IV_iv_Btn.text.toString()
            println("Answer: $selectedAnswer")
            println("Real Type: "+selectedSound.progressionType)
            checkAnswer(selectedAnswer,selectedSound)
        }
        repeatBtn.setOnClickListener {
            playSound(selectedSound)
        }
    }

    private fun checkAnswer(selectedAnswer: String, selectedSound: ChordProgression) {
        if (selectedSound.progressionType == selectedAnswer){
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
        selectSound(chordProgressionAudioList)
    }

    private fun incorrectAns(selectedSound: ChordProgression) {
        guessCounter++
        resultsTv.text = "Try again."
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        //playSound(selectedSound)

    }


}