package com.example.mediastore_exifinterface.ui.home

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    var imageUri: Uri? = null
    var imageIsLoaded: Boolean = false

    fun loadImageFromMediaStore(getImageLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*" // Только изображения
        }
        getImageLauncher.launch(intent) // Открываем MediaStore
    }
}