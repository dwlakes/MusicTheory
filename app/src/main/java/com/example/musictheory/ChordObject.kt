package com.example.musictheory

open class ChordObject(
    var root: String,
    var chordType: String,
    var notes: List<String>
){

    fun getChordInfo(): String {
        return "$root, $chordType, $notes"
    }

}