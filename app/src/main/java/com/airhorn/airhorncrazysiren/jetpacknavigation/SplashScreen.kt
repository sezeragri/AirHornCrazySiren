package com.airhorn.airhorncrazysiren.jetpacknavigation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airhorn.airhorncrazysiren.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
@Composable
fun Login(navController: NavController) {

    val status: Color = colorResource(id = R.color.disdukes)
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = status

        )

        onDispose {}
    }
    Surface(
        color = colorResource(id = R.color.disdukes), modifier = Modifier.fillMaxSize()
    ) {
        val scale = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.6f,
                animationSpec = tween(durationMillis = 800, easing = {
                    OvershootInterpolator(7f).getInterpolation(it)
                })
            )


            delay(3000L)
            navController.navigate(route = Screen.HomeScreen.roote)


        }


         Column(

                   modifier = Modifier.fillMaxSize(),
                   verticalArrangement = Arrangement.Top,
                   horizontalAlignment = Alignment.CenterHorizontally,


                   ) {


                   Spacer(modifier = Modifier.padding(15.dp))
                   Text(text = "Air Horn Sounds", fontSize = 35.sp, color = Color.White, fontWeight = FontWeight.Bold)
                   Spacer(modifier = Modifier.padding(30.dp))

                   Image(
                       painter = painterResource(id = R.drawable.air), "", modifier = Modifier
                           .size(400.dp)
                           .scale(scale.value)
                   )
             Spacer(modifier = Modifier.padding(15.dp))

             Text(text = "Now it's time to have fun!", fontSize = 25.sp, color = Color.White, fontWeight = FontWeight.Bold)

               }

    }




}