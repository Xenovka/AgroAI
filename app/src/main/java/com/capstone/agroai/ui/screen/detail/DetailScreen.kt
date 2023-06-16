package com.capstone.agroai.ui.screen.detail

import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.capstone.agroai.R
import com.capstone.agroai.di.Injection
import com.capstone.agroai.ui.ViewModelFactory
import com.capstone.agroai.ui.common.UiState
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary800
import com.capstone.agroai.ui.theme.Primary900

@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    disease: String,
    imageUriString: String,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getClassificationByName(disease)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    navigateBack,
                    data.diseaseNameIndonesia,
                    imageUriString,
                    data.definition,
                    data.causes
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    navigateBack: () -> Unit,
    disease: String,
    imageUriString: String,
    definition: String,
    causes: String,
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
            AsyncImage(
                model = Uri.parse(imageUriString),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Button(
                onClick = {
                    navigateBack()
                },
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
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(15.dp)
                .fillMaxSize()
        ) {
            Text(
                text = disease,
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
                    text = stringResource(R.string.disease_description, disease),
                    fontFamily = Libre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = definition,
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
                    text = stringResource(R.string.disease_causes, disease),
                    fontFamily = Libre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = modifier.padding(bottom = 10.dp)
                )

                Text(
                    text = causes,
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
                    text = stringResource(R.string.under_development),
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
fun PreviewDetailContent() {
    AgroAITheme {
        DetailContent({}, "", "", "", "")
    }
}