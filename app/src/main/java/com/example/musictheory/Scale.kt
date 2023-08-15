package com.example.musictheory

class Scale (var scaleType: String,  var path: String) {

    fun getInfo(): String {
        return "$scaleType, $path"
    }
}
