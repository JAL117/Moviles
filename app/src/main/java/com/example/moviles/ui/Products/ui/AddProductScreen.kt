package com.example.moviles.ui.Products.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.moviles.apiService.RetroClient
import androidx.compose.material3.CircularProgressIndicator
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavHostController) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val viewModel: AddProductViewModel = viewModel(
        factory = object : ViewModelProvider.Factory{
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return AddProductViewModel(RetroClient.instance) as T
            }
        }
    )


    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.data?.let { uri ->
            productImageUri = uri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            productImageUri = bitmapToUri(context,bitmap)
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your app.
        } else {
            // Explain to the user that the feature is unavailable because the features requires a permission that the user has denied.
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Agregar Nuevo Producto",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            if(viewModel.isLoading){
                CircularProgressIndicator()
            }
            if (viewModel.error != null) {
                Text(text = "Error: ${viewModel.error}", color = MaterialTheme.colorScheme.error)
            }
            if(viewModel.success){
                Text(text = "Producto agregado con éxito", color = MaterialTheme.colorScheme.primary)
            }
            Card(modifier = Modifier
                .fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                Column(modifier = Modifier.padding(16.dp)){
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Nombre", color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,

                            ),
                        shape = RoundedCornerShape(12.dp)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = productPrice,
                        onValueChange = { productPrice = it },
                        label = { Text("Precio", color = MaterialTheme.colorScheme.onBackground) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,


                            ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (productImageUri != null) {
                        Image(
                            painter = rememberImagePainter(data = productImageUri),
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.CenterHorizontally) ,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                galleryLauncher.launch(intent)
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Seleccionar Imagen",  color = MaterialTheme.colorScheme.onPrimary )
                        }

                        Button(
                            onClick = {
                                //Check if camera permission is granted
                                if (ActivityCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.CAMERA
                                    )
                                    != android.content.pm.PackageManager.PERMISSION_GRANTED
                                ) {
                                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                                } else {
                                    cameraLauncher.launch(null)
                                }
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Tomar Foto", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.addProduct(productName,productPrice,productImageUri,context)

                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Agregar Producto", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            ) {
                Text(text = "Regresar",  color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

        }
    }
}


fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()
    val file = File(cachePath, "productImage.jpg")
    var stream: OutputStream? = null
    try {
        stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    } catch (e: Exception) {
        e.printStackTrace()
        return Uri.EMPTY
    } finally {
        stream?.close()
    }
    return  Uri.fromFile(file)
}