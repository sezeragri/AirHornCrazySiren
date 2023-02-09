package com.airhorn.airhorncrazysiren.jetpacknavigation

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airhorn.airhorncrazysiren.R
import com.airhorn.airhorncrazysiren.adding
import com.airhorn.airhorncrazysiren.ui.theme.Shapes
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


private var state : ArrayList<Song> = ArrayList()

var add_id = "ca-app-pub-4270315255262769/4367426397"
var mInterstitialAd: InterstitialAd? = null
private var adRequest: AdRequest? = null
private var count : Int = 1
private var count1 : Int = 0


@Composable
fun HomeScreen(navController: NavController, context: Context) {

    val status: Color = Color.White
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setSystemBarsColor(
            color = status

        )
        onDispose {}
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        adding(navController = navController, context)

    }
}

@Composable
fun SongCard(navController: NavController, song: Song, context: Context) {

    val dialog = remember {
        mutableStateOf(false)
    }

    var heartColor by remember {
        mutableStateOf(false)
    }

    MobileAds.initialize(context) {}
    adsLoaded(context)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(color = Color.White)
            .wrapContentWidth()
            .wrapContentSize()
            .padding(top = 20.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Card(modifier = Modifier
                .height(195.dp)
                .width(170.dp)
                .clickable {
                    count += 1

                    if (count % 4 == 0 && mInterstitialAd != null) {
                        mInterstitialAd?.show(context as Activity)


                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }

                    DataTransfer(
                        navController,
                        soundImage = song.soundImage,
                        soundColor = song.soundColor,
                        soundName = song.soundName,
                        soundColor2 = song.soundColor2,
                        sound = song.sound
                    )


                }
                .graphicsLayer {
                    clip = true
                    shape = Shapes.small
                })
            {

                Column(
                    horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .height(195.dp)
                        .width(170.dp)
                        .background(color = colorResource(id = song.soundColor))

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kalp),
                        "",
                        modifier = Modifier
                            .padding(0.dp, 15.dp, 15.dp, 0.dp)
                            .border(
                                2.dp,
                                color = colorResource(id = song.soundColor2),
                                shape = CircleShape
                            )
                            .padding(6.dp, 6.dp)
                            .background(colorResource(id = song.soundColor), CircleShape)
                            //.clip(CircleShape)
                            .size(18.dp)
                            .clickable {


                                dialog.value = true


                                val preference =
                                    context.getSharedPreferences("apple", Context.MODE_PRIVATE)
                                val mapper = jacksonObjectMapper()
                                val favories = preference.getString("favories", "[]")
                                state = mapper.readValue(favories!!)
                                var isexit: Boolean = false
                                for (i in state) {
                                    if (i.sound == song.sound) {
                                        isexit = true


                                    }
                                }
                                if (isexit == false) {

                                    state.add(song)
                                    heartColor = true

                                    val editor = preference.edit()
                                    editor.putString("favories", mapper.writeValueAsString(state))
                                    editor.commit()
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Already available on the favorites page!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()

                                }


                            },


                        colorFilter = ColorFilter.tint(if (song in state || heartColor == true) Color.Red else Color.White),
                        contentScale = ContentScale.FillBounds

                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier

                        .height(200.dp)
                        .width(170.dp)
                        .padding(top = 30.dp)


                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = song.soundColor2),
                                shape = CircleShape
                            )
                            .size(90.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = song.soundImage),
                            "",
                            modifier = Modifier
                                .requiredSize(70.dp)

                                .background(colorResource(id = song.soundColor2), CircleShape),

                            contentScale = ContentScale.FillBounds
                        )


                    }

                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    androidx.compose.material.Text(
                        text = song.soundName,
                        style = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold

                        )
                    )

                }
            }

        }


    }


}

data class Song(
    val soundName: String,
    val soundImage: Int,
    val soundColor: Int,
    val soundColor2: Int,
    val sound: Int,
    val songId : Int

)

val songs = listOf(
    Song(
        soundColor = R.color.dismor,
        soundColor2 = R.color.icmor,
        soundName = "AirHornRemix",
        soundImage = R.drawable.airremix,
        sound = R.raw.airremix,
        songId = 1

    ),
    Song(
        soundColor = R.color.diskir,
        soundColor2 = R.color.ickir,
        soundName = "CarHorn",
        soundImage = R.drawable.carhorn,
        sound = R.raw.carhornkes,
        songId = 2
    ),
    Song(
        soundColor = R.color.cavarlydis,
        soundColor2 = R.color.cavarlyic,
        soundName = "Cavalry",
        soundImage = R.drawable.trumpet,
        sound = R.raw.cavalrykes,
        songId = 3

    ),
    Song(
        soundColor = R.color.clowndis,
        soundColor2 = R.color.clownic,
        soundName = "ClownHorn",
        soundImage = R.drawable.clown,
        sound = R.raw.clown_hornkes,
        songId = 4
    ),
    Song(
        soundColor = R.color.countdis,
        soundColor2 = R.color.countic,
        soundName = "Alarm",
        soundImage = R.drawable.clock,
        sound = R.raw.alarm,
        songId = 5

    ),
    Song(
        soundColor = R.color.djdis,
        soundColor2 = R.color.djic,
        soundName = "DJHorn",
        soundImage = R.drawable.djhorn,
        sound = R.raw.djhorn,
        songId = 6
    ),

    Song(
        soundColor = R.color.disdukes,
        soundColor2 = R.color.icdukes,
        soundName = "PoliceSiren",
        soundImage = R.drawable.policesiren,
        sound = R.raw.policesiren,
        songId = 7

    ),
    Song(
        soundColor = R.color.disemergency,
        soundColor2 = R.color.icemergency,
        soundName = "Emergency",
        soundImage = R.drawable.emergency,
        sound = R.raw.ambulancesiren,
        songId = 8
    ),
    Song(
        soundColor = R.color.dismor,
        soundColor2 = R.color.icmor,
        soundName = "Emergency2",
        soundImage = R.drawable.emergencyone,
        sound = R.raw.emergency_two,
        songId = 9

    ),
    Song(
        soundColor = R.color.diskir,
        soundColor2= R.color.ickir,
        soundName = "FartHorn",
        soundImage = R.drawable.fart,
        sound = R.raw.fart,
        songId = 10
    ),
    Song(
        soundColor = R.color.cavarlydis,
        soundColor2 = R.color.cavarlyic,
        soundName = "GhostSiren",
        soundImage = R.drawable.ghostone,
        sound = R.raw.ghost,
        songId = 11

    ),
    Song(
        soundColor = R.color.clowndis,
        soundColor2 = R.color.clownic,
        soundName = "GoalHorn",
        soundImage = R.drawable.redbuton,
        sound = R.raw.goalhorn,
        songId = 12
    ),
    Song(
        soundColor = R.color.countdis,
        soundColor2 = R.color.countic,
        soundName = "HellaHorn",
        soundImage = R.drawable.hellahorn,
        sound = R.raw.hella_horn,
        songId = 13

    ),
    Song(
        soundColor = R.color.djdis,
        soundColor2= R.color.djic,
        soundName = "IceCreamBell",
        soundImage= R.drawable.bell,
        sound = R.raw.ice_cream,
        songId = 14
    ),
    Song(
        soundColor = R.color.dismor,
        soundColor2 = R.color.icmor,
        soundName = "CarWindowBreaking",
        soundImage = R.drawable.window,
        sound = R.raw.breaking,
        songId = 15

    ),
    Song(
        soundColor = R.color.diskir,
        soundColor2 = R.color.ickir,
        soundName = "FireTruckSiren",
        soundImage = R.drawable.fire_truck_siren,
        sound = R.raw.fire_truck,
        songId = 16
    ),



    )


fun DataTransfer(
    navController: NavController,
    soundName: String,
    soundColor: Int,
    soundColor2: Int, soundImage: Int, sound: Int
) {

    val ROUTE_USER_DETAILS = "detail_screen/data={data}"

    val data = Data(
        soundName,
        soundColor,
        soundImage,
        soundColor2,
        sound
    )


    val mapper = jacksonObjectMapper()


    navController.navigate(

        ROUTE_USER_DETAILS.replace("{data}", mapper.writeValueAsString(data))
    )


}


@Composable
fun SongList(navController: NavController, state: MutableState<TextFieldValue>, context: Context) {

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 180.dp)) {


        items(items = songs.filter {


            it.soundName.contains(state.value.text, ignoreCase = true)

        }, key = { it.soundName }) { item ->

            SongCard(

                navController, item, context,
            )


        }


    }
}

@Composable
fun FavoriList(navController: NavController,context: Context){

    LazyVerticalGrid( columns = GridCells.Adaptive(minSize = 180.dp), state = LazyGridState()){

        items(state){ item ->

                Favori(navController = navController, item , context = context)
            }


        }

    }

@Composable
fun Favori(navController: NavController, song : Song, context: Context){


    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .background(color = Color.White)
            .wrapContentWidth()
            .wrapContentSize()
            .padding(top = 20.dp)
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Card(modifier = Modifier
                .height(195.dp)
                .width(170.dp)
                .clickable {

                    count1 += 1

                    if (count1 % 4 == 0 && mInterstitialAd != null) {
                        mInterstitialAd?.show(context as Activity)
                        count1 = 1

                    } else {
                        Log.d("TAG", "The interstitial ad wasn't ready yet.")
                    }
                    DataTransfer(
                        navController,
                        soundImage = song.soundImage,
                        soundColor = song.soundColor,
                        soundName = song.soundName,
                        soundColor2 = song.soundColor2,
                        sound = song.sound
                    )

                }
                .graphicsLayer {
                    clip = true
                    shape = Shapes.small
                })
            {

                Column(
                    horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .height(195.dp)
                        .width(170.dp)
                        .background(color = colorResource(id = song.soundColor))

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        "",
                        modifier = Modifier
                            .padding(0.dp, 15.dp, 15.dp, 0.dp)
                            .border(
                                2.dp,
                                color = colorResource(id = song.soundColor2),
                                shape = CircleShape
                            )
                            .padding(6.dp, 6.dp)
                            .background(colorResource(id = song.soundColor), CircleShape)
                            //.clip(CircleShape)
                            .size(18.dp)
                            .clickable {


                                val preference =
                                    context.getSharedPreferences("apple", Context.MODE_PRIVATE)
                                val mapper = jacksonObjectMapper()
                                val favories = preference.getString("favories", "[]")

                                state = mapper.readValue(favories!!)
                                var isexit: Boolean = false
                                for (i in state) {
                                    if (i.sound == song.sound) {
                                        isexit = true

                                    }
                                }
                                if (isexit == true) {

                                    state.remove(song)
                                    navController.navigate(Screen.FavoriHome.roote){
                                        popUpTo(0)
                                    }

                                    val editor = preference.edit()
                                    editor.putString("favories", mapper.writeValueAsString(state))
                                    editor.apply()


                                }


                            },

                        colorFilter = ColorFilter.tint(Color.White),
                        contentScale = ContentScale.FillBounds

                    )

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier

                        .height(200.dp)
                        .width(170.dp)
                        .padding(top = 30.dp)


                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = song.soundColor2),
                                shape = CircleShape
                            )
                            .size(90.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = song.soundImage),
                            "",
                            modifier = Modifier
                                .requiredSize(70.dp)

                                .background(colorResource(id = song.soundColor2), CircleShape),

                            contentScale = ContentScale.FillBounds
                        )


                    }

                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    androidx.compose.material.Text(
                        text = song.soundName,
                        style = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium

                        )
                    )

                }
            }

        }


    }



}

@Composable
fun FavoriHome(navController: NavController,context: Context){


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 13.dp, top = 10.dp, end = 10.dp)) {

                Box(modifier = Modifier
                    .size(25.dp)
                    .border(1.7.dp, shape = CircleShape, color = Color.Black),
                    contentAlignment = Alignment.Center) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        "",
                        modifier = Modifier
                            .size(18.dp)
                            .clickable
                            {
                                navController.navigate(Screen.HomeScreen.roote){
                                    popUpTo(0)
                                }


                            }, tint = Color.Black)

                }

                Text(
                    text = "FAVOURİTES",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Normal, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold
                )

                Box(modifier = Modifier
                    .size(27.dp)
                    .border(
                        2.dp,
                        shape = CircleShape,
                        color = Color.Black
                    ),
                    contentAlignment = Alignment.Center) {

                    Icon(
                        painter = painterResource(id = R.drawable.kalp),
                        "",
                        modifier = Modifier
                            .padding(0.dp)
                            .size(15.dp)
                            .clickable {
                                //navController.navigate(Screen.HomeScreen.roote)
                            }, tint = Color.Red )

                }
            }

        val preference =
            context.getSharedPreferences("apple", Context.MODE_PRIVATE)
        val mapper = jacksonObjectMapper()
        val favories = preference.getString("favories", "[]")
        state = mapper.readValue(favories!!)



      FavoriList( navController = navController,context)
        }

    }


fun addListener(context: Context) {

    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Log.d(ContentValues.TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            Log.d(ContentValues.TAG, "Ad dismissed fullscreen content.")
            mInterstitialAd = null
            adsLoaded(context)
            count = 1



        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {

            mInterstitialAd = null

        }

    }
}

fun adsLoaded(context: Context) {
    adRequest = AdRequest.Builder().build()

    InterstitialAd.load(context, add_id,
        adRequest!!, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(ContentValues.TAG, adError?.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                addListener(context)

            }


        })
}

