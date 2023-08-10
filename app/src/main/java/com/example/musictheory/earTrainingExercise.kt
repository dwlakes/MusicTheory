// TODO: Create other interval buttons

package com.example.musictheory

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.util.ArrayList

class earTrainingExercise : AppCompatActivity() {

    lateinit var min2Btn:Button
    lateinit var maj2ndBtn:Button
    lateinit var maj3Btn:Button
    lateinit var perfFourthBtn:Button
    lateinit var tritoneBtn:Button
    lateinit var perfFifthBtn:Button
    lateinit var min6Btn:Button
    lateinit var maj6Btn:Button
    lateinit var min7Btn:Button
    lateinit var maj7Btn:Button
    lateinit var octBtn:Button
    lateinit var repeatBtn:Button
    lateinit var resultsTv: TextView
    lateinit var accuracyTv: TextView
    var intervalAudioList = mutableListOf<Interval>()
    var guessCounter = 0
    var correctGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ear_training_exercise)

        min2Btn = findViewById(R.id.min2Btn)
        maj2ndBtn = findViewById(R.id.maj2Btn)
        maj3Btn = findViewById(R.id.maj3Btn)
        perfFourthBtn = findViewById(R.id.perf4Btn)
        tritoneBtn = findViewById(R.id.tritoneBtn)
        perfFifthBtn = findViewById(R.id.perf5Btn)
        min6Btn = findViewById(R.id.min6Btn)
        maj6Btn = findViewById(R.id.maj6Btn)
        min7Btn = findViewById(R.id.min7Btn)
        maj7Btn = findViewById(R.id.maj7Btn)
        octBtn = findViewById(R.id.octBtn)
        repeatBtn = findViewById(R.id.repeatBtn)
        resultsTv = findViewById(R.id.resultsTv)
        accuracyTv = findViewById(R.id.accuracyTv)

        val exerciseTV = findViewById<TextView>(R.id.intervalExerciseTV)
        var selectedIntervalStringList = mutableListOf<String>()

        val selectedIntervalInts = intent.getIntegerArrayListExtra("selected_intervals")
        println(selectedIntervalInts)

        selectedIntervalStringList = getIntervalsString(selectedIntervalInts)

        exerciseTV.text ="Intervals Selected: "+ selectedIntervalStringList.joinToString(", ")

        intervalAudioList = getAudioFiles(selectedIntervalStringList)
        selectSound(intervalAudioList)
    }

    private fun selectSound(intervalAudioList: MutableList<Interval>) {
        val randomAudio = intervalAudioList.random()
        playSound(randomAudio)

    }

    //gets interval audio files
    private fun getAudioFiles(selectedIntervalStringList: MutableList<String>): MutableList<Interval> {

        var audioObjectsList = mutableListOf<Interval>()

        // Iterates through each selected interval's subdirectory and gets appropriate
        // audio files
        for(element in selectedIntervalStringList){
            when(element){

                "Minor Second" -> assets.list("min2_ascending")?.forEach {
                    //println(it)
                    val min2Asc = Interval("Min2","Asc", "min2_ascending/$it")
                    //println(min2Asc.getInfo())
                    audioObjectsList.add(min2Asc)
                }
                "Major Second" -> assets.list("maj2_ascending")?.forEach {
                    //println(it)
                    val maj2Asc = Interval("Maj2","Asc", "maj2_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(maj2Asc)
                }
                "Tritone" -> assets.list("tritone_ascending")?.forEach {
                    //println(it)
                    val tritoneAsc = Interval("Tritone","Asc", "tritone_ascending/$it")
                    println(tritoneAsc.getInfo())
                    audioObjectsList.add(tritoneAsc)
                }

            }
        }
        return audioObjectsList
    }

    private fun getIntervalsString(selectedIntervalInts: ArrayList<Int>?): MutableList<String> {
        var intervalsArray = arrayOf(
            "Minor Second", "Major Second", "Minor Third", "Major Third",
            "Perfect Fourth", "Tritone"
        )
        val selectedIntervalList = mutableListOf<String>()

        // Gets selected index with matching interval
        if (selectedIntervalInts != null) {
            for (j in selectedIntervalInts.indices) {
                //Concat array val
                selectedIntervalList.add(intervalsArray[selectedIntervalInts[j]])
            }
        }
        return selectedIntervalList
    }

    fun playSound(selectedSound: Interval) {
        println(selectedSound.path)
        var mediaPlayer: MediaPlayer? = null
        val assetFilePath = selectedSound.path

        // Timer clips end of audio track, removing vocals
        val timer = object: CountDownTimer(3500, 1000) {
            override fun onTick(millisUntilFinished: Long) {println("start: $millisUntilFinished")}
            override fun onFinish() {
                mediaPlayer?.stop()
            }
        }
        timer.start()

        mediaPlayer = MediaPlayer().apply{
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
            } catch (e: IOException){
                e.printStackTrace()
            }

        }

        Toast.makeText(this, "Playing sound. . . .", Toast.LENGTH_SHORT).show()

        min2Btn.setOnClickListener {
            checkMin2(selectedSound)
        }
        maj2ndBtn.setOnClickListener{
            checkMaj2(selectedSound)
        }
        tritoneBtn.setOnClickListener{
            checkTritone(selectedSound)
        }
        repeatBtn.setOnClickListener {
            playSound(selectedSound)
        }
    }

    private fun checkMin2(selectedSound: Interval) {
        if (selectedSound.intervalType == "Min2"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }

    }

    private fun checkTritone(selectedSound: Interval) {
        if (selectedSound.intervalType == "Tritone"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMaj2(selectedSound: Interval) {
        if (selectedSound.intervalType == "Maj2"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }

    }

    private fun incorrectAns(selectedSound: Interval) {
        guessCounter++
        resultsTv.text = "Try again."
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        playSound(selectedSound)

    }

    private fun correctAns() {
        guessCounter++
        correctGuesses++
        resultsTv.text = "Correct!"
        accuracyTv.text = "Accuracy: $correctGuesses/$guessCounter"
        selectSound(intervalAudioList)
    }

}