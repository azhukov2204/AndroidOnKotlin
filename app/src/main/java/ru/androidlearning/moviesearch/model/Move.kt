package ru.androidlearning.moviesearch.model

import android.net.Uri
import java.util.*

data class Move(
    //val moveId: Int,  //вероятно в будущем это свойство пригодится
    val name: String,
    val releaseDate: Date?,
    val rating: Float,
    val posterUri: Uri?
)
