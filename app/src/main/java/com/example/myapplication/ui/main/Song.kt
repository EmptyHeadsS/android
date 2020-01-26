package com.example.myapplication.ui.main

import androidx.annotation.DrawableRes


class SongId {
    private lateinit var name: String
    private lateinit var id: String

    constructor(name: String, id: String) {
        this.name = name
        this.id = id
    }
}

class SongMeta {

    var songTitle: String
    var artist: String
    var imagePath: Int

    constructor(songTitle: String, artist: String, image: Int) {
        this.songTitle = songTitle
        this.artist = artist
        this.imagePath = image
    }
}


