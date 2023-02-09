package com.airhorn.airhorncrazysiren.jetpacknavigation

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.airhorn.airhorncrazysiren.R
import com.airhorn.airhorncrazysiren.SayfaTopBar
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    context: Context,

    ) {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val appURL = "https://play.google.com/store/apps/details?id=com.Bomdia.Gif.WAStickerApps"
    NavHost(
        navController = navController,
        startDestination = Screen.Login.roote
    ) {

        composable(
            route = Screen.Login.roote
        ) {

            Login(navController)

        }

        composable(
            route = Screen.HomeScreen.roote
        ) {

            HomeScreen(navController, context)

        }
        composable(
            route = Screen.DetailScreen.roote,


            )
        {

            val json = it.arguments?.getString("data")
            val mapper = jacksonObjectMapper()
            val state: Data = mapper.readValue(json!!)

            if (state != null) {
                DetailScreen(context, navController, state)
            }

        }
        composable(
            route = Screen.SayfaTopBar.roote
        ) {

            SayfaTopBar(textState, navController, context)

        }
        composable(
            route = Screen.FavoriHome.roote
        ) {

            FavoriHome(navController, context)

        }


    }


}





