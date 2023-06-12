package com.capstone.agroai.ui.screen.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.content.ContextCompat
import com.capstone.agroai.R
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Montserrat
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary600
import com.capstone.agroai.ui.theme.Primary900

fun checkCameraPermission(
    context: Context,
    cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>,
    launcher: ManagedActivityResultLauncher<String, Boolean>
) {
    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        // Open camera because permission is already granted
        Log.i("agroai", "Permission previously granted")
        cameraLauncher.launch()
    } else {
        // Request a permission
        launcher.launch(Manifest.permission.CAMERA)
    }
}

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier,
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }
    var rowSize by remember { mutableStateOf(Size.Zero) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) {
        bitmap = it
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if(isGranted) {
            Log.i("agroai", "camera permission granted")
            cameraLauncher.launch()
        } else {
            Log.i("agroai", "camera permission denied")
        }
    }

    val items = listOf("Jagung", "Tomat", "Padi", "Teh", "Kentang", "Cabai", "Kacang")

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
            if(bitmap != null) {
                Image(
                    bitmap = bitmap?.asImageBitmap()!!,
                    contentDescription = null,
                    modifier = modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
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
            onClick = {
                checkCameraPermission(context, cameraLauncher, launcher)
            },
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
        HomeScreen()
    }
}