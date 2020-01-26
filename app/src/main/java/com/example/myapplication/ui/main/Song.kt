package com.example.myapplication.ui.main


class SongId {
    private lateinit var name: String
    private lateinit var id: String

    constructor(name: String, id: String) {
        this.name = name
        this.id = id
    }
}

class Song {

    private lateinit var name: String
    private lateinit var artist: String
    private lateinit var id: String
    private lateinit var imagePath: String

    constructor(name: String, artist: String, id: String, imagePath: String) {
        this.name = name
        this.id = id
        this.artist = artist
        this.imagePath = imagePath
    }
}


