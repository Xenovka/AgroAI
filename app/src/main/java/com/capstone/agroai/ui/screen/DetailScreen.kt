package com.capstone.agroai.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.capstone.agroai.R
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary800
import com.capstone.agroai.ui.theme.Primary900

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .wrapContentHeight()
            .background(Primary900)
            .verticalScroll(state = scrollState)
    ) {
        Box {
            Button(
                onClick = {},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary400
                ),
                contentPadding = PaddingValues(0.dp),
                modifier = modifier
                    .size(90.dp)
                    .padding(15.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back))
            }

            AsyncImage(
                model = "https://www.cropscience.bayer.us/-/media/Bayer-CropScience/Country-United-States-Internet/Images/Learning-Center/Articles/corn-diseases-threaten-yields/mature-lesions-from-gray-leaf-spot.ashx?h=200&iar=0&w=300&hash=8B1235871D07237E8808DEE271CC6322",
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(15.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Wheat Usarium Head Blight",
                textAlign = TextAlign.Center,
                lineHeight = 30.sp,
                fontFamily = Libre,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )

            Column(
                modifier = modifier
                    .background(Primary800)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.disease_description, "Wheat Usarium Head Blight"),
                    fontFamily = Libre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = stringResource(R.string.lorem),
                    fontFamily = Libre,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color.White,
                )
            }

            Column(
                modifier = modifier
                    .background(Primary800)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.disease_howtocure),
                    fontFamily = Libre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = stringResource(R.string.lorem),
                    fontFamily = Libre,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color.White,
                )
            }

            Column(
                modifier = modifier
                    .background(Primary800)
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.disease_drugs),
                    fontFamily = Libre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = stringResource(R.string.lorem),
                    fontFamily = Libre,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    AgroAITheme {
        DetailScreen()
    }
}