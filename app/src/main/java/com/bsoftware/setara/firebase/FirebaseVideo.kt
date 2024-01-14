package com.bsoftware.setara.firebase

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

abstract class FirebaseVideo<T>(var reference : String) {
    private var databaseReference = Firebase.database.getReference(reference)

    fun getReference() : DatabaseReference {
        return databaseReference
    }

    @Composable
    abstract fun getVideoAll() : SnapshotStateList<T>

    @Composable
    abstract fun getVideoById(videoId : String) : SnapshotStateList<T>

    @Composable
    abstract fun setVideo() : SnapshotStateList<T>

}