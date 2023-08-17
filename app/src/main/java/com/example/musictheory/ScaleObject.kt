package com.example.musictheory

class ScaleObject (var root: String,var scaleType: String,  var notes: List<String>) {

    fun getInfo(): String {
        return "$root,$scaleType,$notes"
    }

}
