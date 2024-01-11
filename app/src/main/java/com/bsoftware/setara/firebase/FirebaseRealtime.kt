package com.bsoftware.setara.firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bsoftware.setara.dataClass.VideoSoftSkillDataClass
import com.bsoftware.setara.getuuid.GetUuid
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class FirebaseRealtime {
    private var databaseReference = Firebase.database.getReference("VideoSelfImprove")

    fun getReference() : DatabaseReference{
        return databaseReference
    }

    fun writeDataVideo(title : String, subtitle : String){
        val getuuId = GetUuid().getUuidCode()
        val videoData = VideoSoftSkillDataClass(getuuId,title, subtitle)
        databaseReference.child("VideoSoftSkill").child(getuuId).setValue(videoData)
            .addOnSuccessListener {
                Log.d("WriteDataVideo() response", "Data Complete Insert")
            }
            .addOnFailureListener {e ->
                Log.e("WriteDataVideo() Error Response", e.toString())
            }
    }



    @Composable
    fun getDataVideoWithId(videoId : String) : SnapshotStateList<VideoSoftSkillDataClass>{
        val dataList = remember { mutableStateListOf<VideoSoftSkillDataClass>() }
        val getDataId = databaseReference.child(videoId)

        LaunchedEffect(databaseReference){
            getDataId.addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val videoSoftSkillDataClass = VideoSoftSkillDataClass(
                        videoId = snapshot.child("videoId").getValue(String::class.java),
                        title = snapshot.child("title").getValue(String::class.java),
                        subtitle = snapshot.child("subtitle").getValue(String::class.java),
                        link = snapshot.child("link").getValue(String::class.java),
                        thumbnail = snapshot.child("thumbnail").getValue(String::class.java)
                    )
                    dataList.add(videoSoftSkillDataClass)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("getDataVideoWithId() Error", error.toString())
                }

            })
        }
        return dataList
    }
}