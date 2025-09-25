package com.jperez.timerapp.feature.composable

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings
import com.jperez.timerapp.feature.model.EntryUI
import com.jperez.timerapp.ui.theme.Typography
import java.time.Duration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    items: List<EntryUI>,
    duration: Duration,
    isPaused: Boolean,
    onSettingsTap: () -> Unit,
    onStart: () -> Unit,
    onPaused: () -> Unit,
    onStop: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Timer") },
                actions = {
                    IconButton(
                        onClick = onSettingsTap,
                    ) {
                        Icon(imageVector = Lucide.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
        ) { innerPadding ->
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