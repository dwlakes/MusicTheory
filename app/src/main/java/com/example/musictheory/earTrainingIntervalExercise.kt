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
import java.io.IOException
import java.util.ArrayList

class earTrainingIntervalExercise : AppCompatActivity() {

    lateinit var min2Btn:Button
    lateinit var maj2Btn:Button
    lateinit var min3Btn:Button
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
        setContentView(R.layout.activity_ear_training_interval_exercise)

        min2Btn = findViewById(R.id.min2Btn)
        maj2Btn = findViewById(R.id.maj2Btn)
        min3Btn = findViewById(R.id.min3Btn)
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
        val randomAudio = intervalAudioList.shuffled().random()
        playSound(randomAudio)

    }

    //gets interval audio files
    private fun getAudioFiles(selectedIntervalStringList: MutableList<String>): MutableList<Interval> {

        var audioObjectsList = mutableListOf<Interval>()

        // Iterates through each selected interval's subdirectory and gets appropriate
        // audio files
        for(element in selectedIntervalStringList){
            when(element){

                "Minor Second" -> assets.list("ascending_intervals/min2_ascending")?.forEach {
                    //println(it)
                    val min2Asc = Interval("Min2","Asc", "ascending_intervals/min2_ascending/$it")
                    println("Info: " + min2Asc.getInfo())
                    audioObjectsList.add(min2Asc)
                }
                "Major Second" -> assets.list("ascending_intervals/maj2_ascending")?.forEach {
                    //println(it)
                    val maj2Asc = Interval("Maj2","Asc", "ascending_intervals/maj2_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(maj2Asc)
                }
                "Minor Third" -> assets.list("ascending_intervals/min3_ascending")?.forEach {
                    //println(it)
                    val min3Asc = Interval("Min3","Asc", "ascending_intervals/min3_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(min3Asc)
                }
                "Major Third" -> assets.list("ascending_intervals/maj3_ascending")?.forEach {
                    //println(it)
                    val maj3Asc = Interval("Maj3","Asc", "ascending_intervals/maj3_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(maj3Asc)
                }
                "Perfect Fourth" -> assets.list("ascending_intervals/perf4_ascending")?.forEach {
                    //println(it)
                    val perf4Asc = Interval("Perf4","Asc", "ascending_intervals/perf4_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(perf4Asc)
                }
                "Tritone" -> assets.list("ascending_intervals/tritone_ascending")?.forEach {
                    //println(it)
                    val tritoneAsc = Interval("Tritone","Asc", "ascending_intervals/tritone_ascending/$it")
                    println(tritoneAsc.getInfo())
                    audioObjectsList.add(tritoneAsc)
                }
                "Perfect Fifth" -> assets.list("ascending_intervals/perf5_ascending")?.forEach {
                    //println(it)
                    val perf5Asc = Interval("Perf5","Asc", "ascending_intervals/perf5_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(perf5Asc)
                }
                "Minor Sixth" -> assets.list("ascending_intervals/min6_ascending")?.forEach {
                    //println(it)
                    val min6Asc = Interval("Min6","Asc", "ascending_intervals/min6_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(min6Asc)
                }
                "Major Sixth" -> assets.list("ascending_intervals/maj6_ascending")?.forEach {
                    //println(it)
                    val maj6Asc = Interval("Maj6","Asc", "ascending_intervals/maj6_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(maj6Asc)
                }
                "Minor Seventh" -> assets.list("ascending_intervals/min7_ascending")?.forEach {
                    //println(it)
                    val min7Asc = Interval("Min7","Asc", "ascending_intervals/min7_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(min7Asc)
                }
                "Major Seventh" -> assets.list("ascending_intervals/maj7_ascending")?.forEach {
                    //println(it)
                    val maj7Asc = Interval("Maj7","Asc", "ascending_intervals/maj7_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(maj7Asc)
                }
                "Octave" -> assets.list("ascending_intervals/oct_ascending")?.forEach {
                    //println(it)
                    val octAsc = Interval("Oct","Asc", "ascending_intervals/oct_ascending/$it")
                    //println(maj2Asc.getInfo())
                    audioObjectsList.add(octAsc)
                }

            }
        }
        return audioObjectsList
    }

    private fun getIntervalsString(selectedIntervalInts: ArrayList<Int>?): MutableList<String> {
        var intervalsArray = arrayOf(
            "Minor Second", "Major Second", "Minor Third", "Major Third",
            "Perfect Fourth", "Tritone", "Perfect Fifth", "Minor Sixth",
            "Major Sixth", "Minor Seventh", "Major Seventh", "Octave"
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
                if (mediaPlayer?.isPlaying == true){
                    mediaPlayer?.stop()
                }

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
                println("Path: "+ selectedSound.path)
                playSound(selectedSound)
            }
            finally {
                mediaPlayer?.release()
            }

        }

        //Toast.makeText(this, "Playing sound. . . .", Toast.LENGTH_SHORT).show()

        min2Btn.setOnClickListener {
            checkMin2(selectedSound)
        }
        maj2Btn.setOnClickListener{
            checkMaj2(selectedSound)
        }
        min3Btn.setOnClickListener {
            checkMin3(selectedSound)
        }
        maj3Btn.setOnClickListener {
            checkMaj3(selectedSound)
        }
        perfFourthBtn.setOnClickListener {
            checkPerf4(selectedSound)
        }
        tritoneBtn.setOnClickListener{
            checkTritone(selectedSound)
        }
        perfFifthBtn.setOnClickListener {
            checkPerf5(selectedSound)
        }
        min6Btn.setOnClickListener {
            checkMin6(selectedSound)
        }
        maj6Btn.setOnClickListener {
            checkMaj6(selectedSound)
        }
        min7Btn.setOnClickListener {
            checkMin7(selectedSound)
        }
        maj7Btn.setOnClickListener {
            checkMaj7(selectedSound)
        }
        octBtn.setOnClickListener {
            checkOct(selectedSound)
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

    private fun checkMaj2(selectedSound: Interval) {
        if (selectedSound.intervalType == "Maj2"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }

    }

    private fun checkMin3(selectedSound: Interval) {
        if (selectedSound.intervalType == "Min3"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMaj3(selectedSound: Interval) {
        if (selectedSound.intervalType == "Maj3"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkPerf4(selectedSound: Interval) {
        if (selectedSound.intervalType == "Perf4"){
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

    private fun checkPerf5(selectedSound: Interval) {
        if (selectedSound.intervalType == "Perf5"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMin6(selectedSound: Interval) {
        if (selectedSound.intervalType == "Min6"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMaj6(selectedSound: Interval) {
        if (selectedSound.intervalType == "Maj6"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMin7(selectedSound: Interval) {
        if (selectedSound.intervalType == "Min7"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkMaj7(selectedSound: Interval) {
        if (selectedSound.intervalType == "Maj7"){
            correctAns()
        } else {
            incorrectAns(selectedSound)
        }
    }

    private fun checkOct(selectedSound: Interval) {
        if (selectedSound.intervalType == "Oct"){
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