package com.capstone.agroai.ui.screen.home

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.capstone.agroai.CameraView
import com.capstone.agroai.R
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Montserrat
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary600
import com.capstone.agroai.ui.theme.Primary900
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HomeScreen : ComponentActivity() {
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private lateinit var photoUri: Uri
    private var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            Log.i("permission", "Permission Granted")
            shouldShowCamera.value = true
        } else {
            Log.i("permission", "Permission Denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if(shouldShowCamera.value) {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = ::handleImageCapture,
                    onError = { Log.e("agroai", "View error: ", it) }
                )
            }

            AgroAITheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    HomeContent(
                        onRequestCamera = { requestCameraPermission() },
                        shouldShowPhoto = shouldShowPhoto,
                        photoUri = photoUri
                    )
                }
            }
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED -> {
                Log.i("permission", "Permission already granted")
                shouldShowCamera.value = true
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> Log.i("permission", "Show camera permission dialog")

            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        Log.i("agroai", "Image Captured: $uri")
        shouldShowCamera.value = false

        photoUri = uri
        shouldShowPhoto.value = true
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}

@Composable
fun HomeContent(
    onRequestCamera: () -> Unit,
    modifier : Modifier = Modifier,
    shouldShowPhoto: MutableState<Boolean>,
    photoUri: Uri
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Jagung", "Tomat", "Padi", "Teh", "Kentang", "Cabai", "Kacang")
    var selectedIndex by remember { mutableStateOf(0) }
    var rowSize by remember { mutableStateOf(Size.Zero) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(Primary900)
    ) {
        Text(
            text = stringResource(R.string.display_name),
            fontFamily = Libre,
            fontSize = 30.sp,
            color = Color.White,
            modifier = modifier.padding(top = 30.dp)
        )

        Text(
            text = "Ambil gambar untuk memindai\npenyakit tanaman.",
            fontFamily = Libre,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            lineHeight = 17.sp,
            color = Color.White,
            modifier = modifier.padding(top = 80.dp)
        )

        Box(modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopStart)
            .padding(horizontal = 30.dp, vertical = 12.dp)
            .clickable(onClick = { expanded = true })
            .onGloballyPositioned { layoutCoordinates ->
                rowSize = layoutCoordinates.size.toSize()
            }
        ) {
            Text(
                text = items[selectedIndex],
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = modifier
                    .fillMaxWidth()
                    .background(Primary400)
                    .padding(12.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = modifier
                    .background(Primary400)
                    .width(with(LocalDensity.current) { rowSize.width.toDp() })
            ) {
                items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = s,
                                color = Color.White,
                                fontFamily = Montserrat,
                            )
                        },
                        onClick = {
                            selectedIndex = index
                            expanded = false
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 12.dp)
            )
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 30.dp)
                .background(Primary600)
        ) {
            if(shouldShowPhoto.value) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = null,
                    modifier = modifier.fillMaxSize()
                )
            } else {
                Text(
                    text = "Your Image",
                    fontFamily = Montserrat,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }
        }

        Button(
            onClick = { onRequestCamera() },
            colors = ButtonDefaults.textButtonColors(
                containerColor = Primary400
            ),
            shape = RectangleShape,
            modifier = modifier
                .padding(vertical = 10.dp, horizontal = 30.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "KAMERA",
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                color = Color.White
            )
        }

        Button(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.White
            ),
            shape = RectangleShape,
            modifier = modifier
                .padding(bottom = 50.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = "GAMBAR DARI GALERI",
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                color = Primary400
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 50.dp)
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Primary400
                ),
                shape = RectangleShape,
                modifier = modifier
                    .padding(vertical = 10.dp, horizontal = 30.dp)
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "PINDAI PENYAKIT",
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    AgroAITheme {
        HomeContent(
            onRequestCamera = {},
            shouldShowPhoto = remember {
                mutableStateOf(false)
            },
            photoUri = Uri.EMPTY
        )
    }
}