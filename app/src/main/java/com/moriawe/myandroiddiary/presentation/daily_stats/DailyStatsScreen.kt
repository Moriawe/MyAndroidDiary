package com.moriawe.myandroiddiary.presentation.daily_stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moriawe.myandroiddiary.R
import com.moriawe.myandroiddiary.presentation.designsystem.theme.MyAndroidDiaryTheme

@Composable
fun DailyStatsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: DailyStatsViewModel,
) {
    DailyStatsScreen()
}


@Composable
fun DailyStatsScreen(

) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.daily_stats_header))
        Spacer(modifier = Modifier.height(16.dp))
    }

}

@Preview
@Composable
private fun DailyStatsScreenPreview() {
    MyAndroidDiaryTheme() {
        DailyStatsScreen()
    }

}