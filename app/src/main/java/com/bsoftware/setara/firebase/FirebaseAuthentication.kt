package com.bsoftware.setara.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class FirebaseAuthentication {

    fun initFirebaseAuth() : FirebaseAuth{
        return Firebase.auth
    }

    fun createDataUser(
        email : String,
        password : String,
        onSuccess : () -> Unit = {},
        onFailed : () -> Unit = {},
        activity : Activity
    ){
        initFirebaseAuth().createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity){task ->
                if(task.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()

                    val exception = task.exception
                    Log.e("CreateDataUser() Exception : ", exception.toString())
                }
            }
    }

    fun loginDataUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
        activity: Activity
    ){
        initFirebaseAuth().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity){task ->
                if(task.isSuccessful){
                    onSuccess()
                } else {
                    onFailed()
                }
            }
    }


}