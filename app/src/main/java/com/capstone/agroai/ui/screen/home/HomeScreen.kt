package com.capstone.agroai.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.capstone.agroai.R
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Montserrat
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary900

@Composable
fun HomeScreen(
    modifier : Modifier = Modifier
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    AgroAITheme {
        HomeScreen()
    }
}