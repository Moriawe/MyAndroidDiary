package com.moriawe.myandroiddiary.presentation.daily_stats

sealed interface DailyStatsAction {
    data object addDailyStats : DailyStatsAction
    data object deleteDailyStats : DailyStatsAction
    data class updateDailyStats(val itemId: Int): DailyStatsAction
}