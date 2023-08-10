package com.example.musictheory

class Interval(var intervalType: String, var direction: String, var path: String) {

    fun getInfo(): String {
        return "$intervalType, $direction, $path"
    }
}