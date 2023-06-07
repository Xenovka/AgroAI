package com.capstone.agroai.ui.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.agroai.R
import com.capstone.agroai.ui.theme.AgroAITheme
import com.capstone.agroai.ui.theme.Libre
import com.capstone.agroai.ui.theme.Montserrat
import com.capstone.agroai.ui.theme.Primary200
import com.capstone.agroai.ui.theme.Primary400
import com.capstone.agroai.ui.theme.Secondary50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    modifier : Modifier = Modifier
) {
    var iEmail by remember { mutableStateOf("") }
    var iPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .background(Secondary50)
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Box {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { onBackClick() }
            )

            Text(
                text = stringResource(R.string.login),
                textAlign = TextAlign.Center,
                fontFamily = Libre,
                fontSize = 26.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }

        TextField(
            value = iEmail,
            onValueChange = { iEmail = it },
            textStyle = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),
            label = {
                Text(
                    text = "Email Address",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RectangleShape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 80.dp, bottom = 15.dp, start = 15.dp, end = 15.dp)
        )

        TextField(
            value = iPassword,
            onValueChange = { iPassword = it },
            textStyle = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
            ),
            label = {
                Text(
                    text = "Password",
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            },
            singleLine = true,
            shape = RectangleShape,
            supportingText = {
                Text(
                    text = "Minimum 8 characters.",
                    fontFamily = Montserrat,
                    fontSize = 12.sp
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp)
        )

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                enabled = false,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Primary400,
                    disabledContainerColor = Primary200
                ),
                shape = RectangleShape,
                modifier = modifier
                    .padding(top = 37.dp, bottom = 15.dp)
                    .width(320.dp)
                    .height(60.dp)
            ) {
                Text(
                    text = stringResource(R.string.login).uppercase(),
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
fun PreviewLoginScreen() {
    AgroAITheme {
        LoginScreen(onBackClick = {})
    }
}