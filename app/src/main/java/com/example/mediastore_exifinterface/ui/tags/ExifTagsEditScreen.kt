package com.example.mediastore_exifinterface.ui.tags

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mediastore_exifinterface.MediastoreTopAppBar
import com.example.mediastore_exifinterface.R
import com.example.mediastore_exifinterface.ui.AppViewModelProvider
import com.example.mediastore_exifinterface.ui.home.ExifData
import com.example.mediastore_exifinterface.ui.navigation.NavigationDestination
import java.io.File

object ExifTagsEditDestination : NavigationDestination {
    override val route = "exif_tags_edit"
    override val titleRes = R.string.exif_tags_edit
}

var exportDataFromMain: Pair<Bitmap, ExifData>? = null
var uriUpdated: Boolean = false
var newUri: Uri? = null

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExifTagsEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExifTagsEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val context = LocalContext.current

    //if(uriUpdated)
       // uriUpdated = false

    // Создайте состояние для текста каждого из полей EXIF
    val creationDate = remember { mutableStateOf(exportDataFromMain?.second?.creationDate ?: "No data") }
    val latitude = remember { mutableStateOf(exportDataFromMain?.second?.latitude ?: "No data") }
    val longitude = remember { mutableStateOf(exportDataFromMain?.second?.longitude ?: "No data") }
    val device = remember { mutableStateOf(exportDataFromMain?.second?.device ?: "No data") }
    val model = remember { mutableStateOf(exportDataFromMain?.second?.model ?: "No data") }

    val newFileName by viewModel.newFilename.observeAsState(initial = "")

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediastoreTopAppBar(
                title = stringResource(ExifTagsEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp,
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
                    OutlinedTextField(
                        value = creationDate.value,
                        onValueChange = { creationDate.value = it },
                        label = { Text(stringResource(R.string.creation_date)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = latitude.value,
                        onValueChange = { latitude.value = it },
                        label = { Text(stringResource(R.string.latitude)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = longitude.value,
                        onValueChange = { longitude.value = it },
                        label = { Text(stringResource(R.string.longitude)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = device.value,
                        onValueChange = { device.value = it },
                        label = { Text(stringResource(R.string.device)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = model.value,
                        onValueChange = { model.value = it },
                        label = { Text(stringResource(R.string.device_model)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            exportDataFromMain = exportDataFromMain?.let {
                                Pair(
                                    it.first,
                                    ExifData(
                                        creationDate = creationDate.value,
                                        latitude = latitude.value,
                                        longitude = longitude.value,
                                        device = device.value,
                                        model = model.value
                                    ))
                            }
                            viewModel.saveImage(context, exportDataFromMain)

                            // Если нужно, установите новый URI при получении нового имени файла
                            /*if (newFileName.isNotEmpty()) {
                                newUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", File(newFileName))
                                uriUpdated = true
                            }*/
                            navigateBack()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(stringResource(R.string.save_image))
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