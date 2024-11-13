package com.example.mediastore_exifinterface.ui.tags

import android.content.Context
import android.graphics.Bitmap
import android.media.ExifInterface
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediastore_exifinterface.ui.home.ExifData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ExifTagsEditViewModel : ViewModel() {

    // Используем LiveData для хранения имени файла
    private val _newFilename = MutableLiveData<String>()
    val newFilename: LiveData<String> get() = _newFilename // Expose as LiveData

    fun saveImage(context: Context, exportData: Pair<Bitmap, ExifData>?) {
        viewModelScope.launch(Dispatchers.IO) { // Запускаем код в фоновом потоке
            exportData?.let { (bitmap, exifData) ->
                // Путь к файлу для сохранения изображения
                val filename = "${System.currentTimeMillis()}.jpg" // Генерация уникального имени

                //val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

                val storageDir = "/storage/emulated/0/DCIM"

                // Создайте файл
                val imageFile = File(storageDir, filename)

                // Сохранение изображения в файл
                try {
                    FileOutputStream(imageFile).use { out ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out) // Сжимаем и сохраняем изображение
                    }

                    // Обновление содержимого медиа (галереи)
                    MediaScannerConnection.scanFile(context, arrayOf(imageFile.absolutePath), null) { _, _ -> }

                    // Теперь обновляем EXIF-теги
                    saveExifData(imageFile.absolutePath, exifData)

                    _newFilename.postValue(imageFile.absolutePath)
                } catch (e: IOException) {
                    e.printStackTrace() // Обработка ошибок сохранения
                }
            }
        }
    }

    private fun saveExifData(imagePath: String, exifData: ExifData) {
        try {
            val exif = ExifInterface(imagePath)
            exif.setAttribute(ExifInterface.TAG_DATETIME, exifData.creationDate)
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, exifData.latitude)
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, exifData.longitude)
            exif.setAttribute(ExifInterface.TAG_MAKE, exifData.device)
            exif.setAttribute(ExifInterface.TAG_MODEL, exifData.model)
            exif.saveAttributes() // Сохраняем изменения
        } catch (e: IOException) {
            e.printStackTrace() // Обработка ошибок записи EXIF
        }
    }
}
