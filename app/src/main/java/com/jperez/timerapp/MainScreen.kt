package com.jperez.timerapp

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jperez.timerapp.model.EntryUI
import com.jperez.timerapp.ui.theme.Typography
import java.time.Duration

@Composable
fun MainScreen(
    items: List<EntryUI>,
    duration: Duration,
    isPaused: Boolean,
    onStart: () -> Unit,
    onPaused: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = DateUtils.formatElapsedTime(duration.seconds),
                style = Typography.titleLarge
            )

            if (duration.isZero)
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = onStart,
                ) {
                    Text("Start Timer")
                }

            if (!duration.isZero)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = onPaused,
                    ) {
                        Text(if (isPaused) "Resume Timer" else "Pause Timer")
                    }
                    Box(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onStop,
                    ) {
                        Text("Stop Timer")
                    }
                }

            LazyColumn {
                items(items.size) { index ->
                    EntryListItem(
                        entry = items[index],
                        onCardClick = {}
                    )
                }
            }
        }
    }
}