package com.airhorn.airhorncrazysiren.jetpacknavigation

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airhorn.airhorncrazysiren.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController


private lateinit var tmer : CountDownTimer


@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailScreen(context: Context, navController: NavHostController,data : Data) {
    val checkedTrackColor: Color =  colorResource(id = data.soundColor2)
    val uncheckedTrackColor: Color = colorResource(id = R.color.clowndis)
    val status: Color = colorResource(id = data.soundColor)

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()



    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = status

        )



        onDispose {}
    }

    var switchON = remember {
        mutableStateOf(true) // Initially the switch is ON
    }
    var timer by remember {
        mutableStateOf("")
    }
    var isPressed by remember {
        mutableStateOf(false)
    }
    var clicable by remember {
        mutableStateOf(false)
    }
    var clicabletimer by remember {
        mutableStateOf(false)
    }
    var dropClick by remember {
        mutableStateOf(false)
    }



    val sampleSong: MediaPlayer by remember {
        mutableStateOf(
            MediaPlayer.create(
                context,
                data.sound // your audio file
            )
        )
    }

    var isPlaying by remember {
        mutableStateOf(false)
    }


    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember {
        mutableStateOf("  OFF")
    }

    val TimeList = listOf("  OFF","15","20","30","45","1")






        Surface(
            color = colorResource(id = data.soundColor),
            modifier = Modifier.fillMaxSize()
        ) {

            Column {

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 13.dp, top = 10.dp, end = 10.dp)) {

                    Box(modifier = Modifier
                        .size(25.dp)
                        .border(1.7.dp, shape = CircleShape, color = Color.White),
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
                                    sampleSong.stop()
                                    sampleSong.prepare()


                                }, tint = Color.White)

                    }

                    Text(
                        text = data.soundName,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Normal, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold
                    )

                    Box(modifier = Modifier
                        .size(27.dp)
                        .border(
                            2.dp,
                            shape = CircleShape,
                            color = colorResource(id = data.soundColor2)
                        ),
                        contentAlignment = Alignment.Center) {

                        Icon(
                            painter = painterResource(id = R.drawable.kalp),
                            "",
                            modifier = Modifier
                                .padding(0.dp)
                                .size(15.dp)
                                .clickable {

                                }, tint = Color.White )

                    }
                }

                Spacer(modifier = Modifier.padding(15.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextButton(onClick = {
                        expanded = true
                    }){
                        Row(modifier = Modifier
                            .padding(start = 5.dp)) {

                            Text(text = "Timer", fontSize = 20.sp, color = Color.White)
                            Spacer(modifier = Modifier.padding(start = 8.dp))
                            Row(modifier = Modifier.background(color = colorResource(id = data.soundColor2), shape = RoundedCornerShape(5.dp))) {
                                Text(text =if(selectedItem == "1") "${selectedItem}m" else if(selectedItem=="  OFF") "$selectedItem" else "${selectedItem}s" , fontSize = 20.sp,color = Color.White)
                                Spacer(modifier = Modifier.padding(start = 8.dp))
                                Icon(if(expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription ="" , tint = Color.White)

                            }

                        }
                    }
                    DropdownMenu(expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(color = Color.White)
                            .width(75.dp)
                            .align(Alignment.Top),


                    ) {

                        TimeList.forEach{
                            DropdownMenuItem(onClick = {


                                if (clicabletimer == true){
                                    tmer.cancel()
                                    clicabletimer = false

                                }


                                expanded = false
                                selectedItem = it

                               tmer = object : CountDownTimer(
                                   if(selectedItem == "  OFF"){
                                       (0) * 1000
                                   }else if (selectedItem == "1") {

                                       ((selectedItem.toLong()*60)) * 1000

                                         }else
                                         {
                                       (selectedItem.toLong()+1) * 1000

                                               },

                                   1000) {
                                    override fun onTick(millisUntilFinished: Long) {

                                        timer = "${millisUntilFinished / 1000}"

                                    }

                                    override fun onFinish() {

                                        timer = "00"
                                        if (selectedItem != "  OFF"){
                                            sampleSong.start()
                                            sampleSong.isLooping
                                        }

                                    }

                                }

                                if (clicabletimer == false){

                                    tmer.start()
                                    clicabletimer= true
                                    dropClick = false


                                }


                            }
                                , contentPadding = PaddingValues(6.dp),) {


                                Text(text = if(it == "  OFF") it else if(it == "1") "${it}m" else "${it}s", color = Color.DarkGray, fontSize = 12.sp)
                            }

                        }

                    }
                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(color = Color.Unspecified), verticalAlignment =
                        Alignment.CenterVertically) {
                        Text(
                            text = "Loop",
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(end = 12.dp)

                        )
                        Spacer(modifier = Modifier.padding(end = 5.dp))


                        val thumbRadius = (19.dp / 2) - 2.dp

                        // To move thumb, we need to calculate the position (along x axis)
                        val animatePosition = animateFloatAsState(
                            targetValue = if (!switchON.value) {
                                with(LocalDensity.current) { (34.dp - thumbRadius - 2.dp).toPx() }
                            }
                            else {
                                with(LocalDensity.current) { (thumbRadius + 2.dp).toPx() }


                            }
                        )

                        Canvas(
                            modifier = Modifier
                                .size(width = 34.dp, height = 19.dp)
                                .scale(scale = 1.5f)
                                .background(color = Color.Unspecified, shape = CircleShape)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {

                                            switchON.value = !switchON.value

                                        }


                                    )
                                },

                            ) {
                            // Track
                            drawRoundRect(
                                color = checkedTrackColor,
                                cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),

                            )

                            // Thumb
                            drawCircle(
                                color = if (!switchON.value) uncheckedTrackColor else  Color.White,
                                radius = thumbRadius.toPx(),
                                center = Offset(
                                    x = animatePosition.value,
                                    y = size.height / 2
                                )
                            )
                        }

                        Spacer(modifier = Modifier.padding(end = 10.dp))
                    }

                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(

                ) {
                    Box( contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {

                            Text(
                                text = if(selectedItem == "1") "00:${timer}"  else if(selectedItem=="  OFF") "00:00"  else "00:$timer",
                                color = Color.White,
                                fontSize = 25.sp,


                                )


                    }
                }

                Spacer(modifier = Modifier.padding(35.dp))

                Row (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ){
                    Box( contentAlignment = Alignment.Center, modifier = Modifier

                        .clip(CircleShape)
                        //.border(0.dp, Color.White, CircleShape)
                        .size(260.dp)
                        .background(
                            color = colorResource(id = data.soundColor2)
                        )
                        .pointerInput(Unit) {


                            sampleSong.stop()
                            sampleSong.prepare()

                            detectTapGestures(

                                onPress = {
                                    if (switchON.value) {

                                        clicable = true

                                        try {
                                            isPressed = true
                                            isPlaying = true
                                            sampleSong.start()
                                            sampleSong.isLooping = true
                                            awaitRelease()

                                        } finally {
                                            //released
                                            isPressed = false
                                            isPlaying = false
                                            sampleSong.stop()
                                            sampleSong.prepare()


                                        }
                                    } else {


                                        clicable = false
                                        sampleSong.start()
                                        sampleSong.isLooping = true
                                        clicable = true


                                    }
                                }


                            )


                        }

                    ) {

                        Image(painter = painterResource(id = data.soundImage), "",
                            modifier = Modifier
                                .size(160.dp)
                                .graphicsLayer {
                                    //clip = true
                                    shape = CircleShape
                                }

                                .background(
                                    color = colorResource(id = data.soundColor2)
                                )


                        )
                    }

                }

                Spacer(modifier = Modifier.padding(40.dp))
              Row(modifier = Modifier
                  .background(color = Color.Transparent)
                  .fillMaxWidth(),
                  horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically) {
                  
                  Text(text = "Tap or Press to Play Sound", fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color.White)
                  
              }

                Spacer(modifier = Modifier.padding(10.dp))


                var sliderPosition by remember { mutableStateOf(0.3f) }

              Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(painter = painterResource(id = R.drawable.volumeneg),
                        contentDescription = "", tint = Color.White,
                        modifier = Modifier
                            .size(30.dp))

                    Row(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = data.soundColor2),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .wrapContentWidth()
                            .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically) {

                        Slider(value = sliderPosition,
                            valueRange = 0F..1.0f,
                            onValueChange = {
                                sliderPosition = it
                                sampleSong.setVolume(sliderPosition,sliderPosition)

                                            },
                            modifier = Modifier

                                .width(250.dp)
                                .height(30.dp)
                                .padding(start = 10.dp, end = 6.dp),


                            colors = SliderDefaults.colors(
                                thumbColor = colorResource(id = data.soundColor),
                                activeTrackColor = colorResource(id = data.soundColor),
                                inactiveTrackColor = Color.White
                            )
                            )
                    }



                    Icon(painter = painterResource(id = R.drawable.volumeplus),
                        contentDescription = "", tint = Color.White,
                        modifier = Modifier
                            .size(30.dp))
                }
                




            }
        }
    }



