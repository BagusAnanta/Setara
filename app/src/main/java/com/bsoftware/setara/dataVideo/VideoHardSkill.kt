package com.bsoftware.setara.dataVideo

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bsoftware.setara.dataClass.VideoHardSkillDataClass
import com.bsoftware.setara.dataClass.VideoSoftSkillDataClass
import com.bsoftware.setara.firebase.FirebaseVideo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class VideoHardSkill : FirebaseVideo<VideoHardSkillDataClass>("VideoHardSkill") {

    @Composable
    override fun getVideoAll(): SnapshotStateList<VideoHardSkillDataClass> {
        val dataList = remember { mutableStateListOf<VideoHardSkillDataClass>() }

        LaunchedEffect(getReference()) {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        dataList.clear()

                        for (projectSnapshot in snapshot.children) {
                            val dataMap = projectSnapshot.value as? Map<*, *>?

                            if (dataMap != null) {
                                val videoHardSkillDataClass = VideoHardSkillDataClass(
                                    videoId = dataMap["videoId"] as? String ?: "",
                                    title = dataMap["title"] as? String ?: "",
                                    subtitle = dataMap["subtitle"] as? String ?: "",
                                    link = dataMap["link"] as? String ?: "",
                                    thumbnail = dataMap["thumbnail"] as? String ?: ""
                                )
                                dataList.add(videoHardSkillDataClass)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("getAllDataVideo() Error", error.toString())
                }

            }
            getReference().addValueEventListener(postListener)
        }

        return dataList
    }

    @Composable
    override fun getVideoById(videoId : String): SnapshotStateList<VideoHardSkillDataClass> {
        val dataList = remember { mutableStateListOf<VideoHardSkillDataClass>() }
        val getDataId = getReference().child(videoId)

        LaunchedEffect(getReference()){
            getDataId.addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val videoHardSkillDataClass = VideoHardSkillDataClass(
                        videoId = snapshot.child("videoId").getValue(String::class.java),
                        title = snapshot.child("title").getValue(String::class.java),
                        subtitle = snapshot.child("subtitle").getValue(String::class.java),
                        link = snapshot.child("link").getValue(String::class.java),
                        thumbnail = snapshot.child("thumbnail").getValue(String::class.java)
                    )
                    dataList.add(videoHardSkillDataClass)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("getDataVideoWithId() Error", error.toString())
                }

            })
        }
        return dataList
    }

    @Composable
    override fun setVideo(): SnapshotStateList<VideoHardSkillDataClass> {
        TODO("Not yet implemented")
    }
}