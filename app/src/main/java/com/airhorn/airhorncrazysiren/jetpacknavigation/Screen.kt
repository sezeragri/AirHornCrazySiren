package com.airhorn.airhorncrazysiren.jetpacknavigation

sealed class Screen(val roote : String ){
    object Login: Screen("login")
    object HomeScreen : Screen("home_screen")
    object DetailScreen: Screen("detail_screen/data={data}")
    object FavoriHome : Screen("list")
    object SayfaTopBar : Screen("search")


}
