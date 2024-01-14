package com.bsoftware.setara.firebase

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.bsoftware.setara.dataClass.ArticleDataClass
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class FirebaseArticle {
    private var databaseReference = Firebase.database.getReference("ArticleHardSkill")

    @Composable
    fun getArticleAll() : SnapshotStateList<ArticleDataClass>{
        val articleList = remember{ mutableStateListOf<ArticleDataClass>() }

        LaunchedEffect(databaseReference) {
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        articleList.clear()

                        for (projectSnapshot in snapshot.children) {
                            val dataMap = projectSnapshot.value as? Map<*, *>?

                            if (dataMap != null) {
                                val articleDataClass = ArticleDataClass(
                                    articleId = dataMap["articleId"] as? String ?: "",
                                    title = dataMap["title"] as? String ?: "",
                                    thumbnail = dataMap["thumbnail"] as? String ?: "",
                                    article = dataMap["article"] as? String ?: "",
                                    imageArticle = dataMap["imageArticle"] as? String ?: ""
                                )
                                articleList.add(articleDataClass)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("getAllArticle() Error", error.toString())
                }

            }
            databaseReference.addValueEventListener(postListener)
        }

        return articleList
    }

    @Composable
    fun getArticleById(articleId : String) : SnapshotStateList<ArticleDataClass>{
        val articleList = remember { mutableStateListOf<ArticleDataClass>() }
        val getDataId = databaseReference.child(articleId)

        LaunchedEffect(databaseReference){
            getDataId.addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val articleDataClass = ArticleDataClass(
                        articleId = snapshot.child("articleId").getValue(String::class.java),
                        title = snapshot.child("title").getValue(String::class.java),
                        thumbnail = snapshot.child("thumbnail").getValue(String::class.java),
                        article = snapshot.child("article").getValue(String::class.java),
                        imageArticle = snapshot.child("imageArticle").getValue(String::class.java),
                    )
                    articleList.add(articleDataClass)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("getArticleById() Error", error.toString())
                }

            })
        }
        return articleList
    }
}