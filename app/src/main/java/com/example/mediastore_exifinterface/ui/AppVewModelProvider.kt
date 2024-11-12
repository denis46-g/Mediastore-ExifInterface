package com.example.mediastore_exifinterface.ui

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mediastore_exifinterface.ui.home.HomeViewModel
import com.example.mediastore_exifinterface.ui.tags.ExifTagsEditViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for TagsEditViewModel
        initializer {
            ExifTagsEditViewModel(
                //this.createSavedStateHandle()
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel()
        }
    }
}

