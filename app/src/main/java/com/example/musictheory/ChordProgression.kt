package com.example.musictheory

class ChordProgression (var progressionType: String,  var path: String) {

    fun getInfo(): String {
        return "$progressionType, $path"
    }
}
