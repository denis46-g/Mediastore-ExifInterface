package com.example.mediastore_exifinterface.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediastore_exifinterface.MediastoreTopAppBar
import com.example.mediastore_exifinterface.R
import com.example.mediastore_exifinterface.ui.AppViewModelProvider
import com.example.mediastore_exifinterface.ui.navigation.NavigationDestination
import android.graphics.Matrix
import com.example.mediastore_exifinterface.ui.tags.exportDataFromMain
import com.example.mediastore_exifinterface.ui.tags.newUri
import com.example.mediastore_exifinterface.ui.tags.uriUpdated

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

var image_is_loaded = false

/**
 * Entry route for Home screen
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToExifTagsEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Используем состояние для отслеживания, загружено изображение или нет
    val imageIsLoaded = remember { mutableStateOf(viewModel.imageIsLoaded) }
    var imageUri by remember { mutableStateOf<Uri?>(viewModel.imageUri) } // URI загруженного изображения

    // EXIF данные
    var exifData by remember { mutableStateOf<ExifData?>(viewModel.exifData) }

    val getImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageUri = result.data?.data // Получаем URI изображения
            imageIsLoaded.value = true
            viewModel.imageUri = imageUri // Сохраняем URI в ViewModel
            viewModel.imageIsLoaded = imageIsLoaded.value // Сохраняем состояние загрузки в ViewModel

            imageUri?.let { uri ->
                // Получаем Bitmap и EXIF данные
                val data = getBitmapFromUri(uri, context)?.second
                exifData = data // Сохраняем EXIF данные для отображения
                viewModel.exifData = data
            }

            uriUpdated = false
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediastoreTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_large)))
                Column(
                    modifier = modifier.padding(dimensionResource(id = R.dimen.padding_extra_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    // Отображение изображения, если оно выбрано
                    if(!uriUpdated){
                        imageUri?.let { uri ->
                            val bitmap = getBitmapFromUri(uri, context)?.first // Получаем Bitmap
                            val imageBitmap = bitmap?.asImageBitmap() // Преобразуем в ImageBitmap
                            if (imageBitmap != null) {
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp) // Можно настроить высоту
                                        .padding(dimensionResource(id = R.dimen.padding_large)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        // Отображение EXIF данных, если они доступны
                        exifData?.let { data ->
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Date: ${data.creationDate ?: "No data"}")
                                Text("Latitude: ${data.latitude ?: "No data"}")
                                Text("Longitude: ${data.longitude ?: "No data"}")
                                Text("Device: ${data.device ?: "No data"}")
                                Text("Device model: ${data.model ?: "No data"}")
                            }
                        }
                    }
                    else{
                        val bitmap =
                            newUri?.let { getBitmapFromUri(it, context)?.first } // Получаем Bitmap
                        val imageBitmap = bitmap?.asImageBitmap() // Преобразуем в ImageBitmap
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Selected Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp) // Можно настроить высоту
                                    .padding(dimensionResource(id = R.dimen.padding_large)),
                                contentScale = ContentScale.Crop
                            )
                        }


                        // Отображение EXIF данных, если они доступны
                        exportDataFromMain?.second?.let { data ->
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text("Date: ${data.creationDate ?: "No data"}")
                                Text("Latitude: ${data.latitude ?: "No data"}")
                                Text("Longitude: ${data.longitude ?: "No data"}")
                                Text("Device: ${data.device ?: "No data"}")
                                Text("Device model: ${data.model ?: "No data"}")
                            }
                        }
                    }

                    if(imageIsLoaded.value || image_is_loaded) {
                        Button(
                            onClick = {
                                if(!uriUpdated){
                                    imageUri?.let { uri ->
                                        exportDataFromMain = getBitmapFromUri(uri, context)
                                    }
                                }
                                else{
                                    exportDataFromMain = newUri?.let { getBitmapFromUri(it, context) }
                                }
                                navigateToExifTagsEdit()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(stringResource(R.string.open_tags_editor))
                        }
                    }
                    Button(
                        onClick = {
                            viewModel.loadImageFromMediaStore(getImageLauncher)
                            image_is_loaded = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        if(!imageIsLoaded.value && !image_is_loaded)
                            Text(stringResource(R.string.load_image))
                        else
                            Text(stringResource(R.string.load_other_image))
                    }
                }
            }
        }
    ){
        /*HomeBody(
            //onItemClick = navigateToExifTagsEdit,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )*/
    }
}

data class ExifData(
    val creationDate: String?,
    val latitude: String?,
    val longitude: String?,
    val device: String?,
    val model: String?
)

fun getBitmapFromUri(uri: Uri, context: Context): Pair<Bitmap, ExifData>? {
    // Получаем Bitmap из URI
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)

    // Получаем EXIF данные для изображения
    val exif = ExifInterface(context.contentResolver.openInputStream(uri)!!)
    val creationDate = exif.getAttribute(ExifInterface.TAG_DATETIME) ?: "No data"   // Дата создания
    val latitude = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE) ?: "No data"    // Широта
    val longitude = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE) ?: "No data"   // Долгота
    val device = exif.getAttribute(ExifInterface.TAG_MAKE) ?: "No data"         // Устройство
    val model = exif.getAttribute(ExifInterface.TAG_MODEL) ?: "No data"              // Модель устройства

    val exifData = ExifData(creationDate, latitude, longitude, device, model)

    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    // Применяем поворот в зависимости от EXIF данных
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> Pair(rotateImage(bitmap, 90f), exifData)
        ExifInterface.ORIENTATION_ROTATE_180 -> Pair(rotateImage(bitmap, 180f), exifData)
        ExifInterface.ORIENTATION_ROTATE_270 -> Pair(rotateImage(bitmap, 270f), exifData)
        else -> Pair(bitmap, exifData) // Возвращаем без изменений
    }
}

private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix().apply {
        postRotate(angle) // Вращаем изображение
    }
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true) // Создаем новое изображение с примененной матрицей
}

@Composable
private fun HomeBody(
    //onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

}