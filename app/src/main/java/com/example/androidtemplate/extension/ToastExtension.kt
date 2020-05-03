package com.example.androidtemplate.extension

import android.widget.Toast
import com.example.androidtemplate.MainApplication

fun Toast.informMessage(message: String){
    Toast.makeText(MainApplication.appContext, message, Toast.LENGTH_LONG).show()
}