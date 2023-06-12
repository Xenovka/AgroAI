package com.capstone.agroai.ui.screen.welcome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.agroai.R
import com.capstone.agroai.ui.navigation.Screen
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Montserrat
import com.capstone.agroai.ui.theme.Primary200
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Primary900

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navigateTo: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.welcome_bg),
            contentDescription = null,
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Canvas(modifier = modifier.fillMaxSize()){
            drawRect(
                color = Primary900,
                alpha = 0.6f
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.display_name),
            fontSize = 28.sp,
            fontFamily = Libre,
            color = Color.White,
            modifier = modifier
                .padding(top = 20.dp)
        )

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                fontSize = 34.sp,
                fontFamily = Libre,
                lineHeight = 45.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            )

            MyButton(
                onClick = { navigateTo(Screen.Home.route) },
                modifier = modifier
                    .padding(top = 37.dp, bottom = 15.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                text = stringResource(R.string.register).uppercase(),
                textColor = Color.White,
                isEnabled = false,
                containerColor = Primary400
            )

            Text(
                text = "or",
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Bold,
                color = Primary400,
                modifier = modifier.padding(bottom = 15.dp)
            )

            MyButton(
                onClick = { navigateTo(Screen.Home.route) },
                modifier = modifier
                    .padding(bottom = 50.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                text = stringResource(R.string.continue_as_guest),
                textColor = Primary400,
                isEnabled = true,
                containerColor = Color.White
            )
        }
    }
}

@Composable
fun MyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    containerColor: Color,
    isEnabled: Boolean
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            disabledContainerColor = Primary200
        ),
        enabled = isEnabled,
        shape = RectangleShape,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    AgroAITheme {
        WelcomeScreen(navigateTo = {})
    }
}