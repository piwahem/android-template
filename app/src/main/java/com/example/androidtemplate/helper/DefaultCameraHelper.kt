package com.example.androidtemplate.helper

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class DefaultCameraHelper(val activity: Context) {

    private var takePhotoUri: Uri? = null

    fun reset(){
        takePhotoUri = null
    }

    fun startDefaultCamera() {
        takePhotoUri = createImageUri()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, takePhotoUri)
        (activity as? Fragment)?.startActivityForResult(intent, REQUEST_CODE_TAKE_A_PHOTO)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_TAKE_A_PHOTO && resultCode == Activity.RESULT_OK) {
            takePhotoUri?.let {
                (activity as? OnDefaultCameraCaptureListener)?.onCaptureDefaultCamera(it)
            }
        }
    }

    private fun createImageUri(): Uri? {
        val contentResolver = activity?.contentResolver
        val cv = ContentValues()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        cv.put(MediaStore.Images.Media.TITLE, timeStamp)
        return contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv)
    }

    companion object{
        const val REQUEST_CODE_TAKE_A_PHOTO = 107
    }

    interface OnDefaultCameraCaptureListener{
        fun onCaptureDefaultCamera(uri: Uri)
    }
}