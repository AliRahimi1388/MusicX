package com.example.musicx.data.remote

import com.example.musicx.common.Constants.SONG_COLLECTION
import com.example.musicx.data.entities.Song
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MusicDatabaseImpl @Inject constructor(
     firestore: FirebaseFirestore
){

    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<Song> {
        return try {
            songCollection.get().await().toObjects(Song::class.java)
        } catch (e: Exception) {
            emptyList<Song>()
        }
    }
}