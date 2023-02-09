package com.airhorn.airhorncrazysiren

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.*

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airhorn.airhorncrazysiren.jetpacknavigation.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SayfaTopBar(state : MutableState<TextFieldValue>,navController: NavController,context: Context){
    val control = remember {
        mutableStateOf(false)
    }

    val textField = remember {
        mutableStateOf("")
    }


        Row(

            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp)
                .background(color = Color.White)
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            Scaffold(

                topBar = {

                    TopAppBar(

                        title = {

                            if(control.value){

                                Row(
                                    modifier = Modifier
                                        .width(320.dp)
                                        .height(55.dp)
                                        .background(color = Color.White),
                                    verticalAlignment = Alignment.CenterVertically

                                )

                                {
                                    TextField(
                                        value = state.value,
                                        onValueChange ={
                                            state.value = it // her girdiği yazıyı textField e aktar

                                        }
                                        , modifier = Modifier
                                            .width(320.dp)
                                            .height(55.dp)
                                            .clip(CircleShape)
                                            .background(color = Color.White)
                                        , colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.White,
                                            focusedIndicatorColor = Color.White,
                                            cursorColor = Color.DarkGray,



                                            )


                                    )
                                }



                            } else{
                                IconButton(onClick = {
                                    control.value = true



                                }) {
                                    Row(
                                        modifier = Modifier
                                            .width(320.dp)
                                            .background(color = Color.White),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(painter = painterResource(id = R.drawable.search),
                                            contentDescription = "",
                                            tint = colorResource(id = R.color.searchColor)
                                        )

                                    }
                                    Text(text = "What are you Looking for?", fontStyle = FontStyle.Normal, fontWeight = FontWeight.Medium, fontSize = 17.sp)
                                }

                            }
                        },
                        actions = {
                            if (control.value){
                                IconButton(onClick = {

                                    control.value = false
                                    textField.value = ""
                                }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                        contentDescription = "",
                                        tint = colorResource(id = R.color.searchColor)
                                    )
                                }

                            }


                        },
                        backgroundColor = Color.White,
                        contentColor = colorResource(id = R.color.searchTextColor),
                        modifier = Modifier
                            .clipToBounds()
                            .padding(top = 10.dp, start = 15.dp)
                            .clip(CircleShape)
                            .border(
                                1.3.dp,
                                color = colorResource(id = R.color.searchBoxColor),
                                shape = CircleShape
                            )
                            .height(60.dp)
                            .width(320.dp)
                    )
                },
                content = {


                }
            , modifier = Modifier
                    .width(320.dp)
                    .background(color = Color.White)

            )
            Spacer(modifier = Modifier.padding(2.dp))

            Icon(
                painter = painterResource(id = R.drawable.kalp),
                contentDescription = "",
                modifier = Modifier
                    .size(23.dp)
                    .clickable {

                        navController.navigate(Screen.FavoriHome.roote)
                    },
                tint = Color.Red

            )

        }


    }

@Composable
fun adding(navController: NavController,context: Context){
    
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    Column() {
        SayfaTopBar(textState,navController,context)
        SongList(navController = navController, state = textState, context =context )

    }
}

































