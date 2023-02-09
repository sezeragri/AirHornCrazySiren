package com.airhorn.airhorncrazysiren

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airhorn.airhorncrazysiren.jetpacknavigation.*
import com.airhorn.airhorncrazysiren.ui.theme.AirHornCrazySirenTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavController
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {



            Surface(
                color = Color.White, modifier = Modifier.fillMaxSize()
            ) {
                AirHornCrazySirenTheme() {


                    navController = rememberNavController()
                    SetupNavGraph(navController = navController as NavHostController, this)

                } } } } }





