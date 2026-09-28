package com.example.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// ---------------------------------------------------------
// COLORS
// ---------------------------------------------------------

val SoftPink = Color(0xFFFFE8F0)
val LightPink = Color(0xFFFFF4F7)
val MainPink = Color(0xFFE95B91)
val DarkPink = Color(0xFF991B4D)
val MediumPink = Color(0xFFD94D83)
val White = Color(0xFFFFFFFF)


// ---------------------------------------------------------
// MAIN APP
// ---------------------------------------------------------

@Composable
fun CalculatorApp() {

    var showCalculator by remember {
        mutableStateOf(false)
    }

    if (showCalculator) {

        CalculatorScreen()

    } else {

        WelcomeScreen(
            onGetStarted = {
                showCalculator = true
            }
        )
    }
}


// ---------------------------------------------------------
// WELCOME SCREEN
// ---------------------------------------------------------

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        SoftPink,
                        LightPink
                    )
                )
            )
    ) {

        Text(
            text = "♡",
            color = MainPink,
            fontSize = 26.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 45.dp,
                    end = 32.dp
                )
        )


        // -------------------------------------------------
        // MAIN CONTENT
        // -------------------------------------------------

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally,

            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "♡",
                color = MainPink,
                fontSize = 28.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // -------------------------------------------------
            // IMAGE
            // -------------------------------------------------

            Image(
                painter = painterResource(id = R.drawable.welcome_image),
                contentDescription = "Calculator Image",
                modifier = Modifier
                    .size(180.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(40.dp)
                    )
                    .clip(RoundedCornerShape(40.dp))
                    .background(
                        color = White,
                        shape = RoundedCornerShape(40.dp)
                    )
                    .padding(16.dp)
            )


            Spacer(
                modifier = Modifier.height(30.dp)
            )


            // -------------------------------------------------
            // APP TITLE
            // -------------------------------------------------

            Text(
                text = "Pink Calculator",
                color = DarkPink,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // -------------------------------------------------
            // DESCRIPTION
            // -------------------------------------------------

            Text(
                text = "Simple, cute and easy calculator",
                color = MediumPink,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )


            Spacer(
                modifier = Modifier.height(36.dp)
            )


            // -------------------------------------------------
            // GET STARTED BUTTON
            // -------------------------------------------------

            Button(
                onClick = {
                    onGetStarted()
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(20.dp)
                    ),

                shape = RoundedCornerShape(20.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MainPink
                )
            ) {

                Text(
                    text = "GET STARTED",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(
                modifier = Modifier.height(24.dp)
            )


            // -------------------------------------------------
            // BOTTOM TEXT
            // -------------------------------------------------

            Text(
                text = "♡ Made with love ♡",
                color = MediumPink,
                fontSize = 14.sp
            )
        }


        // -------------------------------------------------
        // BOTTOM RIGHT HEART
        // -------------------------------------------------

        Text(
            text = "♡",
            color = MainPink,
            fontSize = 34.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 35.dp,
                    bottom = 45.dp
                )
        )


        // -------------------------------------------------
        // BOTTOM LEFT HEART
        // -------------------------------------------------

        Text(
            text = "♡",
            color = MainPink,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 35.dp,
                    bottom = 80.dp
                )
        )
    }
}