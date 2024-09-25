package com.moriawe.myandroiddiary.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moriawe.myandroiddiary.R

sealed class Route(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int? = null
) {
    data object LoginRoute : Route(
        route = "login",
        title = R.string.login_header,
        icon = null
    )

    data object DailyStatsRoute : Route(
        route = "daily",
        title = R.string.daily_stats_header,
        icon = null
    )
}